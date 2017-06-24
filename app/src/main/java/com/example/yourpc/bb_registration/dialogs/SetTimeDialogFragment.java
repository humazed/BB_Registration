package com.example.yourpc.bb_registration.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yourpc.bb_registration.R;
import com.example.yourpc.bb_registration.models.MainResponse;
import com.example.yourpc.bb_registration.models.NotificationResponse;
import com.example.yourpc.bb_registration.utils.Session;
import com.example.yourpc.bb_registration.webservices.WebService;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetTimeDialogFragment extends DialogFragment {
    private static final String TAG = SetTimeDialogFragment.class.getSimpleName();

    @BindView(R.id.time_tv) EditText mTimeTv;
    @BindView(R.id.ok_bt) Button mOkBt;

    Unbinder unbinder;

    public SetTimeDialogFragment() {/* Required empty public constructor*/ }

    public static SetTimeDialogFragment newInstance() {
        SetTimeDialogFragment fragment = new SetTimeDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_time_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        getDialog().setTitle("Set donation time");

        mOkBt.setOnClickListener(view1 -> {
            Toast.makeText(getActivity(), "Thank you for you donation", Toast.LENGTH_LONG).show();

            NotificationResponse response = new NotificationResponse();
            response.response = "accepted - " + mTimeTv.getText().toString();
            response.id = Session.getInstance().getUserId();

            WebService.getInstance().getApi().sendResponse(response).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(@NonNull Call<MainResponse> call, @NonNull Response<MainResponse> response) {
                    Log.d(TAG, "onResponse " + response.body());
                }

                @Override
                public void onFailure(@NonNull Call<MainResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });

            getDialog().dismiss();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
