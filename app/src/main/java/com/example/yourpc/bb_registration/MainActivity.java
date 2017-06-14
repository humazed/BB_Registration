package com.example.yourpc.bb_registration;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourpc.bb_registration.models.User;
import com.example.yourpc.bb_registration.utils.Session;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.yourpc.bb_registration.fcm.MyFirebaseMessagingService.KEY_FROM_NOTIFICATION;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_username) TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getStringExtra(KEY_FROM_NOTIFICATION) != null) {
            Toast.makeText(MainActivity.this, "fsdf", Toast.LENGTH_LONG).show();
            showAlertDialog();
        }

        User user = Session.getInstance().getUser();
        if (user != null) {
            tvUsername.setText(user.id);
        }
    }

    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        NotificationDialogFragment alertDialog = NotificationDialogFragment.newInstance("Donate");
        alertDialog.show(fm, "fragment_alert");
    }




}
