package com.jamesfchen.bundle2.optimization;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.jamesfchen.bundle2.databinding.ActivityOptimizationBinding;


public class OptimizationActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOptimizationBinding binding= ActivityOptimizationBinding.inflate(getLayoutInflater());
        binding.eidt.setOnTextChangedListener(text -> Log.d("cjf", "onTextChange:" + text));
        setContentView(binding.getRoot());
    }
}
