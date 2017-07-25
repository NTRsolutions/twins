package com.apreciasoft.admin.asremis.Fracments;

import android.Manifest;
        import android.app.Activity;
        import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Geocoder;
        import android.location.Location;
import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.annotation.RequiresApi;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.apreciasoft.admin.asremis.Entity.InfoTravelEntity;
import com.apreciasoft.admin.asremis.Entity.TravelLocationEntity;
import com.apreciasoft.admin.asremis.Http.HttpConexion;
import com.apreciasoft.admin.asremis.R;
        import com.apreciasoft.admin.asremis.Util.DataParser;
import com.apreciasoft.admin.asremis.Util.GlovalVar;
import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.location.LocationListener;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.BitmapDescriptor;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Timer;
        import java.util.TimerTask;


/**
 * Created by Admin on 04/01/2017.
 */

public class HomeClientFragment extends Fragment implements  OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {

    public List<LatLng> listPosition = new ArrayList<>();
    static GoogleMap mGoogleMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public static Location mLastLocation;
    public  static  String nameLocation;
    public static PolylineOptions options;
    Marker mCurrLocationMarker;
    public  boolean isFistLocation = true;
    public static ArrayList<LatLng> MarkerPoints;
    public  Timer timer;
    public static Location getmLastLocation() {
        return mLastLocation;
    }
    private static View view;
    public static TextView txtStatus = null;
    public static TextView txt_client_info = null;
    public static TextView txt_destination_info = null;
    public static TextView txt_origin_info = null;
    public static TextView txt_km_info = null;
    public static TextView txt_calling_info = null;
    public static TextView txt_domain = null;
    public static TextView txt_amount_info = null;

