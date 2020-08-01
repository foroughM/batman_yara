package testproject.yara.batman.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import testproject.yara.batman.R;
import testproject.yara.batman.util.enums.LoadingState;


public class CustomLoadingView extends ConstraintLayout {
    private ImageView mIvRetry;
    private ProgressBar mPbLoading;
    private OnClickListener mOnRetryClickListener;
    private LoadingState loadingState;

    public CustomLoadingView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CustomLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.custom_loading_view, this);
        mIvRetry = findViewById(R.id.ivRetry);
        mPbLoading = findViewById(R.id.pbLoading);
        mIvRetry.setOnClickListener(v -> mOnRetryClickListener.onClick(v));
        setVisibility(GONE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void showRetry() {
        setVisibility(VISIBLE);
        mIvRetry.setVisibility(VISIBLE);
        mPbLoading.setVisibility(GONE);
        invalidate();
    }

    public void showLoading() {
        setVisibility(VISIBLE);
        mIvRetry.setVisibility(GONE);
        mPbLoading.setVisibility(VISIBLE);
        invalidate();
    }

    public void hideDialog() {
        setVisibility(GONE);
        invalidate();
    }

    public void setRetryClickListener(OnClickListener onClickListener) {
        mOnRetryClickListener = onClickListener;
    }
}
