package com.example.error;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;


import java.util.List;

/**
 * Created by ruankerui on 2017/5/4.
 */

public class LoginAuthBusiness {

    private static final String TAG = "LoginAuthBusiness";

    private final String SCHEME_TXIM = "istrong.scyxim";

    //istrong.scyxim://jsy.istrong.com/login?username={{username}}&password={{password}}&orgId={{orgId}}&appId={{appId}}&orgName={{orgName}}

    public   boolean isFromOtherApp;
    public String username;
    public  String password;
    public  String orgId;
    public  String orgName;
    public  String appId;


    public LoginAuthBusiness() {

    }

    public void parserIntent(Intent intent) {
        Uri uri = intent.getData();
        if (uri != null) {
            // 完整的url信息
            String url = uri.toString();
            Log.d(TAG, "url: " + uri);
            // scheme部分
            String scheme = uri.getScheme();
            Log.d(TAG, "scheme: " + scheme);
            // host部分
            String host = uri.getHost();
            Log.d(TAG, "host: " + host);
            //port部分
            int port = uri.getPort();
            Log.d(TAG, "port: " + port);
            // 访问路劲
            String path = uri.getPath();
            Log.d(TAG, "path: " + path);
            // Query部分
            String query = uri.getQuery();
            Log.d(TAG, "query: " + query);
            List<String> pathSegments = uri.getPathSegments();
            Log.d(TAG, "pathSegments: " + pathSegments!=null? pathSegments.toString():"null");

            if (SCHEME_TXIM.equals(scheme)) {
                //
                isFromOtherApp = true;
                username = uri.getQueryParameter("username");
                password = uri.getQueryParameter("password");
                orgId = uri.getQueryParameter("orgId");
                orgName = uri.getQueryParameter("orgName");
                appId = uri.getQueryParameter("appId");
            }
            Log.d(TAG, "username: " + username);
        }
    }


}