    public static void setmLastLocation(Location mLastLocation) {
        HomeClientFragment.mLastLocation = mLastLocation;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.fragment_home,container,false);




        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_home_client,container,false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }






        return view;




    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fr = (MapFragment)getChildFragmentManager().findFragmentById(R.id.gmap);

        if(fr == null) {
            MapFragment  mMap = ((MapFragment) this.getFragmentManager().findFragmentById(R.id.gmap));
            mMap.getMapAsync(this);



        }
        else {
            fr.getMapAsync(this);
        }

        HomeClientFragment.txtStatus = (TextView) getActivity().findViewById(R.id.txtStatus);
      //  HomeClientFragment.txt_client_info = (TextView) getActivity().findViewById(R.id.txt_client_info);
        HomeClientFragment.txt_destination_info = (TextView) getActivity().findViewById(R.id.txt_destination_info);
        HomeClientFragment.txt_origin_info = (TextView) getActivity().findViewById(R.id.txt_origin_info);
        HomeClientFragment.txt_km_info = (TextView) getActivity().findViewById(R.id.txt_km_info);
        HomeClientFragment.txt_amount_info = (TextView) getActivity().findViewById(R.id.txt_amount_info);
        HomeClientFragment.txt_calling_info = (TextView) getActivity().findViewById(R.id.txt_calling_info);
        HomeClientFragment.txt_domain = (TextView) getActivity().findViewById(R.id.txt_domain);



       txtStatus.setText("SERVICIO ACTIVO");


       // panelTopIsVisible(false);


    }





    public static void clearInfo() {
//        HomeClientFragment.txt_client_info.setText("N/A");
        HomeClientFragment.txt_calling_info.setText("N/A");
        HomeClientFragment.txt_domain.setText("N/A");
        HomeClientFragment.txt_destination_info.setText("N/A");
        HomeClientFragment.txt_origin_info.setText("N/A");
        HomeClientFragment.txt_km_info.setText("0Km");
        HomeClientFragment.txt_amount_info.setText("0$");
        HomeClientFragment.txtStatus.setText("SERVICIO ACTIVO");
        getPick(-1);

    }

    public static void setInfoTravel(InfoTravelEntity currentTravel)
    {

        if(currentTravel != null) {

            if (view != null) {

                Log.d("currentTravel", String.valueOf(currentTravel));

                if(HomeClientFragment.txt_client_info != null)
                {
                    HomeClientFragment.txt_client_info.setText(currentTravel.getDriver());
                }

                HomeClientFragment.txt_calling_info.setText(currentTravel.getPhoneNumber());
                HomeClientFragment.txt_domain.setText(currentTravel.getDomain());


                HomeClientFragment.txt_destination_info.setText(currentTravel.getNameDestination());
                HomeClientFragment.txt_origin_info.setText(currentTravel.getNameOrigin());
                HomeClientFragment.txt_km_info.setText(currentTravel.getDistanceLabel());
                HomeClientFragment.txtStatus.setText(currentTravel.getNameStatusTravel());

                getPick(currentTravel.getIdUserDriver());// PASAMOS EL ID DE EL USUARIO DE EL CHOFER

                HomeClientFragment.txtStatus.setTextColor(Color.parseColor(currentTravel.getClassColorTwo()));

                if (currentTravel.getIdSatatusTravel() == 6) {
                    if (currentTravel.getAmountCalculate() != null) {
                        HomeClientFragment.txt_amount_info.setText(currentTravel.getTotalAmount() + "$");
                    }
                } else {

                    if (currentTravel.getAmountCalculate() != null) {
                        HomeClientFragment.txt_amount_info.setText(currentTravel.getAmountCalculate() + "$");
                    }
                }
            }
        }


    }

    // METODO OBTENER FOTO DE CHOFER //
    public static void getPick(int idUserDriver)
    {
        HomeClientFragment.DowloadImg dwImg = new HomeClientFragment.DowloadImg();
        dwImg.execute(HttpConexion.BASE_URL+HttpConexion.base+"/Frond/img_users/"+idUserDriver);

    }

    public static class DowloadImg extends AsyncTask<String, Void, Bitmap> {



        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            String url = params[0]+".JPEG";
            Bitmap imagen = descargarImagen(url);
            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            ImageView your_imageView = (ImageView) view.findViewById(R.id.img_face_client);
            ImageView your_imageView2 = (ImageView) view.findViewById(R.id.img_face_client2);

            if(result != null)
            {
                your_imageView.setImageBitmap(result);
                your_imageView2.setImageBitmap(result);

            }else
            {
                your_imageView.setImageResource(R.drawable.noimg);
                your_imageView2.setImageResource(R.drawable.noimg);
            }



        }

        private Bitmap descargarImagen (String imageHttpAddress){
            URL imageUrl = null;
            Bitmap imagen = null;
            try{
                imageUrl = new URL(imageHttpAddress);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                imagen = BitmapFactory.decodeStream(conn.getInputStream());
            }catch(IOException ex){
                ex.printStackTrace();
            }

            return imagen;
        }

    }



    @Override
    public void onPause() {
        super.onPause();

       /* try {
            //stop location updates when Activity is no longer active
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }

        }catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }*/


    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {



            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(2));

                if (MarkerPoints != null) {
                    MarkerPoints.clear();

                   if(mGoogleMap != null)
                   {
                       mGoogleMap.clear();
                   }

                }

            } else {
                //Request Location Permission
                checkLocationPermission();
                if (MarkerPoints != null) {
                    MarkerPoints.clear();

                    if(mGoogleMap != null)
                    {
                        mGoogleMap.clear();
                    }

                }
            }

        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(2));

            if (MarkerPoints != null) {
                MarkerPoints.clear();

                if(mGoogleMap != null)
                {
                    mGoogleMap.clear();
                }

            }
        }

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


        // Setting onclick event listener for the map
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {


            }
        });






    }

    public  void setDirection(LatLng point)
    {



        // Already two locations
        if (MarkerPoints.size() > 1) {
            MarkerPoints.clear();
            mGoogleMap.clear();
        }

        // Adding new item to the ArrayList
        MarkerPoints.add(point);

        // Creating MarkerOptions
        MarkerOptions optionsDriving = new MarkerOptions();

        // Setting the position of the marker
        optionsDriving.position(point);

        /**
         * For the start location, the color of marker is GREEN and
         * for the end location, the color of marker is RED.
         */
        if (MarkerPoints.size() == 1) {
            optionsDriving.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else if (MarkerPoints.size() == 2) {
            optionsDriving.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }


        // Add new marker to the Google Map Android API V2
        mGoogleMap.addMarker(optionsDriving);

        // Checks, whether start and end locations are captured
        if (MarkerPoints.size() >= 2) {
            LatLng origin = MarkerPoints.get(0);
            LatLng dest = MarkerPoints.get(1);

            // Getting URL to the Google Directions API
            String url = getUrl(origin, dest);
            FetchUrl FetchUrl = new FetchUrl();

            // Start downloading json data from Google Directions API
            FetchUrl.execute(url);
            //move map camera
            // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(2));
        }

    }

    private static String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    protected synchronized void buildGoogleApiClient() {

        try
        {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


            this._activeTimerMap();

           /* if(HomeClientActivity.currentTravel != null)
            {



                // Initializing
                MarkerPoints = new ArrayList<>();

                LatLng origuin = new LatLng(Double.parseDouble(HomeActivity.currentTravel.getLatOrigin()),Double.parseDouble(HomeActivity.currentTravel.getLonOrigin()));
                setDirection(origuin);



                if(HomeActivity.currentTravel.getLatDestination() != null) {
                    LatLng desination = new LatLng(Double.parseDouble(HomeActivity.currentTravel.getLatDestination()),Double.parseDouble(HomeActivity.currentTravel.getLonDestination()));
                    setDirection(desination);
                }



            }else
            {

                if(MarkerPoints != null) {
                    MarkerPoints.clear();
                }

                if(mGoogleMap != null) {
                    mGoogleMap.clear();
                }
            }*/

        }



    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {

        Log.d("onLocationChanged","onLocationChanged");

        if(getActivity() != null) {

            Geocoder gCoder = new Geocoder(getActivity());
            List<android.location.Address> addresses = null;


            try {
                addresses = gCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

/*
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
*/
                android.location.Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                String strAdd = strReturnedAddress.toString();





/* antes socket
            mLastLocation = location;
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }

            //Place current location marker
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Usted esta Aqui "+strAdd);


            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.persona);
            markerOptions.icon(icon);

            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);


*/

                mLastLocation = location;

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                this.nameLocation = strAdd;

                if (isFistLocation) {
                    //move map camera
                    isFistLocation = false;

                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
                }


                // SI POSEE UN VIAJE DIBUAMOS LA RUTA //
            /*if(HomeActivity.currentTravel != null) {

                listPosition.add(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));


                // List<LatLng> list = new ArrayList<>();

                //list.add(new LatLng(-34.508809, -58.534441));
                //list.add(new LatLng(-34.520094, -58.524079));  // North of the previous point, but at the same longitude


                options = new
                        PolylineOptions()
                        .width(5)
                        .color(Color.GRAY)
                        .geodesic(true);

                for (int z = 0; z < listPosition.size(); z++) {
                    LatLng point = listPosition.get(z);
                    options.add(point);
                }

                Log.d("valuePrama", "Vaa gps");

                Polyline line = mGoogleMap.addPolyline(options);
                line.setColor(Color.argb(	23, 61, 113, 21));
            }

*/


                //optionally, stop location updates if only current location is needed
                ///if (mGoogleApiClient != null) {
                // LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                //}

            } catch (IOException e) {
                Log.d("ERROR", e.getMessage());
            }
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getActivity().getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions((Activity) getActivity().getApplicationContext(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) getActivity().getApplicationContext(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity().getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public    void addDriverMap()
    {

        Log.d("onLocationChanged","timer");

         GlovalVar gloval;

        // variable global //
        gloval = ((GlovalVar)getActivity().getApplicationContext());

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

         if (gloval.getLocationDriverFromClient() != null) {
             Log.d("onLocationChanged","auto");


             TravelLocationEntity info = gloval.getLocationDriverFromClient();





             //Place current location marker
             LatLng latLng = new LatLng(Double.parseDouble(info.getLatLocation()),
                     Double.parseDouble(info.getLongLocation()));
             MarkerOptions markerOptions = new MarkerOptions();
             markerOptions.position(latLng);
             markerOptions.title("Chofer esta aqui" + info.getLocation());


             int height = 45;
             int width = 40;
             BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.auto);
             Bitmap b=bitmapdraw.getBitmap();
             Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            /* BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.auto);*/
             markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

             mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
         }

        if(mLastLocation != null) {


            //Place current location marker
            LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Usted esta Aqui ");


            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.persona);
            markerOptions.icon(icon);

            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        }
    }

    public void _activeTimerMap()
    {

            timer = new Timer();
            timer.schedule(new TimerTask() {
            @Override
            public void run() {


                if(getActivity() != null)
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addDriverMap();
                        }
                    });
                }




                 }
            }, 0, 9000);

    }

    public static float calculateMiles() {
        float totalDistance = 0;



        Log.d("valuePrama", String.valueOf(options.getPoints().size()));
        for(int i = 1; i < options.getPoints().size(); i++) {


            Log.d("valuePrama", String.valueOf(i));
            Location currLocation = new Location("this");
            currLocation.setLatitude(options.getPoints().get(i).latitude);
            currLocation.setLongitude(options.getPoints().get(i).longitude);

            Location lastLocation = new Location("this");
            lastLocation.setLatitude(options.getPoints().get(i-1).latitude);
            lastLocation.setLongitude(options.getPoints().get(i-1).longitude);

            totalDistance += lastLocation.distanceTo(currLocation);
        }
        return totalDistance;
    }

    // Fetches data from url passed
    private static class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        /**
         * A method to download json data from url
         */
        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();
                Log.d("downloadUrl", data.toString());
                br.close();

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }

    }

    private static class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mGoogleMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }


}