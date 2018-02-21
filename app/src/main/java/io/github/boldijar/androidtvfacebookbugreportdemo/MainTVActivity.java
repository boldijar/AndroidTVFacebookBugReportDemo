/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package io.github.boldijar.androidtvfacebookbugreportdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.DeviceLoginManager;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;

import java.util.Collections;

public class MainTVActivity extends Activity {

    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tv);
        FacebookSdk.setApplicationId(Values.APP_ID);
        FacebookSdk.setClientToken(Values.CLIENT_TOKEN);
        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                showLoginDialog();
            }
        });
    }

    private void showLoginDialog() {
        callbackManager = CallbackManager.Factory.create();
        DeviceLoginManager loginManager = DeviceLoginManager.getInstance();
        loginManager.setLoginBehavior(LoginBehavior.DEVICE_AUTH);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainTVActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainTVActivity.this, "Login cancel!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainTVActivity.this, "Login error! " + error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        DeviceLoginManager.getInstance().logInWithReadPermissions(this, Collections.EMPTY_LIST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
