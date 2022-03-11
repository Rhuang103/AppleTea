package com.example.appletea;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

/**
 * Currently, LOCATION DATA is null
 */

public class FoodListFragment extends Fragment {
    private static final String TAG = "FoodTeaFragment";

    private EditText mLocationData;
    Location foodLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Location object is Parcelable instead of Serializable
        foodLocation = getActivity().getIntent()
                .getParcelableExtra(FoodListActivity.LOCATION_DATA);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foodlist, container, false);

        mLocationData = (EditText)v.findViewById(R.id.locationdata);
        mLocationData.setText(foodLocation.toString());

        return v;
    }
}
