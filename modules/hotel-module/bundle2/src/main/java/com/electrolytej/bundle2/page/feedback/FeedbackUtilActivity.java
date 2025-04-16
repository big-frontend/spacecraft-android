package com.electrolytej.bundle2.page.feedback;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.electrolytej.bundle2.R;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
public class FeedbackUtilActivity extends AppCompatActivity {
    ProgressBar indeterminateBar;
    ProgressBar determinateBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_util);
        indeterminateBar = (ProgressBar) findViewById(R.id.indeterminateBar);
        determinateBar = (ProgressBar) findViewById(R.id.determinateBar);
        determinateBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                ToastUtils.showShort("asdfaf");
            }
        });
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        progressBar.setIndeterminateDrawable(getDrawable(R.drawable.cust_indeterminate_progress_bar));

    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
