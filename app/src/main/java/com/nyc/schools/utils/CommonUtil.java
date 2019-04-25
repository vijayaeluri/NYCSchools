package com.nyc.schools.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.nyc.schools.R;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class CommonUtil {

    public static String getAppName(Context context) {
        return context.getString(R.string.app_name);
    }

    public static boolean isEmptyString(String text) {
        return null == text || text.isEmpty();
    }

    public static String getEmptyIfNull(String text) {
        return null == text || text.isEmpty() ? "" : text;
    }

    public static AlertDialog getAlertDialog(Context context, int titleID, String message, DialogInterface.OnClickListener onclickListener, boolean isCancelable, int themeResId) {
        AlertDialog.Builder builder;
        builder = themeResId != -1 ? new AlertDialog.Builder(context, themeResId) : new AlertDialog.Builder(context);
        if (titleID != -1) {
            builder.setTitle(context.getResources().getString(titleID));
        }
        builder.setMessage(CommonUtil.isEmptyString(message) ? "Alert" : message);

        builder.setPositiveButton(context.getResources().getString(R.string.ok), onclickListener);
        builder.setCancelable(isCancelable);
        return builder.show();
    }

    public static AlertDialog getOkCancelAlert(Context context, int titleID, String message, DialogInterface.OnClickListener onclickListener, boolean isCancelable, int themeResId) {
        AlertDialog.Builder builder;
        builder = themeResId != -1 ? new AlertDialog.Builder(context, themeResId) : new AlertDialog.Builder(context);
        if (titleID != -1) {
            builder.setTitle(context.getResources().getString(titleID));
        }
        builder.setMessage(CommonUtil.isEmptyString(message) ? "Alert" : message);

        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface di, int i) {
                di.dismiss();
            }
        });
        builder.setPositiveButton(context.getResources().getString(R.string.ok), onclickListener);
        builder.setCancelable(isCancelable);
        return builder.show();
    }
}
