package testproject.yara.batman.data.datasource;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import testproject.yara.batman.data.api.BatmanApi;
import testproject.yara.batman.data.datasource.localdatasource.VideoDao;
import testproject.yara.batman.data.datasource.remotedatasource.NetworkBoundResource;
import testproject.yara.batman.data.datasource.remotedatasource.Resource;
import testproject.yara.batman.data.model.RemoteResponse;
import testproject.yara.batman.data.model.Video;

import static testproject.yara.batman.data.Constants.APIKEY;
import static testproject.yara.batman.data.Constants.SERVICE_NAME;

@Singleton
public class VideoRepository {

    private BatmanApi batmanApi;
    private VideoDao videoDao;

    @Inject
    public VideoRepository(BatmanApi batmanApi, VideoDao videoDao) {
        this.batmanApi = batmanApi;
        this.videoDao = videoDao;
    }

    public LiveData<Resource<List<Video>>> getVideos() {
        return new NetworkBoundResource<List<Video>, RemoteResponse>() {
            @Override
            protected void saveCallResult(@NonNull RemoteResponse item) {
                videoDao.deleteAll();
                for (Video video : item.getResponseData())
                    video.setUpdateDate(System.currentTimeMillis());
                videoDao.saveAll(item.getResponseData());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Video> data) {
                return videoDao.isExpired(data);
            }

            @NonNull
            @Override
            protected LiveData<List<Video>> loadFromDb() {
                return videoDao.getVideos();
            }

            @NonNull
            @Override
            protected Call<RemoteResponse> createCall() {
                return batmanApi.getVideos(APIKEY, SERVICE_NAME);
            }
        }.getAsLiveData();
    }

}
