package com.project.prototype;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.project.prototype.MainActivity;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    // Solo Robotium helper object
    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    // Setup test case with solo
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void testStartActivity() throws Exception {
        final String fieldValue = "App of graphic examples";

        TextView texto = (TextView) solo.getView(R.id.texto);
        assertEquals("Text should be the field value", fieldValue,
                texto.getText().toString());
    }

    //Finalize solo object
    @Override
    public void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        getActivity().finish();
        super.tearDown();
    }

}