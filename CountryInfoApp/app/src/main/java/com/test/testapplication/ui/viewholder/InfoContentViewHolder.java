package com.test.testapplication.ui.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.test.testapplication.R;
import com.test.testapplication.model.Information;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moseskesavan on 11/23/17.
 *
 * This view is used for view having no images, only title and description.
 */

public class InfoContentViewHolder extends MasterViewHolder {

    /* Holds the title view instance */
    @BindView(R.id.title_text_view)
    TextView mTitleText;

    /* Holds the description view instance */
    @BindView(R.id.description_text_view)
    TextView mDescriptionText;



    public InfoContentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }


    public void bind(Information information) {
        if (information != null) {
            mTitleText.setText(TextUtils.isEmpty(information.getTitle())? mTitleText.getContext().getString(R.string.empty_desc_text):information.getTitle());
            mDescriptionText.setText(TextUtils.isEmpty(information.getDescription())? mDescriptionText.getContext().getString(R.string.empty_desc_text):information.getDescription());
        }

    }
}
