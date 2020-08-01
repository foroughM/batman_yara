package testproject.yara.batman.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import testproject.yara.batman.data.model.Video;
import testproject.yara.batman.databinding.VideoListItemBinding;
import testproject.yara.batman.view.fragment.VideoListFragmentDirections;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>
        implements BaseRecyclerViewAdapter<List<Video>> {

    private List<Video> videos;

    public VideoListAdapter() {
        videos = new ArrayList<>();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(VideoListItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    @Override
    public void setData(List<Video> data) {
        videos = data;
        notifyDataSetChanged();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        private final VideoListItemBinding binding;

        VideoViewHolder(@NonNull VideoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Video video) {
            binding.setVideo(video);
            binding.categoryHolder.setOnClickListener(v -> Navigation.findNavController(binding.getRoot())
                    .navigate(VideoListFragmentDirections.showVideoDetails(video.getImdbId())));
            binding.executePendingBindings();
        }
    }
}