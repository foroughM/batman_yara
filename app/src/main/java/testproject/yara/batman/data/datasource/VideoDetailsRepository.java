package testproject.yara.batman.data.datasource;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import testproject.yara.batman.data.api.BatmanApi;
import testproject.yara.batman.data.model.VideoDetails;

import static testproject.yara.batman.data.Constants.APIKEY;

@Singleton
public class VideoDetailsRepository {

    private BatmanApi batmanApi;
    private VideoDetailsDao videoDetailsDao;

    @Inject
    VideoDetailsRepository(BatmanApi batmanApi, VideoDetailsDao videoDetailsDao) {
        this.batmanApi = batmanApi;
        this.videoDetailsDao = videoDetailsDao;
    }

    public LiveData<Resource<VideoDetails>> getVideoDetails(String imdbId) {
        return new NetworkBoundResource<VideoDetails, VideoDetails>() {
            @Override
            protected void saveCallResult(@NonNull VideoDetails data) {
                videoDetailsDao.delete(imdbId);
                data.setUpdateDate(System.currentTimeMillis());
                videoDetailsDao.saveAll(data);
            }

            @Override
            protected boolean shouldFetch(@Nullable VideoDetails data) {
                return videoDetailsDao.isExpired(data);
            }

            @NonNull
            @Override
            protected LiveData<VideoDetails> loadFromDb() {
                return videoDetailsDao.getVideoDetails(imdbId);
            }

            @NonNull
            @Override
            protected Call<VideoDetails> createCall() {
                return batmanApi.getVideoDetails(APIKEY, imdbId);
            }
        }.getAsLiveData();
    }

}
