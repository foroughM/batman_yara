package testproject.yara.batman.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import testproject.yara.batman.data.model.VideoDetails;
import testproject.yara.batman.databinding.VideoDetailsBinding;
import testproject.yara.batman.util.enums.LoadingState;
import testproject.yara.batman.view.adapter.VideoRateAdapter;
import testproject.yara.batman.viewmodel.VideoDetailsViewModel;

import static testproject.yara.batman.data.Constants.IMDB_BUNDLE_KEY;

public class VideoDetailFragment extends Fragment {

    private VideoDetailsBinding videoDetailsBinding;
    private VideoDetailsViewModel videoDetailsViewModel;
    private VideoRateAdapter videoRateAdapter;
    private String imdbId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().getString(IMDB_BUNDLE_KEY) != null)
            imdbId = getArguments().getString(IMDB_BUNDLE_KEY);
        else imdbId = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (videoDetailsBinding == null) {
            videoDetailsBinding = VideoDetailsBinding.inflate(inflater, container, false);
            videoDetailsViewModel = ViewModelProviders.of(this).get(VideoDetailsViewModel.class);
            videoDetailsBinding.setLifecycleOwner(this);
            videoDetailsViewModel.getVideoDetailsLD().observe(getViewLifecycleOwner(),
                    videoDetailsResource -> {
                        switch (videoDetailsResource.getStatus()) {
                            case SUCCESS:
                                VideoDetails videoDetails = videoDetailsResource.getData();
                                videoDetailsBinding.setVideoDetails(videoDetails);
                                if (videoDetails.getRatingList() != null) {
                                    videoRateAdapter.setData(videoDetails.getRatingList());
                                    videoDetailsBinding.setLoadingState(LoadingState.HIDDEN);
                                }
                                break;
                            case ERROR:
                                videoDetailsBinding.setLoadingState(LoadingState.RETRY);
                                videoDetailsBinding.setErrorMessage(videoDetailsResource.getMessage());
                                break;
                            case LOADING:
                                videoDetailsBinding.setLoadingState(LoadingState.LOADING);
                                break;
                            default:
                                break;
                        }
                    });
        }
        setupRecyclerView();
        if (imdbId.length() != 0)
            videoDetailsViewModel.requestVideoDetails(imdbId);
        videoDetailsBinding.loading.setRetryClickListener(v -> videoDetailsViewModel.requestVideoDetails(imdbId));
        videoDetailsBinding.backBtn.setOnClickListener(v -> {
            if (getActivity() != null)
                getActivity().onBackPressed();
        });
        return videoDetailsBinding.getRoot();
    }

    private void setupRecyclerView() {
        videoDetailsBinding.rateRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        if (videoRateAdapter == null)
            videoRateAdapter = new VideoRateAdapter();
        videoDetailsBinding.rateRv.setAdapter(videoRateAdapter);
    }
}
