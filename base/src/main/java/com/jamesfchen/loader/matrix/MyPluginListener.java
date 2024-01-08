package com.jamesfchen.loader.matrix;

import android.content.Context;

import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.util.MatrixLog;

public class MyPluginListener extends DefaultPluginListener {
    public static final String TAG = "Matrix.TestPluginListener";

    public MyPluginListener(Context context) {
        super(context);

    }

    @Override
    public void onReportIssue(Issue issue) {
//        super.onReportIssue(issue);
        MatrixLog.e(TAG, issue.toString());

        //add your code to process data
    }
}