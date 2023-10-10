package com.jamesfchen.vi;

import static com.jamesfchen.vi.render.FpsKt.TAG_FRAME_MONITOR;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

/**
 * Helper class for controlling overlay view with FPS and JS FPS info that gets added directly
 * to @{link WindowManager} instance.
 */
public class DebugOverlayController {

  public static void requestPermission(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      // Get permission to show debug overlay in dev builds.
      if (!Settings.canDrawOverlays(context)) {
        Intent intent =
            new Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.w(TAG_FRAME_MONITOR, "Overlay permissions needs to be granted in order for react native apps to run in dev mode");
        if (canHandleIntent(context, intent)) {
          context.startActivity(intent);
        }
      }
    }
  }

  public static boolean permissionCheck(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      // Get permission to show debug overlay in dev builds.
      if (!Settings.canDrawOverlays(context)) {
        // overlay permission not yet granted
        return false;
      } else {
        return true;
      }
    }
    // on pre-M devices permission needs to be specified in manifest
    return hasPermission(context, Manifest.permission.SYSTEM_ALERT_WINDOW);
  }

  private static boolean hasPermission(Context context, String permission) {
    try {
      PackageInfo info =
          context
              .getPackageManager()
              .getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
      if (info.requestedPermissions != null) {
        for (String p : info.requestedPermissions) {
          if (p.equals(permission)) {
            return true;
          }
        }
      }
    } catch (PackageManager.NameNotFoundException e) {
      Log.e(TAG_FRAME_MONITOR, "Error while retrieving package info", e);
    }
    return false;
  }

  private static boolean canHandleIntent(Context context, Intent intent) {
    PackageManager packageManager = context.getPackageManager();
    return intent.resolveActivity(packageManager) != null;
  }
}
