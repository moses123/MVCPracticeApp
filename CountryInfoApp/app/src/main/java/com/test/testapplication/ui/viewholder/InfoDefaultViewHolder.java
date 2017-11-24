package com.test.testapplication.ui.viewholder;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.test.testapplication.R;
import com.test.testapplication.model.Information;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * Created by moseskesavan on 11/21/17.
 * <p>
 * View holder class which holds individual info data.
 */

public class InfoDefaultViewHolder extends MasterViewHolder {

    /* Holds the title view instance */
    @BindView(R.id.title_text_view)
     TextView mTitleText;

    /* Holds the description view instance */
    @BindView(R.id.description_text_view)
    FlowTextView mDescriptionText;

    /* Holds the image view instance */
    @BindView(R.id.info_image)
     ImageView mImageView;

    public InfoDefaultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    /**
     * Binds the data with the UI.
     * @param information info object.
     */
    public void bind(Information information) {
        if (information != null) {
                mTitleText.setText(TextUtils.isEmpty(information.getTitle())? mTitleText.getContext().getString(R.string.empty_title_text):information.getTitle());
            String description=TextUtils.isEmpty(information.getDescription())? mDescriptionText.getContext().getString(R.string.empty_desc_text):information.getDescription();
            Spanned html = Html.fromHtml("<p>"+description+"</p>");
            mDescriptionText.setText(html);
            mDescriptionText.setTextSize(mDescriptionText.getContext().getResources().getDimension(R.dimen.feed_item_description_size));
            downloadImage(information.getImageUrl());
        }

    }

    /**
     * Method to download image using glide.
     */
    private void downloadImage(String url) {
        if (TextUtils.isEmpty(url)) {
            setPlaceHolderImage();
        } else {
            Glide
                    .with(mImageView.getContext())
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                           setPlaceHolderImage();
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            return false;
                        }
                    })
                    .into(mImageView);
        }
    }

    /**
     *  Set default image if image not available.
     */
    private void setPlaceHolderImage(){
        Drawable drawable= ContextCompat.getDrawable(mImageView.getContext(),R.drawable.image_place_holder);
        mImageView.setImageDrawable(drawable);
    }


}
