package testproject.yara.batman.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import javax.inject.Inject;

import testproject.yara.batman.BatmanApplication;
import testproject.yara.batman.data.datasource.remotedatasource.Resource;
import testproject.yara.batman.data.datasource.VideoDetailsRepository;
import testproject.yara.batman.data.model.VideoDetails;

public class VideoDetailsViewModel extends BaseViewModel {

    @Inject
    VideoDetailsRepository videoDetailsRepository;
    private LiveData<Resource<VideoDetails>> videoDetailsLD;
    private MutableLiveData<String> imdbId;

    public VideoDetailsViewModel(@NonNull Application application) {
        super(application);
        videoDetailsLD = new MutableLiveData<>();
        ((BatmanApplication) application.getApplicationContext()).getApplicationComponent()
                .inject(this);
        imdbId = new MutableLiveData<>();
        videoDetailsLD = Transformations.switchMap(imdbId,
                id -> videoDetailsRepository.getVideoDetails(id));
    }

    public void requestVideoDetails(String imdbId) {
        this.imdbId.setValue(imdbId);
    }

    public LiveData<Resource<VideoDetails>> getVideoDetailsLD() {
        return videoDetailsLD;
    }
}
