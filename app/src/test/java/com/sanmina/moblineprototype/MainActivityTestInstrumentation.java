package com.sanmina.moblineprototype;


import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

import com.sanmina.moblineprototype.MainActivity;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;

public class MainActivityTestInstrumentation extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;

    public MainActivityTestInstrumentation() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }


    @UiThreadTest
    public void testMyActivityAppearsAsExpectedInitially() {
        assertThat(mActivity.textoInicial).isVisible();
        assertThat(mActivity.textoInicial).hasText("App of graphic examples");

    }

}
