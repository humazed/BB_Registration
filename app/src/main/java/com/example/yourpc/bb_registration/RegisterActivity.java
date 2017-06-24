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
import com.fourhcode.forhutils.FUtilsValidation;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private final String TAG = "RegisterActivity";

    @BindView(R.id.img_header_logo) ImageView imgHeaderLogo;
    @BindView(R.id.tv_login) TextView tvLogin;
    @BindView(R.id.et_username) EditText etUsername;
    @BindView(R.id.et_id) EditText mEtId;
    @BindView(R.id.et_blood_type) EditText etBloodType;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.et_repeat_password) EditText etRepeatPassword;
    @BindView(R.id.lnlt_inputs_container) LinearLayout lnltInputsContainer;
    @BindView(R.id.tv_already_have_account) TextView tvAlreadyHaveAccount;
    @BindView(R.id.btn_signup) Button btnSignup;
    @BindView(R.id.rllt_body) RelativeLayout rlltBody;
    @BindView(R.id.prgs_loading) ProgressBar prgsLoading;
    @BindView(R.id.rllt_loading) RelativeLayout rlltLoading;
    @BindView(R.id.activity_login) RelativeLayout activityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_already_have_account, R.id.btn_signup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_already_have_account:
                final Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
                break;
            case R.id.btn_signup:
                if (!FUtilsValidation.isEmpty(etUsername, getString(R.string.enter_username))
                        && !FUtilsValidation.isEmpty(mEtId, getString(R.string.enter_id))
                        && !FUtilsValidation.isEmpty(etPassword, getString(R.string.enter_password))
                        && !FUtilsValidation.isEmpty(etBloodType, getString(R.string.enter_blood_type))
                        && !FUtilsValidation.isEmpty(etRepeatPassword, getString(R.string.enter_password_again))
                        && FUtilsValidation.isPasswordEqual(etPassword, etRepeatPassword, getString(R.string.password_isnt_equal))
                        ) {
                    setLoadingMode();
                    // create new user object and set data from editTexts
                    User user = new User();
                    user.username = etUsername.getText().toString();
                    user.id = mEtId.getText().toString();
                    user.bloodType = etBloodType.getText().toString().toLowerCase();
                    user.password = etPassword.getText().toString();
                    user.fcmRegistrationToken = FirebaseInstanceId.getInstance().getToken();

                    Session.getInstance().loginUser(user);
                    // register user with retrofit
                    WebService.getInstance().getApi().registerUser(user).enqueue(new Callback<MainResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<MainResponse> call, @NonNull Response<MainResponse> response) {
                            if (response.body().status == 0) {
                                Toast.makeText(RegisterActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            } else if (response.body().status == 1) {
                                Toast.makeText(RegisterActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            }
                            setNormalMode();
                        }

                        @Override
                        public void onFailure(@NonNull Call<MainResponse> call, @NonNull Throwable t) {
                            Log.e(TAG, t.getMessage());
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
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
