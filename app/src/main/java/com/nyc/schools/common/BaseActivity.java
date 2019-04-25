package com.nyc.schools.common;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nyc.schools.R;
import com.nyc.schools.utils.CommonUtil;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    private T viewDataBinding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(CommonUtil.getAppName(this));
        } else {
            setTitle(CommonUtil.getAppName(this));
        }

        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        setDataBinding();
        viewDataBinding.executePendingBindings();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    protected T getViewDataBinding() {
        return viewDataBinding;
    }

    protected abstract int getLayoutId();

    protected abstract void setDataBinding();

    protected void showLoading(int messageId) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(this, R.style.alert_dialog_theme);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
        }
        if (messageId != 0) {
            progressDialog.setMessage(getString(messageId));
        }
        progressDialog.show();
    }

    protected void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    protected void showAlert(String message) {
        CommonUtil.getAlertDialog(this,
                -1,
                message,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                },
                true,
                R.style.alert_dialog_theme).show();
    }

    protected void showNetworkError() {
        CommonUtil.getAlertDialog(this, R.string.no_network_title, getResources().getString(R.string.no_network_message),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface di, int i) {
                        di.dismiss();
                    }
                }, true,
                R.style.alert_dialog_theme).show();
    }

    protected void showApiError(int messageId) {

        CommonUtil.getAlertDialog(this, -1,
                (messageId != 0) ? getResources().getString(messageId) : getResources().getString(R.string.api_error_message),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface di, int i) {
                        di.dismiss();
                    }
                },
                true,
                R.style.alert_dialog_theme).show();
    }
}
