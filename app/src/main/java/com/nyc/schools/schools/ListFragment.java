package com.nyc.schools.schools;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nyc.schools.R;
import com.nyc.schools.common.BaseFragment;
import com.nyc.schools.common.INavHelper;
import com.nyc.schools.databinding.FragmentListBinding;
import com.nyc.schools.model.DataRepository;
import com.nyc.schools.model.School;
import com.nyc.schools.utils.CommonUtil;
import com.nyc.schools.utils.Constants;
import com.nyc.schools.utils.NavigationUtil;
import com.nyc.schools.viewmodel.SchoolViewModel;
import com.squareup.otto.Subscribe;

import java.util.List;

import static com.nyc.schools.utils.Constants.EXTRA_TYPE;
import static com.nyc.schools.utils.NavigationUtil.setActivityTag;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class ListFragment extends BaseFragment<FragmentListBinding> implements INavHelper, ListAdapter.ListCallback {

    private SchoolViewModel listViewModel;
    private String listType = "";

    public ListFragment() {
        //no op
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(CommonUtil.getAppName(getContext()));

        if (getArguments() != null && getArguments().containsKey(EXTRA_TYPE)) {
            listType = getArguments().getString(EXTRA_TYPE);
        }
        Log.d(ListFragment.class.getSimpleName(), "schools. type = " + listType);
    }

    @Override
    public void onResume() {
        super.onResume();

        setActivityTag(Constants.ActivityTag.SCHOOL_LIST);
        DataRepository.getInstance().getEventBus().register(this);

        setHasOptionsMenu(true);
        if (getActivity() instanceof SchoolsActivity) {
            ((SchoolsActivity) getActivity()).setDrawerEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        DataRepository.getInstance().getEventBus().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void setDataBinding() {

        listViewModel = ViewModelProviders.of(this).get(SchoolViewModel.class);
        listViewModel.setContext(getContext());
        listViewModel.setNavHelper(this);

        getViewDataBinding().setLifecycleOwner(this);

        if (listViewModel.getSchoolResponse() == null) {
            showLoading();
            listViewModel.getSchools();
        } else {
            updateUI();
        }
    }

    public void updateList(String type) {
        this.listType = type;
        updateUI();
    }

    @Override
    public void updateUI() {
        hideLoading();
        bindList(listViewModel.getSchools(listType));

        getViewDataBinding().fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtil.getOkCancelAlert(getContext(), R.string.alert, getResources().getString(R.string.favorites_alert),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int i) {
                                di.dismiss();

                                showLoading();
                                listViewModel.getSchools();
                            }
                        }, true,
                        R.style.alert_dialog_theme).show();
            }
        });
    }

    private void bindList(final List<School> schools) {

        ListAdapter listAdapter = new ListAdapter(getContext(), this, schools);

        RecyclerView recyclerView = getViewDataBinding().recyclerview;
        TextView empty = getViewDataBinding().empty;

        if (schools == null || schools.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);

            if (CommonUtil.getEmptyIfNull(listType).equalsIgnoreCase("fav")) {
                empty.setText(getContext().getString(R.string.empty_text_fav));
            } else {
                empty.setText(getContext().getString(R.string.empty_text));
            }
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);

        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectedListItem(int index, School school) {
        NavigationUtil.loadDetailsFragment(this.getActivity().getSupportFragmentManager(), index, listType, school);
    }

    @Subscribe
    public void repoUpdated(DataRepository.RepoUpdated objectParam) {
        bindList(listViewModel.getSchools(listType));
    }

    @Override
    public void showLoading() {
        super.showLoading(R.string.loading);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showAlert(String message) {
        super.showAlert(message);
    }

    @Override
    public void showNetworkError() {
        super.showNetworkError();
    }

    @Override
    public void showApiError() {
        CommonUtil.getAlertDialog(getContext(), -1, getResources().getString(R.string.api_error_message),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }, true, R.style.alert_dialog_theme).show();
    }
}
