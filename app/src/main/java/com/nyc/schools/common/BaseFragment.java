package com.nyc.schools.common;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyc.schools.R;
import com.nyc.schools.utils.CommonUtil;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    private T viewDataBinding;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(CommonUtil.getAppName(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        setDataBinding();
        viewDataBinding.executePendingBindings();

        //setHasOptionsMenu(true);

        return viewDataBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        super.onStop();
    }

    protected T getViewDataBinding() {
        return viewDataBinding;
    }

    protected abstract int getLayoutId();

    protected abstract void setDataBinding();


    protected void showLoading(int messageId) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(getContext(), R.style.alert_dialog_theme);
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
        CommonUtil.getAlertDialog(getContext(),
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
        CommonUtil.getAlertDialog(getContext(), R.string.no_network_title,
                getResources().getString(R.string.no_network_message),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface di, int i) {
                        di.dismiss();
                    }
                }, true,
                R.style.alert_dialog_theme).show();
    }

    protected void showApiError(int messageId) {

        CommonUtil.getAlertDialog(getContext(), -1,
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
