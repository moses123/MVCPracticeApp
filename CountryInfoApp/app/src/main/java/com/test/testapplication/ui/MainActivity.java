package com.test.testapplication.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.test.testapplication.R;
import com.test.testapplication.controller.AppController;
import com.test.testapplication.controller.ResponseCallback;
import com.test.testapplication.logger.AppLog;
import com.test.testapplication.model.Information;
import com.test.testapplication.ui.adapter.CountryInfoAdapter;
import com.test.testapplication.utils.AppConstants;
import com.test.testapplication.utils.AppUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainActivity extends AppCompatActivity {

    /* Tag to log info, error, warning */
    private final static String TAG = MainActivity.class.getSimpleName();

    /* Holds the swipe refresh layout instance*/
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    /* Holds the recycler view instance*/
    @BindView(R.id.list_view)
    RecyclerView mInfoRecyclerView;

    /* Holds the progress view instance*/
    @BindView(R.id.layout_progress)
    FrameLayout mWaitingProgress;

    /* Holds the error text view instance*/
    @BindView(R.id.error_text)
    TextView mErrorText;

    /* Holds the info adapter instance */
    CountryInfoAdapter mInfoAdapter;

    /*Holds the call back instance*/
    ResponseCallback.InfoCallback callback;

    /*Holds unbinder object returned by butternknife*/
    private Unbinder mUnBinder;

    /*Holds the activity context*/
    private Context mContext;

    /*Holds the shared pref instance*/
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        mUnBinder = ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAppTitle(mPreferences.getString(AppConstants.TITLE_PREF_KEY, ""));
    }

    /**
     * Initialise the UI components here
     */
    private void initViews() {
        if (mPreferences == null) {
            mPreferences = mContext.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        }
        attachCallBack();
        initData();
    }

    /**
     * Register the call back here, as the app controller will give the call back to the UI.
     */
    private void attachCallBack() {

        //Attaching the call back from the controller
        callback = new ResponseCallback.InfoCallback() {

            @Override
            public void onResponse(List<Information> response, String title) {
                // Set refreshing false
                mSwipeRefreshLayout.setRefreshing(false);
                // Make the error text gone as we have data to store
                mErrorText.setVisibility(View.GONE);
                AppLog.d(TAG, "Got the Response");
                // Remove the waiting progress
                mWaitingProgress.setVisibility(View.GONE);
                setAppTitle(title);
                setAdapter(response);
            }

            @Override
            public void onError(Throwable t) {
                // Set refreshing false
                mSwipeRefreshLayout.setRefreshing(false);
                AppLog.d(TAG, "Got an error");
                // Remove the waiting progress
                mErrorText.setVisibility(View.VISIBLE);
                String errorMessage;
                if (t != null) {
                    mWaitingProgress.setVisibility(View.GONE);
                    if (TextUtils.isEmpty(t.getMessage())) {
                        errorMessage = getString(R.string.common_error_text);
                    } else {
                        errorMessage = t.getMessage();
                    }
                } else {
                    errorMessage = getString(R.string.common_error_text);
                }

                Snackbar.make(getWindow().getDecorView().getRootView(), errorMessage, Snackbar.LENGTH_SHORT).show();
            }
        };

        AppController.getAppController().registerCallback(callback);

        //Attaching the swipe refresh listener to the layout
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                AppController.getAppController().fetchInformationList();
            }
        };
        mSwipeRefreshLayout.setOnRefreshListener(listener);

    }


    /**
     * Initialize the data here
     */
    private void initData() {
        mWaitingProgress.setVisibility(View.VISIBLE);
        if (AppUtil.isNetworkConnected(mContext)) {
            List<Information> infoList = AppController.getAppController().fetchInformationListFromDB();
            if (infoList == null || infoList.size() == 0) {
                AppController.getAppController().fetchInformationList();
            } else {
                setAdapter(infoList);
                mWaitingProgress.setVisibility(View.GONE);
            }
        } else {
            mWaitingProgress.setVisibility(View.GONE);
            Snackbar.make(getWindow().getDecorView().getRootView(), getString(R.string.connectivity_error_text), Snackbar.LENGTH_SHORT).show();
        }

    }

    /**
     * Method to set the data to adapter & the adapter to our recyler view.
     *
     * @param infoList list of information object
     */
    private void setAdapter(List<Information> infoList) {

        if (mInfoAdapter == null) {
            mInfoAdapter = new CountryInfoAdapter(infoList);
            mInfoRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mInfoRecyclerView.setAdapter(mInfoAdapter);
        } else {
            mInfoAdapter.setCountryInfoList(infoList);
            mInfoRecyclerView.getAdapter().notifyDataSetChanged();
        }


    }

    /**
     *  save App title in preference and set it in action bar.
     * @param title title
     */
    private void setAppTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
            mPreferences.edit().putString(AppConstants.TITLE_PREF_KEY, title).apply();
        } else {
            setTitle(getString(R.string.no_data_text));
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanupResources();
    }

    /**
     * Release/unregister the resources here.
     */
    private void cleanupResources() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        AppController.getAppController().unRegisterCallback(callback);
        if (mContext != null) {
            mContext = null;
        }

    }
}
