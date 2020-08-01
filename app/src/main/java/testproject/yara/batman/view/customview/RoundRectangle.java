package testproject.yara.batman.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import testproject.yara.batman.R;

public class RoundRectangle extends ConstraintLayout {

    private Paint paint;
    private Path path;
    private int backgroundColor;

    private RectF trRect;
    private RectF tlRect;
    private RectF brRect;
    private RectF blRect;

    private float topRightCorner;
    private float topLeftCorner;
    private float bottomRightCorner;
    private float bottomLeftCorner;

    public RoundRectangle(Context context) {
        super(context);
        init(context, null);
    }

    public RoundRectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundRectangle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        setWillNotDraw(false);
        paint = new Paint();
        path = new Path();
        brRect = new RectF();
        blRect = new RectF();
        trRect = new RectF();
        tlRect = new RectF();

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.RoundRectangle,
                    0, 0
            );

            try {
                int color = a.getColor(R.styleable.RoundRectangle_backgroundColor, Color.WHITE);
                float trCorner = a.getDimension(R.styleable.RoundRectangle_topRightCorner, 0);
                float tlCorner = a.getDimension(R.styleable.RoundRectangle_topLeftCorner, 0);
                float brCorner = a.getDimension(R.styleable.RoundRectangle_bottomRightCorner, 0);
                float blCorner = a.getDimension(R.styleable.RoundRectangle_bottomLeftCorner, 0);
                setColor(color);
                setTopRightCorner(trCorner);
                setTopLeftCorner(tlCorner);
                setBottomRightCorner(brCorner);
                setBottomLeftCorner(blCorner);
            } finally {
                a.recycle();
            }
        }

    }

    public void setColor(int color) {
        this.backgroundColor = color;
        invalidate();
    }

    public void setTopRightCorner(float topRightCorner) {
        this.topRightCorner = topRightCorner;
        invalidate();
    }

    public void setTopLeftCorner(float topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
        invalidate();
    }

    public void setBottomLeftCorner(float bottomLeftCorner) {
        this.bottomLeftCorner = bottomLeftCorner;
        invalidate();
    }

    public void setBottomRightCorner(float bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setArcRect();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        path.reset();
        path.moveTo(topLeftCorner, 0);
        path.rLineTo(getWidth() - topRightCorner - topLeftCorner, 0);
        path.lineTo(getWidth(), topRightCorner);
        path.rLineTo(0, getHeight() - topRightCorner - bottomRightCorner);
        path.lineTo(getWidth() - bottomRightCorner, getHeight());
        path.rLineTo(-getWidth() + bottomRightCorner + bottomLeftCorner, 0);
        path.lineTo(0, getHeight() - bottomLeftCorner);
        path.rLineTo(0, -getHeight() + bottomLeftCorner + topLeftCorner);
        path.lineTo(topLeftCorner, 0);

        path.addCircle(topLeftCorner, topLeftCorner, topLeftCorner, Path.Direction.CW);
        path.addCircle(getWidth() - topRightCorner, topRightCorner, topRightCorner, Path.Direction.CW);
        path.addCircle(getWidth() - bottomRightCorner, getHeight() - bottomRightCorner, bottomRightCorner, Path.Direction.CW);
        path.addCircle(bottomLeftCorner, getHeight() - bottomLeftCorner, bottomLeftCorner, Path.Direction.CW);

        canvas.clipPath(path);
        canvas.drawPath(path, paint);
    }

    private void setArcRect() {
        trRect.left = getWidth() - 2 * topRightCorner;
        trRect.top = 0;
        trRect.right = getWidth();
        trRect.bottom = 2 * topRightCorner;

        tlRect.left = 0;
        tlRect.top = 0;
        tlRect.right = 2 * topLeftCorner;
        tlRect.bottom = 2 * topLeftCorner;

        brRect.left = getWidth() - 2 * bottomRightCorner;
        brRect.top = getHeight() - 2 * bottomRightCorner;
        brRect.right = getWidth();
        brRect.bottom = getHeight();

        blRect.left = 0;
        blRect.top = getHeight() - 2 * bottomLeftCorner;
        blRect.right = 2 * bottomLeftCorner;
        blRect.bottom = getHeight();
    }
}
