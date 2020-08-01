package testproject.yara.batman.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import testproject.yara.batman.data.model.VideoRate;
import testproject.yara.batman.databinding.RateListItemBinding;

public class VideoRateAdapter extends RecyclerView.Adapter<VideoRateAdapter.VideoRateViewHolder>
        implements BaseRecyclerViewAdapter<List<VideoRate>> {

    private List<VideoRate> videoRates;

    public VideoRateAdapter() {
        videoRates = new ArrayList<>();
    }

    @NonNull
    @Override
    public VideoRateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoRateViewHolder(RateListItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoRateViewHolder holder, int position) {
        holder.bind(videoRates.get(position));
    }

    @Override
    public int getItemCount() {
        return videoRates == null ? 0 : videoRates.size();
    }

    @Override
    public void setData(List<VideoRate> data) {
        videoRates = data;
        notifyDataSetChanged();
    }

    class VideoRateViewHolder extends RecyclerView.ViewHolder {

        private final RateListItemBinding binding;

        VideoRateViewHolder(@NonNull RateListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(VideoRate videoRate) {
            binding.setRate(videoRate);
            binding.executePendingBindings();
        }
    }
}