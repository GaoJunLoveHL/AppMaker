package com.gaojun.appmarket.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.ui.view.LoadingPage;

public class HomeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoadingPage mLoadingPage = new LoadingPage(this) {
            @Override
            public View onCreateSuccessView() {
                return null;
            }

            @Override
            public ResultState onLoad() {
                return null;
            }
        };
    }
}
