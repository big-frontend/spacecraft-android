package com.hawksjamesf.spacecraft.ui.fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hawksjamesf.spacecraft.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Jan/27/2019  Sun
 */
public class DeepLinkFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deep_link, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) view).setText(DeepLinkFragment.this.getArguments().getString("args_deep_link"));
            }
        });
        final EditText etEditArgs = view.findViewById(R.id.et_edit_args);
        view.findViewById(R.id.send_notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("args_deep_link", etEditArgs.getText().toString());
                PendingIntent pendingIntent = Navigation.findNavController(view).createDeepLink()
                        .setDestination(R.id.fragment_deep_link)
                        .setArguments(bundle)
                        .createPendingIntent();
                NotificationManager nm = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                String id = "deep_link";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    nm.createNotificationChannel(new NotificationChannel(id, "deep link", NotificationManager.IMPORTANCE_HIGH));
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), id)
                        .setContentTitle("navigation")
                        .setContentText("deep link to android")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                nm.notify(0, builder.build());

            }
        });

    }
}
