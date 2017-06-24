package com.example.yourpc.bb_registration;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.yourpc.bb_registration.dialogs.NotificationDialogFragment;
import com.example.yourpc.bb_registration.models.User;
import com.example.yourpc.bb_registration.utils.Session;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.yourpc.bb_registration.fcm.MyFirebaseMessagingService.KEY_FROM_NOTIFICATION;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tv_username) TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getStringExtra(KEY_FROM_NOTIFICATION) != null) {
            showAlertDialog();
        }

        User user = Session.getInstance().getUser();
        if (user != null) {
            Log.d(TAG, "user = " + user);
            tvUsername.setText(user.id);
            FirebaseMessaging.getInstance().subscribeToTopic(user.bloodType);
        }
    }

    private void showAlertDialog() {
        NotificationDialogFragment alertDialog = NotificationDialogFragment.newInstance("Donate");
        alertDialog.show(getSupportFragmentManager(), "fragment_alert");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                Session.getInstance().logoutAndGoToLogin(this);
                return true;
            case R.id.about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
