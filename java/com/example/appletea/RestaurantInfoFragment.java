package com.example.appletea;

import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.UUID;

/**
 * This Fragment classes creates a fragment that holds one restaurant object
 */
public class RestaurantInfoFragment extends Fragment {
    private static final String TAG = "RestaurantInfoFragment";

    private static final String ARG_RESTAUR_ID = "resturant_id";

    private Restaurant mRestaurant;
    private EditText mTitleField;
    private EditText mEditDetails;
    private TextView mLatitude;
    private TextView mLongitude;

    Location foodLocation;
    String callingActivity;

    /**
     * For when you want the fragment to get the items UUID by itself instead of relying on the
     * activity
     */
    public static RestaurantInfoFragment newInstance(UUID restuarantId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESTAUR_ID, restuarantId);

        RestaurantInfoFragment fragment = new RestaurantInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mRestaurant = new Restaurant();


        callingActivity = getActivity().getIntent().getExtras().getString
                (RestaurantInfoActivity.EXTRA_ACTIVITY_INFO);

        if(callingActivity.equals("AppleTeaActivity")) {
            UUID restaurantId = (UUID)getActivity().getIntent().getSerializableExtra
                    (RestaurantInfoActivity.EXTRA_RESTAURANT_ID);
            foodLocation = getActivity().getIntent()
                    .getParcelableExtra(FoodListActivity.LOCATION_DATA);
            mRestaurant = FoodLab.get(getActivity()).getRestaurant(restaurantId);
        }
        else if(callingActivity.equals("FoodListActivity")) {
            //Log.i(TAG, "made it into the if Statement for FoodListActivity");
            UUID restaurantId = (UUID)getActivity().getIntent()
                    .getSerializableExtra(RestaurantInfoActivity.EXTRA_RESTAURANT_ID);
            mRestaurant = FoodLab.get(getActivity()).getRestaurant(restaurantId);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        FoodLab.get(getActivity()).updateRestaurant(mRestaurant);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_info, container, false);

        mTitleField = (EditText) v.findViewById(R.id.restuarant_title);
        if(callingActivity.equals("FoodListActivity")) {
            mTitleField.setText(mRestaurant.getTitle());
        }
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                //space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                    mRestaurant.setTitle(s.toString());
                    updateRestaurant();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //space intentionally left blank
            }

        });

        mEditDetails = (EditText) v.findViewById(R.id.restuarant_detail);
        mEditDetails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                //space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mRestaurant.setDetails(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                //space intentionally left blank
            }
        });

        mLatitude = (TextView) v.findViewById(R.id.latitude);
        mLongitude = (TextView) v.findViewById(R.id.longitude);

        if(callingActivity == "AppleTeaActivity") {

        }

        return v;
    }

    private void updateRestaurant() {
        FoodLab.get(getActivity()).updateRestaurant(mRestaurant);
    }
}
