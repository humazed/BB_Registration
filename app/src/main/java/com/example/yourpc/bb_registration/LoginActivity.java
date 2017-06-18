package com.example.yourpc.bb_registration;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourpc.bb_registration.models.MainResponse;
import com.example.yourpc.bb_registration.models.User;
import com.example.yourpc.bb_registration.utils.Session;
import com.example.yourpc.bb_registration.webservices.WebService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fourhcode.forhutils.FUtilsValidation.isEmpty;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";

    @BindView(R.id.img_header_logo) ImageView imgHeaderLogo;
    @BindView(R.id.tv_login) TextView tvLogin;
    @BindView(R.id.et_id) EditText etId;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.lnlt_inputs_container) LinearLayout lnltInputsContainer;
    @BindView(R.id.tv_dont_have_account) TextView tvDontHaveAccount;
    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.rllt_body) RelativeLayout rlltBody;
    @BindView(R.id.prgs_loading) ProgressBar prgsLoading;
    @BindView(R.id.rllt_loading) RelativeLayout rlltLoading;
    @BindView(R.id.activity_login) RelativeLayout activityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_dont_have_account, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dont_have_account:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                // override default transation of activity
                this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
                break;
            case R.id.btn_login:
                if (!isEmpty(etId, getString(R.string.enter_id)) && !isEmpty(etPassword, getString(R.string.enter_password))) {
                    setLoadingMode();
                    // create new user
                    final User user = new User();
                    user.id = etId.getText().toString();
                    user.password = etPassword.getText().toString();
                    // login User using Retrofit
                    WebService.getInstance().getApi().loginUser(user).enqueue(new Callback<MainResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<MainResponse> call, @NonNull Response<MainResponse> response) {
                            // check for status value comming from server (response of login-user.php file status)
                            if (response.body().status == 0) { //failed
                                Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                            } else if (response.body().status == 1) { // success
                                login(response);
                            } else {
                                Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            }
                            setNormalMode();
                        }

                        @Override
                        public void onFailure(@NonNull Call<MainResponse> call, @NonNull Throwable t) {
                            Log.e(TAG, t.getLocalizedMessage());
                        }

                        private void login(@NonNull Response<MainResponse> response) {
                            MainResponse body = response.body();

                            Log.d(TAG, "response.body() = " + body);
                            Toast.makeText(LoginActivity.this, body.message, Toast.LENGTH_LONG).show();

                            user.fcmRegistrationToken = body.user.fcmRegistrationToken;
                            user.bloodType = body.user.bloodType;

                            Session.getInstance().loginUser(user);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                }
                break;
        }
    }

    // set loading layout visible and hide body layout
    private void setLoadingMode() {
        rlltLoading.setVisibility(View.VISIBLE);
        rlltBody.setVisibility(View.GONE);
    }

    // set body layout visible and hide loading layout
    private void setNormalMode() {
        rlltLoading.setVisibility(View.GONE);
        rlltBody.setVisibility(View.VISIBLE);
    }
}
