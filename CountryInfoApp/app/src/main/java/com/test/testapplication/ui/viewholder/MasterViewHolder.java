package com.test.testapplication.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test.testapplication.model.Information;

/**
 * Created by moseskesavan on 11/23/17.
 * This is the parent class of the all the view holder to be used in the info list redering.
 * All the item related to the info will extend this as the adapter will only generate this view holder.
 */

public class MasterViewHolder extends RecyclerView.ViewHolder {

    public MasterViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Information information) {
    }
}
