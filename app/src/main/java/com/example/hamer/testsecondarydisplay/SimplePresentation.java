package com.example.hamer.testsecondarydisplay;

import android.annotation.TargetApi;
import android.app.Presentation;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.webkit.WebView;

/**
 * Created by hamer on 2015/9/5.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class SimplePresentation extends Presentation {

    public SimplePresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        WebView wv = new WebView(getContext());
//        wv.loadUrl("http://www.google.com");
//
//        setContentView(wv);
        setContentView(R.layout.secondary_layout);
    }


}


