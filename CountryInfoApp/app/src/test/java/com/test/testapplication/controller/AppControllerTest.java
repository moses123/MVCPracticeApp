package com.test.testapplication.controller;

import com.test.testapplication.network.ApiInterface;
import com.test.testapplication.network.NetworkManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import retrofit2.Call;
import retrofit2.Retrofit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by moseskesavan on 11/23/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({NetworkManager.class, Retrofit.class})
public class AppControllerTest {

    private AppController mAppController;

    @Before
    public void setUp() throws Exception {
        mAppController= AppController.getAppController();
    }


    @Test
    public void testFetchInformationList(){
        PowerMockito.mockStatic(NetworkManager.class);
        ApiInterface mockApiService= mock(ApiInterface.class);
        Call mockCall = mock(Call.class);
        Retrofit mockRetrofit= PowerMockito.mock(Retrofit.class);
        when(NetworkManager.getClient()).thenReturn(mockRetrofit);
        when(mockRetrofit.create(ApiInterface.class)).thenReturn(mockApiService);
        when(mockApiService.getCountryData()).thenReturn(mockCall);
        mAppController.fetchInformationList();
    }

}