package com.test.testapplication.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.test.testapplication.utils.PreferenceManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by moseskesavan on 11/24/17.
 *
 * Fragment that renders the list view with the data binded to it.
 */

public class InfoListFragment extends Fragment {

    /* Tag to log info, error, warning */
    private final static String TAG = InfoListFragment.class.getSimpleName();

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
    private PreferenceManager mPreferences;

    /*Holds the app controller instance */
    private AppController mController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        mUnBinder = ButterKnife.bind(this, view);
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        setAppTitle(mPreferences.getString(AppConstants.TITLE_PREF_KEY));
    }

    /**
     * Initialise the UI components here
     */
    private void initViews() {
        if (mPreferences == null) {
            mPreferences = PreferenceManager.getInstance(mContext);
        }
        mController=AppController.getAppController(getActivity());
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
                if(getView()!=null) {
                    Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNetworkChange(boolean status) {
                if(status){
                    mPreferences.putBoolean(AppConstants.IS_NETWORK_OFF,false);
                    initData();

                }
            }
        };

        mController.registerCallback(callback);

        //Attaching the swipe refresh listener to the layout
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mController.fetchInformationList();
            }
        };
        mSwipeRefreshLayout.setOnRefreshListener(listener);

    }


    /**
     * Initialize the data here
     */
    private void initData() {
        mWaitingProgress.setVisibility(View.VISIBLE);
        List<Information> infoList = mController.fetchInformationListFromDB();
        if (infoList == null || infoList.size() == 0) {
            if (AppUtil.isNetworkConnected(mContext)) {
                mController.fetchInformationList();
            } else {
                mPreferences.putBoolean(AppConstants.IS_NETWORK_OFF, true);
                mWaitingProgress.setVisibility(View.GONE);
                if(getView()!=null){
                    Snackbar.make(getView(), getString(R.string.connectivity_error_text), Snackbar.LENGTH_SHORT).show();
                }
            }
        }else{
            setAdapter(infoList);
            mWaitingProgress.setVisibility(View.GONE);
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
            getActivity().setTitle(title);
            mPreferences.putString(AppConstants.TITLE_PREF_KEY, title);
        } else {
            getActivity().setTitle(getString(R.string.no_data_text));
        }


    }

    @Override
    public void onDestroy() {
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
        mController.unRegisterCallback(callback);
        if (mContext != null) {
            mContext = null;
        }

    }
}
