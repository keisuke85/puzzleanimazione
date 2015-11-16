package it.keisoft.puzzleanimazione;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mmarcheselli on 16/11/2015.
 */
public class GalleryDetailActivity extends BaseActivity{

    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;
    private FrameLayout frameLayout;
    private ColorDrawable colorDrawable;
    private ImageView imageView;
    private int ANIM_DURATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_detail_layout);

        //Set the background color to black
        frameLayout = (FrameLayout) findViewById(R.id.main_background);
        colorDrawable = new ColorDrawable(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            frameLayout.setBackground(colorDrawable);
        }
        imageView = (ImageView) findViewById(R.id.image);

        // Only run the animation if we're coming from the parent activity, not if
        // we're recreated automatically by the window manager (e.g., device rotation)
        if (savedInstanceState == null) {
            ViewTreeObserver observer = imageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    imageView.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    imageView.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / imageView.getWidth();
                    mHeightScale = (float) thumbnailHeight / imageView.getHeight();

                    enterAnimation();
                    return true;
                }
            });
        }

        //retrieves the thumbnail data
        Bundle bundle = getIntent().getExtras();
        thumbnailTop = bundle.getInt("top");
        thumbnailLeft = bundle.getInt("left");
        thumbnailWidth = bundle.getInt("width");
        thumbnailHeight = bundle.getInt("height");

        String title = getIntent().getStringExtra("title");
        Bitmap bitmap = getIntent().getParcelableExtra("image");

        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(title);

        imageView.setImageBitmap(bitmap);
    }

    public void enterAnimation() {
        imageView.setPivotX(0);
        imageView.setPivotY(0);
        imageView.setScaleX(mWidthScale);
        imageView.setScaleY(mHeightScale);
        imageView.setTranslationX(mLeftDelta);
        imageView.setTranslationY(mTopDelta);

        // interpolator where the rate of change starts out quickly and then decelerates.
        TimeInterpolator sDecelerator = new DecelerateInterpolator();

        // Animate scale and translation to go from thumbnail to full size
        imageView.animate().setDuration(ANIM_DURATION).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator);

        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0, 255);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }

    public void exitAnimation(final Runnable endAction) {
        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.animate().setDuration(ANIM_DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                    translationX(mLeftDelta).translationY(mTopDelta)
                    .setInterpolator(sInterpolator).withEndAction(endAction);
        }

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }

    @Override
    public void onBackPressed() {
        exitAnimation(new Runnable() {
            public void run() {
                finish();
            }
        });
    }
}
