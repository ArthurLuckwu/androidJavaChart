package com.project.prototype;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.w3c.dom.Text;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class MyActivityTest {

    private TextView textoInicial;
    private MainActivity mActivity;

    @Before
    public void setup() {
        mActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        textoInicial = (TextView) mActivity.findViewById(R.id.texto);
    }

    @Test
    public void testeConteudoTextView() throws Exception{
        assertNotNull(textoInicial);
    }
}