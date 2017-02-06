package tetrisii.android.lyon.com.webcampeepers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;


/**
 * Created by Lyon on 2015/10/21.
 */
//20160301 word cloud Lyon start
public class ScreenWeb extends Activity {
    WebView mWebView = null;

    //Vickie 20150829 add
    String URL = null;
    Activity activity;
    TextView url_txt;
    public static final String LOG_TAG = "AppsFlyerSampleAppWeb 2";
    Context context;

    Button button_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.assess_web);
        Bundle bundle =this.getIntent().getExtras();
        activity=this;
        context = getApplicationContext();
        //Vickie 20150829 start
        if(bundle != null){
            URL = bundle.getString("URL");
        }else {
            finish();
        }

        button_url = (Button) findViewById(R.id.button_url);

        button_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mWebView = (WebView)findViewById(R.id.webView);
        url_txt = (TextView)findViewById(R.id.url_txt);
        url_txt.setText(URL);
        url_txt.setVisibility(View.GONE);
        mWebView.setWebViewClient(getWebViewClient());
        mWebView.setWebChromeClient(getWebChromeClient());
        //url_txt.setText(mWebView.getUrl().toString());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheMaxSize(1024 * 8);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.loadUrl(URL);

        //20160330 webView Lyon start
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    Log.d("Lyon","goback");
                    mWebView.goBack();
                    mWebView.destroy();
                    return true;
                }
                return false;
            }
        });
        //20160330 webView Lyon end

    }

    //20160926 Lyon turn off the youtube when finish
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //为了使WebView退出时音频或视频关闭
        mWebView.destroy();
    }


    public void onResume(){
        super.onResume();
        mWebView.onResume();
    }

    public void onPause(){
        super.onPause();

        //20160926 Lyon turn off the youtube when finish
        mWebView.onPause();
        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(mWebView, (Object[]) null);

        } catch(ClassNotFoundException cnfe) {

        } catch(NoSuchMethodException nsme) {

        } catch(InvocationTargetException ite) {

        } catch (IllegalAccessException iae) {

        }
    }



    WebViewClient getWebViewClient() {
        return new EventWebViewClient();
    }
    WebChromeClient getWebChromeClient() {
        return new EventWebChromeClient();
    }
    private class EventWebChromeClient extends WebChromeClient {

        public void onProgressChanged(WebView view, int progress) {

        }
    }

    private class EventWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            url_txt.setText(url);
            Log.d("Lyon","shouldOverrideUrlLoading:"+url);
            return false ;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {


            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedSslError(WebView view, @NonNull SslErrorHandler handler, SslError error) {

            super.onReceivedSslError(view, handler, error);

        }

        @Override
        public void onPageFinished(WebView view, String url) {


        }
    }
}
