package testproject.yara.batman.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import javax.inject.Inject;

import testproject.yara.batman.BatmanApplication;
import testproject.yara.batman.data.datasource.remotedatasource.Resource;
import testproject.yara.batman.data.datasource.VideoRepository;
import testproject.yara.batman.data.model.Video;

public class VideoListViewModel extends BaseViewModel {

    @Inject
    VideoRepository videoRepository;
    private LiveData<Resource<List<Video>>> videosLD;
    private MutableLiveData<Boolean> shouldRefreshData;

    public VideoListViewModel(@NonNull Application application) {
        super(application);
        videosLD = new MutableLiveData<>();
        ((BatmanApplication) application.getApplicationContext()).getApplicationComponent().inject(this);
        shouldRefreshData = new MutableLiveData<>();
        videosLD = Transformations.switchMap(shouldRefreshData, ignored -> videoRepository.getVideos());
    }

    public void requestVideos() {
        this.shouldRefreshData.setValue(true);
    }

    public LiveData<Resource<List<Video>>> getVideosLD() {
        return videosLD;
    }
}
