package com.nyc.schools.schools;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nyc.schools.BR;
import com.nyc.schools.R;
import com.nyc.schools.model.School;
import com.nyc.schools.utils.Constants;

import java.util.List;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private Context context;
    private ListCallback callback;
    private List<School> schools;

    public ListAdapter(Context context, ListCallback callback, List<School> schools) {
        this.context = context;
        this.callback = callback;
        this.schools = schools;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewDataBinding binding;
        ImageView iconView;
        TextView textView;
        ImageView favIconView;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            textView = binding.getRoot().findViewById(R.id.title);
            favIconView = binding.getRoot().findViewById(R.id.fav_icon);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            callback.selectedListItem(getAdapterPosition(), schools.get(getAdapterPosition()));
        }

        public void bind(School school) {
            binding.setVariable(BR.school, school);
            binding.executePendingBindings();
        }
    }

    public interface ListCallback {
        void selectedListItem(int index, School school);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.list_item, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {

        final School school = schools.get(position);

        viewHolder.bind(school);

        if (school.isFavorite()) {
            viewHolder.favIconView.setImageResource(R.drawable.fav);
            viewHolder.favIconView.setContentDescription(school.getSchoolName() + " is marked as favorite.");
        } else {
            viewHolder.favIconView.setImageResource(R.drawable.unfav);
            viewHolder.favIconView.setContentDescription(school.getSchoolName() + " is not favorite.");
        }
        viewHolder.favIconView.setAdjustViewBounds(true);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return schools.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Constants.VIEW_TYPE_LIST;
    }
}
