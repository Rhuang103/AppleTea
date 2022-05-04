package com.example.appletea;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FoodListFragment extends Fragment {
    private static final String TAG = "FoodTeaFragment";

    private EditText mLocationData;
    Location foodLocation;

    private RecyclerView mFoodRecyclerView;
    private FoodAdapter mAdapter;

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
        /**Old view inflater code
        View v = inflater.inflate(R.layout.fragment_foodlist, container, false);

        mLocationData = (EditText)v.findViewById(R.id.locationdata);
        mLocationData.setText(foodLocation.toString());

         return v;
        **/

        View view = inflater.inflate(R.layout.fragment_foodlist_recycler, container, false);

        mFoodRecyclerView = (RecyclerView) view.findViewById(R.id.food_recycler_view);
        mFoodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * Inflated the ViewHolder class with list_item_food layout so it is used as a template
     * to create the recycler view list
     */
    private class FoodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mBestFoodTextView;
        private Restaurant mRestaurant;

        public FoodHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_food, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.restuarant);
            mBestFoodTextView = (TextView) itemView.findViewById(R.id.best_dish);
        }

        public void bind(Restaurant restaurant) {
            mRestaurant = restaurant;
            mTitleTextView.setText(mRestaurant.getTitle());
            mBestFoodTextView.setText(mRestaurant.getBestFood());
        }

        /**
         * When a item on the list is clicked, it opens up a restaurant info view
         */
        @Override
        public void onClick (View view) {
            //Intent intent = new Intent(getActivity(), RestaurantInfoActivity.class);
            Intent intent = RestaurantInfoActivity.pullIntent(getActivity(), mRestaurant.getId(),
                    "FoodListActivity");
            startActivity(intent);
        }
    }

    /**
     * The adapter is called when the RecyclerView needs to create a new viewHolder and combine
     * and object with an existing viewHolder
     */
    private class FoodAdapter extends RecyclerView.Adapter<FoodHolder> {
        private List<Restaurant> mRestauarants;

        public FoodAdapter(List<Restaurant> restaurants) {
            mRestauarants = restaurants;
        }

        @Override
        public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new FoodHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FoodHolder holder, int position) {
            Restaurant restaurant = mRestauarants.get(position);
            holder.bind(restaurant);
        }

        @Override
        public int getItemCount() {
            return mRestauarants.size();
        }
    }

    private void updateUI() {
        FoodLab foodLab = FoodLab.get(getActivity());
        List<Restaurant> restaurants = foodLab.getRestaurants();

        if(mAdapter == null) {
            mAdapter = new FoodAdapter(restaurants);
            mFoodRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

}
