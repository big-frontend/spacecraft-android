package com.hawksjamesf.spacecraft;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.hawksjamesf.common.util.ui.feedback.ToastUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: jf.chen
 * @email: jf.chen@Ctrip.com
 * @since: Apr/28/2019  Sun
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
                ToastUtil.showShort("asdfaf");
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
