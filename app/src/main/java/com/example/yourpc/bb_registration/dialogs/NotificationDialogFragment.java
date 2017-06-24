package com.example.yourpc.bb_registration.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.yourpc.bb_registration.R;
import com.example.yourpc.bb_registration.models.MainResponse;
import com.example.yourpc.bb_registration.models.NotificationResponse;
import com.example.yourpc.bb_registration.utils.Session;
import com.example.yourpc.bb_registration.webservices.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationDialogFragment extends DialogFragment {
    private static final String TAG = NotificationDialogFragment.class.getSimpleName();


    public NotificationDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static NotificationDialogFragment newInstance(String title) {
        NotificationDialogFragment frag = new NotificationDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme)
                .setTitle(title)
                .setMessage("Donate please!")
                .setPositiveButton("Donate", (dialog, which) -> {
                    Log.d(TAG, "onCreateDialog " + "Postive");
                    showAlertDialog();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    if (dialog != null) dialog.dismiss();
                    NotificationResponse response = new NotificationResponse();
                    response.response = "rejected";
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
                });
        return alertDialogBuilder.create();
    }

    private void showAlertDialog() {
        SetTimeDialogFragment alertDialog = SetTimeDialogFragment.newInstance();
        alertDialog.show(getActivity().getSupportFragmentManager(), "fragment_alert");
    }
}
