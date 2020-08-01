package testproject.yara.batman.data.datasource;

import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static testproject.yara.batman.data.Constants.CONNECTION_ERROR;

/**
 * Check validity of local data, if local data is valid and not expired local data returned, else
 * remote data fetched and save locally.
 * @param <ResultType> type of output data
 * @param <RequestType> type of input data
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundResource() {
        setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource,
                        newData -> setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (result.getValue() == null ||
                !result.getValue().equals(newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        Call<RequestType> apiResponse = createCall();

        result.addSource(dbSource, newData ->
        {
            setValue(Resource.loading(newData));
            result.removeSource(dbSource);
        });
        apiResponse.enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                if (response.body() == null)
                    result.addSource(dbSource,
                            newData -> setValue(Resource.error(CONNECTION_ERROR, newData)));
                else {
                    saveResultAndReInit(response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                onFetchFailed();
                result.addSource(dbSource, newData -> setValue(Resource.error(t.getMessage(),
                        newData)));
            }
        });
    }

    @MainThread
    private void saveResultAndReInit(final RequestType response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), newData -> {
                    setValue(Resource.success(newData));
                });
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();

    @MainThread
    protected void onFetchFailed() {
    }

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}