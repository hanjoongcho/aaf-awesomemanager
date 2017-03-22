package me.blog.korn123.awesomemanager.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import me.blog.korn123.awesomemanager.R;


/**
 * Created by CHO HANJOONG on 2017-02-11.
 */

public class LicensesActivity extends AppCompatActivity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenses);
        webView = (WebView)findViewById(R.id.licenses);
        webView.loadUrl("file:///android_asset/licenses.html");
    }

}
