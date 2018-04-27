package com.example.user.cama;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaTimestamp;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean mapLaunched = false;
    private boolean positionSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //CODE BEGINS******
                String str = "http://10.10.41.139:3000/Cameras";
                URL url = createUrl(str);
                String jsonResponse = null;
                try {
                    jsonResponse = makeHttpRequest(url);
                } catch (IOException e) {
                    Log.e("KAKI", "Error closing input stream", e);
                }
                final String res = jsonResponse;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        jsonParsing(res);
                    }
                });
//  //CODE BEGINS******
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mapLaunched) {


                                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }

//                            LocationManager service = (LocationManager)getSystemService(LOCATION_SERVICE);
//                            Criteria criteria = new Criteria();
//                            String provider = service.getBestProvider(criteria, false);
//                            Location location = service.getLastKnownLocation(provider);
//                            LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
//
//
//

                                mMap.setMyLocationEnabled(true);
                                Location myLocation = mMap.getMyLocation();
                                if (myLocation != null) {
                                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {

                                            Toast toast = Toast.makeText(MapsActivity.this,marker.getSnippet(),Toast.LENGTH_SHORT);
                                            toast.show();
                                            return false;
                                        }
                                    });

                                    mMap.clear(); // clear all previous markers
                                    double lat = myLocation.getLatitude();
                                    double lng = myLocation.getLongitude();

                                    // Log.e("kaki", "lat: " + lat + ", lng: " + lng);
                                    // Add a marker in Sydney and move the camera
                                    LatLng myCoords = new LatLng(lat, lng);
                                    mMap.addMarker(new MarkerOptions().position(myCoords).title("Marker in IDC"));
                                    if (!positionSet) {
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoords));
                                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                                        positionSet = true;
                                    }
                                    LatLng coords;
                                    String url_vid;
                                    JSONArray jArray = new JSONArray();
                                    JSONObject jObj;



                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
//                                    try {
//                                        jsonResponse = makeHttpRequest(url);
//                                    } catch (IOException e) {
//                                        Log.e("kak", "Error closing input stream", e);
//                                    }

//
//                                    try {
//                                        FetchVideos fv = new FetchVideos();
//                                        fv.execute(str);
//                                        jObj = fv.jObject;
//                                        Thread.sleep(5000);
//                                        jArray = jObj.getJSONArray("msg");
//                                        for (int i = 0; i < jArray.length(); i++) {
//                                            jObj = jArray.getJSONObject(i);
//                                            String id = jObj.get("id").toString();
//                                            lat = Double.parseDouble(jObj.get("latitude").toString());
//                                            lng = Double.parseDouble(jObj.get("longtitude").toString());
//                                            url_vid = jObj.get("vid_url").toString();
//                                            // Add a marker in Sydney and move the camera
//                                            coords = new LatLng(lat, lng);
//                                            mMap.addMarker(new MarkerOptions().position(coords).title(id).snippet(url_vid));
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }


//
//
//                                    try {
//                                        jObj = new JSONObject();
//                                        jObj.put("id", "1");
//                                        jObj.put("latitude", "32.1569178");
//                                        jObj.put("longtitude", "34.8773372");
//                                        jObj.put("vid_url", "blaaaa");
//                                        jObj.put("timestamp", "yyyy-mm-dd hh:mm:ss.fffffffff");
//                                        jArray.put(jObj);
//
//                                        jObj = new JSONObject();
//                                        jObj.put("id", "2");
//                                        jObj.put("latitude", "32.1869514");
//                                        jObj.put("longtitude", "34.8373410");
//                                        jObj.put("vid_url", "blaaaa");
//                                        jObj.put("timestamp", "yyyy-mm-dd hh:mm:ss.fffffffff");
//                                        jArray.put(jObj);
//
//                                        jObj = new JSONObject();
//                                        jObj.put("id", "3");
//                                        jObj.put("latitude", "32.1969608");
//                                        jObj.put("longtitude", "34.8173577");
//                                        jObj.put("vid_url", "blaaaa");
//                                        jObj.put("timestamp", "yyyy-mm-dd hh:mm:ss.fffffffff");
//                                        jArray.put(jObj);
//
//                                        for (int i = 0; i < jArray.length(); i++) {
//                                            jObj = jArray.getJSONObject(i);
//                                            String id = jObj.get("id").toString();
//                                            lat = Double.parseDouble(jObj.get("latitude").toString());
//                                            lng = Double.parseDouble(jObj.get("longtitude").toString());
//                                            url = jObj.get("vid_url").toString();
//                                            // Add a marker in Sydney and move the camera
//                                            coords = new LatLng(lat, lng);
//                                            mMap.addMarker(new MarkerOptions().position(coords).title(id).snippet(url));
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }

                                }
                            }
                        }

//                        private URL createUrl(String stringUrl) {
//                            URL url = null;
//                            try {
//                                url = new URL(stringUrl);
//                            } catch (MalformedURLException e) {
//                                Log.e("kak", "Error with creating URL ", e);
//                            }
//                            return url;
//                        }

//                        private String makeHttpRequest(URL url) throws IOException {
//                            String jsonResponse = "";
//
//                            // If the URL is null, then return early.
//                            if (url == null) {
//                                return jsonResponse;
//                            }
//
//                            HttpURLConnection urlConnection = null;
//                            InputStream inputStream = null;
//
//                            try {
//                                urlConnection = (HttpURLConnection) url.openConnection();
//                                urlConnection.setReadTimeout(10000 /* miliseconds */);
//                                urlConnection.setConnectTimeout(15000 /* miliseconds */);
//                                urlConnection.setRequestMethod("GET"); //maybe GET instead of POST
//                                urlConnection.connect();
//
//                                // If the request was successful (response code 200),
//                                // then read the input stream and parse the response.
//                                if (urlConnection.getResponseCode() == 200) {
//                                    inputStream = urlConnection.getInputStream();
//                                    jsonResponse = readFromStream(inputStream);
//                                }
//                            } catch (Exception e) {
//                                Log.e("kak", "Problem retrieving the earthquake JSON results.", e);
//                            } finally {
//                                if (urlConnection != null) urlConnection.disconnect();
//                                if (inputStream != null ) inputStream.close();
//                            }
//                            return jsonResponse;
//                        }

//                        private String readFromStream(InputStream inputStream) throws IOException {
//                            StringBuilder output = new StringBuilder();
//                            if (inputStream != null) {
//                                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
//                                BufferedReader reader = new BufferedReader(inputStreamReader);
//                                String line = reader.readLine();
//                                while (line != null) {
//                                    output.append(line);
//                                    line = reader.readLine();
//                                }
//                            }
//                            return output.toString();
//                        }

                    });
                }
            }
        }).start();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapLaunched = true;

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
    }
    private void jsonParsing(String str) {
        if (str == null) return ;
        try {
            JSONObject mainJson = new JSONObject(str);

        }
        catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("profile", "Problem parsing the JSON results", e);
        }

//        if (allOk) {
//            pointsNumberView.setText(Integer.toString(points));
//            userNameView.setText(userName);
//            levelView.setText(Integer.toString(level));
//            XPView.setText(Integer.toString(curXP));
//        }

        return;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("KAKI", "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* miliseconds */);
            urlConnection.setConnectTimeout(15000 /* miliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e("KAKI", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (inputStream != null ) inputStream.close();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
