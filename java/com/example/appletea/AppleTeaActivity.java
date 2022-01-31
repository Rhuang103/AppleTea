package com.example.appletea;

import android.app.Dialog;
import android.content.DialogInterface;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class AppleTeaActivity extends SingleFragmentActivity {
    private static final int REQUEST_ERROR=0;

    @Override
    protected Fragment createFragment() {
        return AppleTeaFragment.newInstance();
    }

    /**
     * Check if Google Play Services are Available
     * Requires com.google.android.gms:play-services-location:10.0.1
     */
    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if(errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability
                    .getErrorDialog(this, errorCode, REQUEST_ERROR,
                            new DialogInterface.OnCancelListener() {

                            @Override
                                public void onCancel(DialogInterface dialog) {
                                //Leave if services are unavailable
                                finish();
                            }
                            });

            errorDialog.show();
        }
    }


}
