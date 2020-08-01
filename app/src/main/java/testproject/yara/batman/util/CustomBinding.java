package testproject.yara.batman.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import testproject.yara.batman.R;
import testproject.yara.batman.util.enums.LoadingState;
import testproject.yara.batman.view.customview.CustomLoadingView;

public class CustomBinding {

    private CustomBinding() {
    }

    @BindingAdapter("custom:toast")
    public static void showToast(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @BindingAdapter("app:state")
    public static void setCustomLoadingState(CustomLoadingView view, LoadingState state) {
        if (state == null)
            return;

        switch (state) {
            case LOADING:
                view.showLoading();
                break;

            case RETRY:
                view.showRetry();
                break;

            case HIDDEN:
                view.hideDialog();
                break;

            default:
                showToast(view.getRootView(), "UnExpected Error");
        }
    }

    @BindingAdapter("custom:image_url")
    public static void setImage(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background))
                .into(imageView);
    }

    /*
    Show user movie title with IMDB rating
     */
    @BindingAdapter(value = {"custom:title", "custom:time"})
    public static void setDetailsTitle(TextView textView, String title, String time) {
        textView.setText(String.format(title + " (%s)", time));
    }

}
