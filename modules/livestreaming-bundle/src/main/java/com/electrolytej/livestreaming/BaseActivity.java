package com.electrolytej.livestreaming;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.Surface;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.media.camera.preview.R;
import com.media.camera.preview.capture.PreviewFrameHandler;
import com.media.camera.preview.capture.VideoCameraPreview;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends FragmentActivity implements PreviewFrameHandler,
        SimpleGestureFilter.SimpleGestureListener {

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    protected VideoCameraPreview mPreview;
    protected SimpleGestureFilter mDetector;
    protected ResolutionDialog mResolutionDialog;
    protected int mParams;

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mPreview = new VideoCameraPreview(this);
        mDetector = new SimpleGestureFilter(this, this);
        mResolutionDialog = new ResolutionDialog(this);
        mPreview.init(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    protected int getOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        return (ORIENTATIONS.get(rotation) + mPreview.getSensorOrientation() + 270) % 360;
    }

    public void showResolutionDialog(List<Size> items) {
        mResolutionDialog.setItems(items);
        mResolutionDialog.show();
    }

    class BaseDialog extends Dialog {
        RecyclerView mRecyclerView;
        TextView mTextView;

        BaseDialog(@NonNull Context context) {
            super(context);

            setContentView(R.layout.dialog);

            if (getWindow() != null) {
                getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
            mRecyclerView = findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(BaseActivity.this));

            mTextView = findViewById(R.id.text_view);
        }
    }

    public class ResolutionDialog extends BaseDialog {
        private final ItemAdapter<Size> mAdapter;

        ResolutionDialog(@NonNull Context context) {
            super(context);

            mTextView.setText(R.string.select_resolution);
            ArrayList<Size> items = new ArrayList<>();
            ItemAdapter.ItemListener<Size> mListener = item -> {
                dismiss();
                mPreview.changeSize(item);
            };
            mAdapter = new ItemAdapter<>(items, mListener, R.layout.size_list_item);
            mRecyclerView.setAdapter(mAdapter);
        }

        void setItems(List<Size> items) {
            mAdapter.setItems(items);
        }
    }
}
