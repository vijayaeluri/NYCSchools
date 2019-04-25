package com.nyc.schools.schools;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;

import com.nyc.schools.BR;
import com.nyc.schools.R;
import com.nyc.schools.common.BaseFragment;
import com.nyc.schools.common.INavHelper;
import com.nyc.schools.databinding.FragmentDetailBinding;
import com.nyc.schools.model.School;
import com.nyc.schools.model.SchoolScore;
import com.nyc.schools.utils.CommonUtil;
import com.nyc.schools.viewmodel.SchoolDetailViewModel;

import static com.nyc.schools.utils.Constants.EXTRA_INDEX;
import static com.nyc.schools.utils.Constants.EXTRA_SCHOOL;
import static com.nyc.schools.utils.Constants.EXTRA_TYPE;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class DetailFragment extends BaseFragment<FragmentDetailBinding> implements INavHelper {

    private SchoolDetailViewModel detailViewModel;
    private String listType;
    private School school;
    private SchoolScore schoolScore;
    private int index = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(EXTRA_INDEX)) {
            index = getArguments().getInt(EXTRA_INDEX);
        }
        if (getArguments() != null && getArguments().containsKey(EXTRA_TYPE)) {
            listType = getArguments().getString(EXTRA_TYPE);
        }
        if (getArguments() != null && getArguments().containsKey(EXTRA_SCHOOL)) {
            school = getArguments().getParcelable(EXTRA_SCHOOL);
        }

        setHasOptionsMenu(false);
        if (getActivity() instanceof SchoolsActivity) {
            ((SchoolsActivity) getActivity()).setDrawerEnabled(false);
            ((SchoolsActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void setDataBinding() {

        detailViewModel = ViewModelProviders.of(this).get(SchoolDetailViewModel.class);

        detailViewModel.setContext(getContext());
        detailViewModel.setNavHelper(this);

        if (detailViewModel.getSchoolScoreResponse() == null) {
            detailViewModel.getSchoolScores();
        } else {
            getViewDataBinding().setVariable(BR.school, school);
            getViewDataBinding().executePendingBindings();
        }

        getViewDataBinding().setLifecycleOwner(this);
        getActivity().setTitle(getContext().getString(R.string.school_details));
    }

    @Override
    public void updateUI() {

        this.schoolScore = detailViewModel.getSchoolScoreObject(this.school.getDbn());

        getViewDataBinding().setVariable(BR.school, school);
        getViewDataBinding().setVariable(BR.schoolScore, schoolScore);
        getViewDataBinding().executePendingBindings();

        getViewDataBinding().website.setMovementMethod(LinkMovementMethod.getInstance());
        loadFavoriteImage();
    }

    private void loadFavoriteImage() {

        getViewDataBinding().favIcon.setVisibility(View.VISIBLE);

        if (school.isFavorite()) {
            getViewDataBinding().favIcon.setImageResource(R.drawable.fav);
            getViewDataBinding().favIcon.setContentDescription(school.getSchoolName() + " is marked as favorite.");
        } else {
            getViewDataBinding().favIcon.setImageResource(R.drawable.unfav);
            getViewDataBinding().favIcon.setContentDescription(school.getSchoolName() + " is not favorite. ");
        }
        getViewDataBinding().favIcon.getLayoutParams().width = 80;
        getViewDataBinding().favIcon.getLayoutParams().height = 80;
        getViewDataBinding().favIcon.setAdjustViewBounds(true);

        getViewDataBinding().favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFavorite();
                loadFavoriteImage();
            }
        });
    }

    private void clickFavorite() {

        if (school.isFavorite()) {
            school.setFavorite(false);
        } else {
            school.setFavorite(true);
        }
        detailViewModel.setSchoolToRepo(index, listType, school.isFavorite());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setTitle(CommonUtil.getAppName(getContext()));
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
                }, true,
                R.style.alert_dialog_theme).show();
    }
}
