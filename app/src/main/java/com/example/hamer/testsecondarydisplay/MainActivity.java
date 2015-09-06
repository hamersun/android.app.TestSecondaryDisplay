package com.example.hamer.testsecondarydisplay;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaRouter;
import android.os.Build;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class MainActivity extends Activity {

    MediaRouter router = null;
    MediaRouter.SimpleCallback cb = null;

    SimplePresentation preso = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        WebView wv = new WebView(this);
//        wv.loadUrl("http://www.google.com");
//
//        setContentView(wv);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (cb == null) {
                cb = new RouteCallback();
                router = (MediaRouter) getSystemService(MEDIA_ROUTER_SERVICE);
            }

            handleRoute(router.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO));
            router.addCallback(MediaRouter.ROUTE_TYPE_LIVE_VIDEO, cb);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            clearPreso();

            if (router != null) {
                router.removeCallback(cb);
            }
        }
        super.onStop();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private class RouteCallback extends MediaRouter.SimpleCallback {
        @Override
        public void onRoutePresentationDisplayChanged(MediaRouter router, MediaRouter.RouteInfo info) {
            super.onRoutePresentationDisplayChanged(router, info);

            handleRoute(info);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void handleRoute(MediaRouter.RouteInfo route) {
        if (route == null) {
            clearPreso();
        } else {
            Display display = route.getPresentationDisplay();

            if (route.isEnabled() && display != null) {
                if (preso == null) {
                    showPreso(route);
                    Log.d(getClass().getSimpleName(), "enable route");
                }
                else if (preso.getDisplay().getDisplayId() != display.getDisplayId()) {
                    clearPreso();
                    showPreso(route);
                    Log.d(getClass().getSimpleName(), "switched route");
                }
                else {
                    // no-op
                }
            }
            else {
                clearPreso();
                Log.d(getClass().getSimpleName(), "disabled route");
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showPreso(MediaRouter.RouteInfo route) {
        preso = new SimplePresentation(this, route.getPresentationDisplay());
        preso.show();;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void clearPreso() {
        if (preso != null) {
            preso.dismiss();
            preso = null;
        }
    }

}
