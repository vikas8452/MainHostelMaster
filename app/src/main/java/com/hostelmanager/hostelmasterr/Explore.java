package com.hostelmanager.hostelmasterr;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Explore extends Fragment implements LocationListener {

    private RecyclerView recyclerview;
    private EditText searchArea;
    LocationManager locationManager;
    private DatabaseReference databaseReference;
    ArrayList<String> hostelNameList;
    ArrayList<String> hostelPicList;
    ArrayList<String> ratingList;
    ArrayList<String> rating_Count;
    SearchAdapter searchAdapter;
    double longitude;
    double latitude;
    static final int REQUEST_LOCATION=1;
    public Explore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        final View view= inflater.inflate(R.layout.fragment_explore, container, false);

       // Toast.makeText(this, "onLocationChanged before", Toast.LENGTH_SHORT).show();


        // Construct a GeoDataClient.
        // mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        //  mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // TODO: Start using the Places API.
        locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        databaseReference= FirebaseDatabase.getInstance().getReference();
//initialising the widgets by their ids

        searchArea=view.findViewById(R.id.searchArea);
        recyclerview=view.findViewById(R.id.recyclerview);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        hostelNameList=new ArrayList<String>();
        hostelPicList=new ArrayList<String>();
        ratingList=new ArrayList<String>();
        rating_Count=new ArrayList<String>();

        searchArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    Toast.makeText(getActivity(), "Entered", Toast.LENGTH_SHORT).show();

                    setAdapter(editable.toString());

                }else{
                    hostelNameList.clear();
                    hostelPicList.clear();
                    ratingList.clear();
                    rating_Count.clear();
                    recyclerview.removeAllViews();
                }
            }
        });
        return view;

    }
    void getLocation()
    {


        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        }
        else {

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            longitude=location.getLongitude();
            latitude=location.getLatitude();
            // onLocationChanged(location);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case REQUEST_LOCATION:
                getLocation();
                break;

        }

    }
    /*  private double getDistanceInfo(double lat1, double lng1, String destinationAddress) {
        StringBuilder stringBuilder = new StringBuilder();
        Double dist = 0.0;
        try {

            destinationAddress = destinationAddress.replaceAll(" ","%20");
            String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + latFrom + "," + lngFrom + "&destination=" + latTo + "," + lngTo + "&mode=driving&sensor=false";

            HttpPost httppost = new HttpPost(url);

            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            stringBuilder = new StringBuilder();


            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = new JSONObject(stringBuilder.toString());

            JSONArray array = jsonObject.getJSONArray("routes");

            JSONObject routes = array.getJSONObject(0);

            JSONArray legs = routes.getJSONArray("legs");

            JSONObject steps = legs.getJSONObject(0);

            JSONObject distance = steps.getJSONObject("distance");

            Log.i("Distance", distance.toString());
            dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]","") );

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dist;
    }

    public class ApiDirectionsAsyncTask extends AsyncTask<URL, Integer, StringBuilder> {

        //private static final String TAG = makeLogTag(ApiDirectionsAsyncTask.class);

        private static final String DIRECTIONS_API_BASE = "https://maps.googleapis.com/maps/api/directions";
        private static final String OUT_JSON = "/json";

        // API KEY of the project Google Map Api For work
        private static final String API_KEY = "YOUR_API_KEY";

        @Override
        protected StringBuilder doInBackground(URL... params) {
          //  Log.i(TAG, "doInBackground of ApiDirectionsAsyncTask");

            HttpURLConnection mUrlConnection = null;
            StringBuilder mJsonResults = new StringBuilder();
            try {
                StringBuilder sb = new StringBuilder(DIRECTIONS_API_BASE + OUT_JSON);
                sb.append("?origin=" + URLEncoder.encode("Your origin address", "utf8"));
                sb.append("&destination=" + URLEncoder.encode("Your destination address", "utf8"));
                sb.append("&key=" + API_KEY);

                URL url = new URL(sb.toString());
                mUrlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(mUrlConnection.getInputStream());

                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1){
                    mJsonResults.append(buff, 0, read);
                }

            } catch (MalformedURLException e) {
            //    Log.e(TAG, "Error processing Distance Matrix API URL");
                return null;

            } catch (IOException e) {
                System.out.println("Error connecting to Distance Matrix");
                return null;
            } finally {
                if (mUrlConnection != null) {
                    mUrlConnection.disconnect();
                }
            }

            return mJsonResults;
        }
    } */

    private void setAdapter(final String searchedString) {

        //   Toast.makeText(getApplicationContext(), "Entered in setAdapter", Toast.LENGTH_SHORT).show();

        databaseReference.child("Hostel").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Toast.makeText(getApplicationContext(), "Entered in on data change", Toast.LENGTH_SHORT).show();
                hostelNameList.clear();
                hostelPicList.clear();
                ratingList.clear();
                rating_Count.clear();
                recyclerview.removeAllViews();
                int counter=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    //   Toast.makeText(getApplicationContext(), "Entered in for", Toast.LENGTH_SHORT).show();
                    String hostelName=snapshot.child("name").getValue(String.class);
                    String hostelPic=snapshot.child("image").getValue(String.class);
                    String rating=snapshot.child("rating").getValue(String.class);
                    String ratingCount=snapshot.child("ratingCount").getValue(String.class)+" Rating";
                    if(hostelName.toLowerCase().contains(searchedString.toLowerCase())){
                        //  Toast.makeText(getApplicationContext(), "Entered in if", Toast.LENGTH_SHORT).show();
                        hostelNameList.add(hostelName);
                        hostelPicList.add(hostelPic);
                        ratingList.add(rating);
                        rating_Count.add(ratingCount);
                        counter++;
                    }
                    if(counter==15)
                        break;
                }
                searchAdapter=new SearchAdapter(getActivity(),hostelNameList,hostelPicList,ratingList,rating_Count,latitude,longitude);
                recyclerview.setAdapter(searchAdapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }





   /* private void firebaseSearch(String searchText){
        Toast.makeText(this, "Started Search", Toast.LENGTH_SHORT).show();

        Query firebaseQuery=databaseReference.orderByChild("name").startAt(searchText).endAt(searchText+"\uf8ff");

        FirebaseRecyclerAdapter<Hostel,userViewHolder> firebaseAdapter=new FirebaseRecyclerAdapter<Hostel, userViewHolder>(

                Hostel.class,
                R.layout.list_layout,
                userViewHolder.class,
                firebaseQuery
        ) {
            @Override
            protected void populateViewHolder(userViewHolder viewHolder, Hostel model, int position) {

                viewHolder.setDetails(getApplicationContext(),model.getName(),model.getImage());
            }
        };
        recyclerview.setAdapter(firebaseAdapter);
    }

    public static class userViewHolder extends RecyclerView.ViewHolder     {
        View mView;
        public userViewHolder(View itemView) {
            super(itemView);

            mView=itemView;
        }
        public void setDetails(Context ctx,String hostelName, String imageHostel){

            TextView hostel_name=mView.findViewById(R.id.hostel_name);
            final ImageView image_hostel=mView.findViewById(R.id.image_hostel);

            hostel_name.setText(hostelName);
            Picasso.with(ctx).load(imageHostel).into(image_hostel);

        }
    }*/

    }


