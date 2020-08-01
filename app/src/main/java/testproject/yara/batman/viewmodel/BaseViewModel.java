package testproject.yara.batman.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends AndroidViewModel {

    private CompositeDisposable compositeDisposable;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}