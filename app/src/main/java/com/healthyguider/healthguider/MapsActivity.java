package com.healthyguider.healthguider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.support.design.widget.Snackbar;


import android.support.v7.app.ActionBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.healthyguider.healthguider.ApiConfig.FetchData;
import com.healthyguider.healthguider.ApiConfig.Hospital;
import com.healthyguider.healthguider.AutoCompleteList.Element;
import com.healthyguider.healthguider.Charts.ChartsActivity;
import com.healthyguider.healthguider.Demands.Demandss;
import com.healthyguider.healthguider.Demands.ViewDemands;
import com.healthyguider.healthguider.Direction.DrawDirections;
import com.healthyguider.healthguider.Logging.LogingActivity;
import com.healthyguider.healthguider.Logging.UserLocalStorage;
import com.healthyguider.healthguider.Logging.WelcomeActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.app.Service.START_STICKY;
import static android.widget.Toast.LENGTH_SHORT;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    static GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private String locationProvider;
    private static final int LOCATION_REQUEST = 1;


    //user details
    UserLocalStorage userLocalStorage;

    //  Declare the  Menu side
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    MenuItem logout;
    NavigationView navigationView;

    // Declare  Buttons
    private ImageButton Clear, Search, ChartsButton;
    private Switch switchResultList;


    // User Location details
    private LatLng UserLocation;
    public static Double Userlat, Userlag;
    private Location mLastLocation;
    public static ImageButton LocateMe;

    // Draw paths Config
    static ArrayList<LatLng> listPoints;
    ImageButton DrawPathButton;
    public static boolean PathDrawerReady = true;
    public static Context ctx;

    // APi to get User Location
    public FusedLocationProviderClient mFusedLocationClient;
    Marker myLocationMarker;



    //Autocomplete config
    public static ArrayList<Element> Elements = new ArrayList<Element>();
    public static HashMap<String, Element> HashMapElements = new HashMap<String, Element>();
    public static AutoCompleteTextView autoCompleteTextView;

    //config listResults
    public static RecyclerView recyclerView;
    public static ArrayList<Hospital> Hospitals;
    public static HashMap<String, Hospital> HashMapHospitals;

    //config connection with our API
    FetchData fetchData = new FetchData(this);

    public static DrawDirections drawDirections;

    private static final String TAG = MapsActivity.class.getSimpleName();

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;


    public static GoogleMap getMapp() {

        return mMap;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userLocalStorage = new UserLocalStorage(this);


        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();


        //config Draw Paths
        ctx = this;

        // menu side bare
        navigationView = (NavigationView) findViewById(R.id.navView);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Logout) {


                    userLocalStorage.clearUserData();
                    userLocalStorage.setUserLoggedIn(false);

                    Intent intent = new Intent(MapsActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();

                } else if (id == R.id.viewDemands) {
                    Intent intent = new Intent(MapsActivity.this, ViewDemands.class);
                    startActivity(intent);

                } else if (id == R.id.AddHospital) {
                    Intent intent = new Intent(MapsActivity.this, Demandss.class);
                    startActivity(intent);
                }

                return true;
            }
        });


        //check the authentification


        if (Authentification() == true) {

            //Autocomplete Config


            fetchData = new FetchData(this);
            fetchData.getAutocomplete();

            if(userLocalStorage.isAdmin() != true){

                navigationView.getMenu().getItem(0).setVisible(false);
            }


        } else {
            Intent intent = new Intent(MapsActivity.this, LogingActivity.class);
            startActivity(intent);
            finish();
        }

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setFocusable(false);
        autoCompleteTextView.setFocusableInTouchMode(true);

        autoCompleteTextView.setOnFocusChangeListener(

                new View.OnFocusChangeListener() {


                    @Override

                    public void onFocusChange(View v, boolean hasFocus) {
                        if (autoCompleteTextView.isFocused()) {
                            autoCompleteTextView.setHint("Tapez une Spécialité, un Hopital ..");
                        } else {
                            autoCompleteTextView.setHint(null);
                        }

                    }
                });


        //ResultList Config

        Hospitals = new ArrayList<Hospital>();
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.GONE);


        // cofig buttons
        Clear = (ImageButton) findViewById(R.id.ClearButton);
        Search = (ImageButton) findViewById(R.id.SearchButton);
        LocateMe = (ImageButton) findViewById(R.id.LocateMe);
        ChartsButton = (ImageButton) findViewById(R.id.ChartsButton);

        switchResultList = (Switch) findViewById(R.id.switchResultList);
        switchResultList.setVisibility(View.GONE);
        switchResultList.setActivated(false);

