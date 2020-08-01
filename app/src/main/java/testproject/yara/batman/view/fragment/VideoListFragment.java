package testproject.yara.batman.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import testproject.yara.batman.databinding.VideoListBinding;
import testproject.yara.batman.util.enums.LoadingState;
import testproject.yara.batman.view.adapter.VideoListAdapter;
import testproject.yara.batman.viewmodel.VideoListViewModel;

public class VideoListFragment extends Fragment {

    private VideoListBinding videoListBinding;
    private VideoListViewModel videoListViewModel;
    private VideoListAdapter videoListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (videoListBinding == null) {
            videoListBinding = VideoListBinding.inflate(inflater, container, false);
            videoListViewModel = ViewModelProviders.of(this).get(VideoListViewModel.class);
            videoListBinding.setLifecycleOwner(this);
            videoListViewModel.getVideosLD().observe(getViewLifecycleOwner(), listResource ->
            {
                switch (listResource.getStatus()) {
                    case SUCCESS:
                        videoListAdapter.setData(listResource.getData());
                        videoListBinding.setLoadingState(LoadingState.HIDDEN);
                        break;
                    case ERROR:
                        videoListBinding.setLoadingState(LoadingState.RETRY);
                        videoListBinding.setErrorMessage(listResource.getMessage());
                        break;
                    case LOADING:
                        videoListBinding.setLoadingState(LoadingState.LOADING);
                        break;
                    default:
                        break;
                }
            });
        }
        setupCategoryRecyclerView();
        videoListViewModel.requestVideos();
        videoListBinding.loading.setRetryClickListener(v -> videoListViewModel.requestVideos());
        return videoListBinding.getRoot();
    }

    private void setupCategoryRecyclerView() {
        videoListBinding.videoRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (videoListAdapter == null)
            videoListAdapter = new VideoListAdapter();
        videoListBinding.videoRv.setAdapter(videoListAdapter);
    }
}
