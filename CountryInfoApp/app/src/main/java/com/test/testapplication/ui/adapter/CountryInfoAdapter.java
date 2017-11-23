package com.test.testapplication.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.testapplication.R;
import com.test.testapplication.model.Information;
import com.test.testapplication.ui.viewholder.InfoContentViewHolder;
import com.test.testapplication.ui.viewholder.InfoDefaultViewHolder;
import com.test.testapplication.ui.viewholder.MasterViewHolder;
import com.test.testapplication.utils.AppUtil;

import java.util.List;

/**
 * Created by moseskesavan on 11/21/17.
 *
 * Class which binds the country info data to the UI.
 */

public class CountryInfoAdapter extends RecyclerView.Adapter<MasterViewHolder> {

    /*Constans for the type of view we are going to inflate*/
    public static final int VIEW_TYPE_DEFAULT=1001;
    public static final int VIEW_TYPE_NO_IMAGE=1002;

    private List<Information> mCountryInfo;

    public CountryInfoAdapter(List<Information> countryInfo){
        mCountryInfo=countryInfo;
    }

    public void setCountryInfoList(List<Information> countryInfo){
        if(mCountryInfo!=null){
            mCountryInfo.clear();
        }
        mCountryInfo=countryInfo;
    }

    @Override
    public MasterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView ;
        MasterViewHolder holder;
        switch (viewType){

            case VIEW_TYPE_NO_IMAGE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_content_only, parent, false);
                holder= new InfoContentViewHolder(itemView);
                break;

                default:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
                    holder= new InfoDefaultViewHolder(itemView);
                    break;
        }

        return holder;

    }

    @Override
    public void onBindViewHolder(MasterViewHolder holder, int position) {
        holder.bind(mCountryInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return mCountryInfo!=null?mCountryInfo.size():0;
    }

    @Override
    public void onViewRecycled(MasterViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return AppUtil.getItemViewType( mCountryInfo.get(position));
    }
}
