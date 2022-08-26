package com.aerotom.partner.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aerotom.partner.R;
import com.aerotom.partner.model.User;
import com.aerotom.partner.utils.SessionManager;
import com.onesignal.OneSignal;


import static com.aerotom.partner.utils.SessionManager.login;


public class FirstActivity extends AppCompatActivity {

    SessionManager sessionManager;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        sessionManager = new SessionManager(FirstActivity.this);
        user = sessionManager.getUserDetails("");
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (sessionManager.getBooleanData(login)) {
                        if (!user.getCategoryid().equalsIgnoreCase("0")) {
                            OneSignal.deleteTag("Categoryid");
                            OneSignal.sendTag("PartnerId", user.getId());
                            Intent openMainActivity = new Intent(FirstActivity.this, HomeActivity.class);
                            startActivity(openMainActivity);
                            finish();
                        } else {
                            Intent openMainActivity = new Intent(FirstActivity.this, CategoryActivity.class);
                            startActivity(openMainActivity);
                            finish();
                        }

                    } else {
                        Intent openMainActivity = new Intent(FirstActivity.this, LoginActivity.class);
                        startActivity(openMainActivity);
                        finish();
                    }

                }
            }
        };
        timer.start();
    }
}