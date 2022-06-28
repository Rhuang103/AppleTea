package com.example.appletea;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static java.lang.Double.parseDouble;

/**
 * This Fragment classes creates a fragment that holds one restaurant object
 */
public class RestaurantInfoFragment extends Fragment {
    private static final String TAG = "RestaurantInfoFragment";

    private static final String ARG_RESTAUR_ID = "resturant_id";

    private Restaurant mRestaurant;
    private EditText mTitleField;
    private EditText mEditDetails;
    private TextView mAddress;
    private TextView mLatitude;
    private TextView mLongitude;

    Location foodLocation;
    String callingActivity;


    /**
     * For when you want the fragment to get the items UUID by itself instead of relying on the
     * activity - currently not in use
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
            mRestaurant.setLatitude(String.valueOf(foodLocation.getLatitude()));
            mRestaurant.setLongitude(String.valueOf(foodLocation.getLongitude()));
            mRestaurant.setAddress(getCompleteAddressString(foodLocation.getLatitude(),
                    foodLocation.getLongitude()));
            Log.i(TAG, getCompleteAddressString(foodLocation.getLatitude(), foodLocation.getLongitude()));
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

        mAddress = (TextView) v.findViewById(R.id.address);
        mAddress.setText(mRestaurant.getAddress());



        mLatitude = (TextView) v.findViewById(R.id.latitude);
        mLatitude.setText(mRestaurant.getLatitude());

        mLongitude = (TextView) v.findViewById(R.id.longitude);
        mLongitude.setText(mRestaurant.getLongitude());

        if(callingActivity == "AppleTeaActivity") {

        }

        return v;
    }

    private void updateRestaurant() {
        FoodLab.get(getActivity()).updateRestaurant(mRestaurant);
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this.getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.i(TAG, "My Current Location Address " + strReturnedAddress.toString());
            } else {
                Log.i(TAG, "My Current Location Address : No Address Returned!" );
                strAdd = "My Current Location Address : No Address Returned!";
                return strAdd;

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "My Current Location Address : Cannot get Address!");
            strAdd = "My Current Location Address : Cannot get Address!";
            return strAdd;
        }
        return strAdd;
    }
}