//        DrawPathButton=(ImageButton)findViewById(R.layout.list_element_layout.);
        Search.setImageResource(R.drawable.ic_search_black_24dp);
        Clear.setImageResource(R.drawable.ic_clear_black_24dp);
        ChartsButton.setImageResource(R.drawable.charts);


        //config onclickListener for buttons

        ChartsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this.getApplicationContext(), ChartsActivity.class);
                startActivity(intent);
            }
        });

        switchResultList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchResultList.isChecked()) {

                    recyclerView.setVisibility(View.VISIBLE);


                } else {
                    recyclerView.setVisibility(View.GONE);


                }
            }
        });

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                autoCompleteTextView.clearListSelection();
                autoCompleteTextView.setText("");
                recyclerView.setVisibility(View.GONE);
                recyclerView.setAdapter(null);
                switchResultList.setVisibility(View.GONE);
                switchResultList.setChecked(false);
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoCompleteTextView.equals("")) {
                    Toast.makeText(MapsActivity.this.getApplicationContext(), "please enter a value !", LENGTH_SHORT).show();
                } else {
                    if (!HashMapElements.containsKey(autoCompleteTextView.getText().toString())) {
                        Toast.makeText(MapsActivity.this.getApplicationContext(), "please choose a valide value !", LENGTH_SHORT).show();


                    } else {


                        String Option, typeOption;
                        int id;
                        Element e = HashMapElements.get(autoCompleteTextView.getText().toString());
                        if (e == null) {
                            Toast.makeText(MapsActivity.this.getApplicationContext(), "mal9ahech", LENGTH_SHORT).show();


                        } else {
                            //Toast.makeText(MapsActivity.this.getApplicationContext(),e.getName()+" "+e.getType(),Toast.LENGTH_SHORT).show();

                            View view = MapsActivity.this.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }

                            switchResultList.setChecked(true);
                            switchResultList.setVisibility(View.VISIBLE);

                            Option = e.getOption();
                            typeOption = e.getOption_type();
                            id = e.getId();


                            FetchData ft = new FetchData(MapsActivity.this);
                            getLastLocation();

                            //  Toast.makeText(MapsActivity.this.getApplicationContext(),e.getOption()+" "+e.getOption_type()+" "+e.getId(),Toast.LENGTH_SHORT).show();


                            ft.getHospitals(id, Option, typeOption, Userlat, Userlag);


                        }


                    }

                }

            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MapsActivity.this.Search.callOnClick();
            }
        });


        LocateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Disable keyboard
        View view1 = MapsActivity.this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
    }

    public static void DrawPaths(LatLng Destination, Hospital H) {

        if (PathDrawerReady == true) {

            mMap.clear();
            drawDirections = new DrawDirections(ctx.getApplicationContext());
            listPoints = new ArrayList<LatLng>();
            PathDrawerReady = false;
            mMap.addMarker(new MarkerOptions().position(Destination)
                    .title(H.getNomh()));
            getMapp().moveCamera(CameraUpdateFactory.newLatLng(Destination));

            drawDirections.DrawRoad(new LatLng(Userlat, Userlag), Destination, listPoints);
            PathDrawerReady = true;


        } else {

            /*
            AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Alert message to be shown");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });


            alertDialog.show();
            */

        }


    }


    public void checkProviders() {

        LocationManager lm = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            final AlertDialog.Builder dialog = new AlertDialog.Builder(MapsActivity.this);
            dialog.setMessage("gps_network_not_enabled");
            dialog.setPositiveButton("open_location_settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    MapsActivity.this.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                   Toast.makeText(MapsActivity.this,"You refused to grant permissions some feuters wont work !",LENGTH_SHORT).show();

                }
            });
            dialog.show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();


        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }


    }

    public Boolean Authentification() {
        return userLocalStorage.IsLoggedIn();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
                Toast.makeText(this, "User interaction was cancelled", Toast.LENGTH_LONG).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.textwarn, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MapsActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);


        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Toast.makeText(this, "Displaying permission rationale to provide additional context.", LENGTH_SHORT).show();


            showSnackbar(R.string.textwarn, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            Toast.makeText(this, "Requesting permission", LENGTH_SHORT).show();

            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }


    public void getLastLocation() {




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            checkProviders();
        }
        mFusedLocationClient.getLastLocation()

                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            LatLng UserLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                            MapsActivity.Userlat = task.getResult().getLatitude();
                            MapsActivity.Userlag = task.getResult().getLongitude();

                            //Toast.makeText(getApplicationContext(),""+MapsActivity.Userlat+","+MapsActivity.Userlag,Toast.LENGTH_SHORT).show();


                            if (myLocationMarker != null) {
                                myLocationMarker.remove();

                            }
                            myLocationMarker = getMapp().addMarker(new MarkerOptions().position(UserLocation)
                                    .title("My location"));


                            getMapp().moveCamera(CameraUpdateFactory.newLatLng(UserLocation));


                        } else {
                            Log.w(TAG, "exceptionLocation " + "getLastLocation:exception", task.getException());
                            Toast.makeText(getApplicationContext(), "Bad Network :" + task.getException(), LENGTH_SHORT).show();


                        }
                    }
                });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        if (!checkPermissions()) {

            requestPermissions();
        }else{

            mMap.setMyLocationEnabled(true);
        }





/*
        LatLng marko;

        Toast.makeText(getApplicationContext(),"map",Toast.LENGTH_SHORT).show();

        if(lat !=null){
             marko=new LatLng(lat,lag);
        }else {
             marko = new LatLng(36.766591, 2.941256);

        }
*/


    }







}
