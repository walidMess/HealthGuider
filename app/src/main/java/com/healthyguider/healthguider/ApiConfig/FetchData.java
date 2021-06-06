package com.healthyguider.healthguider.ApiConfig;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.healthyguider.healthguider.ApiConfig.Hospital;
import com.healthyguider.healthguider.AutoCompleteList.AutoCompleteElementAdapter;
import com.healthyguider.healthguider.AutoCompleteList.Element;
import com.healthyguider.healthguider.Charts.ChartData;
import com.healthyguider.healthguider.Charts.ChartsActivity;
import com.healthyguider.healthguider.Demands.Demandss;
import com.healthyguider.healthguider.Demands.HopitalDemandesAdapter;
import com.healthyguider.healthguider.Demands.ViewDemands;
import com.healthyguider.healthguider.Logging.LogingActivity;
import com.healthyguider.healthguider.Logging.UserLocalStorage;
import com.healthyguider.healthguider.Logging.WelcomeActivity;
import com.healthyguider.healthguider.Logging.user;
import com.healthyguider.healthguider.MapsActivity;
import com.healthyguider.healthguider.R;
import com.healthyguider.healthguider.ResultList.HospitalAdapter;
//import com.healthyguider.healthguider.ResultList.RecyckerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.healthyguider.healthguider.MapsActivity.Elements;
import static com.healthyguider.healthguider.MapsActivity.Hospitals;
import static com.healthyguider.healthguider.MapsActivity.autoCompleteTextView;
import static com.healthyguider.healthguider.MapsActivity.recyclerView;
import static com.healthyguider.healthguider.Register.registerActivity.wronginfos;

/**
 * Created by wezzy on 30/04/2018.
 */

public class FetchData {

    //private ArrayList<Hospital> Hospitals = new ArrayList<Hospital>();

    Context ctx;



    String serverAdress="192.168.137.1";
    // DEcisions
    String decision;
    String nomh;


    //Loging()
    String username,password;

    String  email;
    String privileges;

    UserLocalStorage userLocalStorage;

     user USER;

     // getHospital()


    int OptionId;
    String Option;
    String typeOption;
    Double Userlat;
    Double Userlag;

    // Adding Hospitals

    Hospital H;



    public FetchData(Context ctx ) {


        this.ctx=ctx;


    }


    public void addDemands(Hospital H){

        FetchData.this.H=H;


        String server_URL= "http://"+serverAdress+"/healthProject/Users/nouvHopital.php?nomh="+
                    FetchData.this.H.getNomh()+"&"+
                    "adresse="+FetchData.this.H.getAdresse()+"&"+
                    "numero="+FetchData.this.H.getMobile()+"&"+
                    "GPS="+FetchData.this.H.getGPS()+"&"+
                    "heuro="+FetchData.this.H.getHeurO();


        server_URL=server_URL.replace(" ","%20");


        //Toast.makeText(ctx,"1 "+server_URL,//Toast.LENGTH_LONG).show();
        //Demandss.wronginfos.setText(server_URL);
        Demandss.wronginfos.setVisibility(View.VISIBLE);

        getAutocomplete();
        
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, server_URL,



                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                       // //Toast.makeText(ctx,"2",//Toast.LENGTH_SHORT).show();
                        try {





                            String serverResponce=response.getString("responce");

                          //  //Toast.makeText(ctx,"3",//Toast.LENGTH_SHORT).show();

                            Demandss.wronginfos.setText(serverResponce);
                            Demandss.wronginfos.setVisibility(View.VISIBLE);

                            
                            

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ctx.getApplicationContext(),"Error ...."+error.getMessage(),//Toast.LENGTH_LONG).show();
                error.printStackTrace();


                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(error.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();



            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

               /* params.put("nomh",FetchData.this.H.getNomh());

                params.put("adresse",FetchData.this.H.getAdresse());
                params.put("numero",FetchData.this.H.getMobile());
                params.put("GPS",FetchData.this.H.getGPS());
                params.put("heuro",FetchData.this.H.getHeurO());*/



                return params;

            }
        }

                ;

        com.healthyguider.healthguider.MySignleton.getInstance(FetchData.this.ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequest);





    }

    public void HandleOnErrer(VolleyError error){

        String typeError="";
        if(error instanceof TimeoutError || error instanceof AuthFailureError ){


            typeError = "TimeOutError";


        }
        if(error instanceof ServerError){


            typeError = "ServerError";


        }
        if(error instanceof NetworkError){

            typeError = "NetworkEror";



        }
        if(error instanceof ParseError){


            typeError = "Parser Error";



        }


        AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(typeError);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }



    public void Login(final String username, String password ){

        FetchData.this.username=username;
        FetchData.this.password=password;



        String server_URL="http://"+serverAdress+"/healthProject/Users/login.php?username="+
                FetchData.this.username+"&"+
          "password="+FetchData.this.password;

        server_URL=server_URL.replace(" ","%20");


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, server_URL,



                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            //Toast.makeText(ctx,"icio ",//Toast.LENGTH_SHORT).show();


//Toast.makeText(ctx,"ici "+response.getString("isMembre"),//Toast.LENGTH_SHORT).show();

                            user U = new user(response.getString("isMembre"),
                                         response.getString("username"),
                                         response.getString("email"),
                                         response.getString("privileges"),
                                    response.getString("password")
                                    );

                            if(U.getIsmember().equals("false")){
                                //Toast.makeText(ctx,"Dekhl hna",//Toast.LENGTH_SHORT).show();
                                LogingActivity.wronginfos.setText("User doesn't exist ! please check your informations again ");
                                LogingActivity.wronginfos.setVisibility(View.VISIBLE);


                            }else {

                                LogingActivity.wronginfos.setText("welcome !");
                               LogingActivity. wronginfos.setVisibility(View.VISIBLE);


                               // register the user
                                userLocalStorage= new UserLocalStorage(ctx);
                                userLocalStorage.storUserData(U);
                                userLocalStorage.setUserLoggedIn(true);

                                //starting the maps activity
                                Intent intent = new Intent(ctx,MapsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                ctx.getApplicationContext().startActivity(intent);
                                ((LogingActivity)ctx).finish();



                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ctx.getApplicationContext(),"Error .... Login"+error.getMessage(),//Toast.LENGTH_LONG).show();
                error.printStackTrace();


                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(error.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();



            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username",FetchData.this.username);

                params.put("password",FetchData.this.password);




                return params;

            }
        }

                ;

        com.healthyguider.healthguider.MySignleton.getInstance(FetchData.this.ctx.getApplicationContext()).addToRequestQueue(jsonArrayRequest);





    }

    public void getAutocomplete(){

        String server_URL="http://"+serverAdress+"/healthProject/v1/AutocompleteSeguestions.php";


        ////Toast.makeText(ctx.getApplicationContext(),"well "+query,//Toast.LENGTH_SHORT).show();


           //     FetchData.this.server_URL="http://healthyguide.epizy.com/healthProject/v1/AutocompleteSeguestions.php";



        JsonArrayRequest jsonObjectRequestAutoComplete = new JsonArrayRequest(Request.Method.GET, server_URL,



                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        int count =0;


                        try {

              // //Toast.makeText(ctx.getApplicationContext(),"choosed "+query,//Toast.LENGTH_SHORT).show();


                            Elements.clear();


                                        while (count < response.length()) {

                                            JSONObject jsonObject = response.getJSONObject(count);


                                            Element e = new Element(
                                                    jsonObject.getString("id").toString(),
                                                    jsonObject.get("Option").toString(),
                                                    jsonObject.get("Type").toString()
                                            );

                                            Elements.add(e);
                                            MapsActivity.HashMapElements.put(e.getOption(),e);
                                            count ++;
                                        }

                                          //  //Toast.makeText(ctx.getApplicationContext(),"lenth autocompleye is "+Elements.size(),//Toast.LENGTH_SHORT).show();


                                        AutoCompleteElementAdapter autoCompleteElementAdapter = new AutoCompleteElementAdapter(ctx, R.layout.auto_complete_element,Elements);
                                        MapsActivity.autoCompleteTextView.setAdapter(autoCompleteElementAdapter);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        };                                ;

                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ctx.getApplicationContext(),"Error ...."+error.getMessage(),//Toast.LENGTH_LONG).show();
                error.printStackTrace();
                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(error.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();



            }


        }) {

        }

                ;

        com.healthyguider.healthguider.MySignleton.getInstance(FetchData.this.ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequestAutoComplete);







    }


public void Register(String username,String password,String email,String privileges){

        FetchData.this.username=username;
        FetchData.this.password=password;
        FetchData.this.email=email;
        FetchData.this.privileges=privileges;



        String server_URL="http://"+serverAdress+"/healthProject/Users/signup.php?username="+
                FetchData.this.username+"&"+
                "password="+FetchData.this.password+
                "&"+"email="+FetchData.this.email+
                "&"+"privileges="+FetchData.this.privileges;

    server_URL=server_URL.replace(" ","%20");




    JsonObjectRequest jsonObjectRequestResult = new JsonObjectRequest(Request.Method.GET, server_URL,

            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        //Toast.makeText(ctx,"aussi",//Toast.LENGTH_SHORT).show();

                        String Serverresponce =response.getString("success");

                        //Toast.makeText(ctx,"miaw"+" "+response.getString("success").toString(),//Toast.LENGTH_SHORT).show();

                        wronginfos.setText(Serverresponce);
                        wronginfos.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {



        @Override
        public void onErrorResponse(VolleyError error) {

            //Toast.makeText(ctx.getApplicationContext(),"Error ...."+error.getMessage(),//Toast.LENGTH_LONG).show();
            error.printStackTrace();


            HandleOnErrer(error);



        }


    })





            ;

    com.healthyguider.healthguider.MySignleton.getInstance(FetchData.this.ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequestResult);

}






    public void getHospitals(int OptionId,String Option,String typeOption,Double Userlat,Double Userlag){


        FetchData.this.OptionId=OptionId;
        FetchData.this.Userlat=Userlat;
        FetchData.this.Userlag=Userlag;
        FetchData.this.Option=Option;
        FetchData.this.typeOption=typeOption;




        String  server_URL="http://"+serverAdress+"/healthProject/v1/AllItems.php?QueryType="
                +FetchData.this.typeOption
                +"&"+"Latitude="+ String.valueOf(FetchData.this.Userlat)
                +"&"+"longitude="+String.valueOf(FetchData.this.Userlag)
                +"&"+"OptionId="+FetchData.this.OptionId;
                // //Toast.makeText(ctx.getApplicationContext(),"choosed "+query,//Toast.LENGTH_SHORT).show();

        server_URL=server_URL.replace(" ","%20");

                Hospitals.clear();






                JsonArrayRequest jsonObjectRequestResult = new JsonArrayRequest(Request.Method.GET, server_URL,

                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {


                                int count =0;


                                try {



                                    MapsActivity.recyclerView.setAdapter(null);


                                    while (count < response.length()) {




                                        JSONObject jsonObject = response.getJSONObject(count);





                                        Hospitals.add( new Hospital(
                                                jsonObject.get("H_id").toString(),
                                                jsonObject.get("NomH").toString(),
                                                jsonObject.get("Adress").toString(),
                                                jsonObject.get("Commune").toString(),
                                                jsonObject.get("Latitude").toString(),
                                                jsonObject.get("Longitude").toString(),
                                                jsonObject.get("mobile").toString(),
                                                jsonObject.get("heurO").toString(),
                                                jsonObject.get("Specialite").toString(),
                                                jsonObject.get("Distance").toString()

                                        ));



                                        count ++;
                                    }



                                    HospitalAdapter hospitalAdapter = new HospitalAdapter(ctx,MapsActivity.Hospitals);
                                    MapsActivity.recyclerView.setAdapter(hospitalAdapter);
                                    recyclerView.setVisibility(View.VISIBLE);




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                };                                ;

                            }
                        }, new Response.ErrorListener() {



                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Toast.makeText(ctx.getApplicationContext(),"Error ...."+error.getMessage(),//Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                        autoCompleteTextView.setText(error.getMessage());

                        HandleOnErrer(error);



                    }


                })





                        ;

                com.healthyguider.healthguider.MySignleton.getInstance(FetchData.this.ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequestResult);

        }


        public void viewDemands(){

            String  server_URL="http://"+serverAdress+"/healthProject/Users/viewDemands.php";
            // //Toast.makeText(ctx.getApplicationContext(),"choosed "+query,//Toast.LENGTH_SHORT).show();


            ViewDemands.HopitalDemandes.clear();







            JsonArrayRequest jsonObjectRequestResult = new JsonArrayRequest(Request.Method.GET, server_URL,

                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {


                            int count =0;


                            try {


                                //Toast.makeText(ctx,"1",//Toast.LENGTH_SHORT).show();

                                ViewDemands.viewDemandsRecycler.setAdapter(null);


                                while (count < response.length()) {




                                    JSONObject jsonObject = response.getJSONObject(count);



                                    //Toast.makeText(ctx,"1",//Toast.LENGTH_SHORT).show();


                                    ViewDemands.HopitalDemandes.add( new Hospital(
                                            jsonObject.get("H_id").toString(),
                                            jsonObject.get("NomH").toString(),
                                            jsonObject.get("Adress").toString(),
                                            jsonObject.get("Commune").toString(),
                                            jsonObject.get("Latitude").toString(),
                                            jsonObject.get("Longitude").toString(),
                                            jsonObject.get("mobile").toString(),
                                            jsonObject.get("heurO").toString(),
                                            jsonObject.get("Specialite").toString()


                                    ));



                                    count ++;
                                }



                                HopitalDemandesAdapter hopitalDemandesAdapter = new HopitalDemandesAdapter(ctx,ViewDemands.HopitalDemandes);
                                ViewDemands.viewDemandsRecycler.setAdapter(hopitalDemandesAdapter);
                                ViewDemands.viewDemandsRecycler.setVisibility(View.VISIBLE);




                            } catch (JSONException e) {
                                e.printStackTrace();
                            };                                ;

                        }
                    }, new Response.ErrorListener() {



                @Override
                public void onErrorResponse(VolleyError error) {

                    //Toast.makeText(ctx.getApplicationContext(),"Error ...."+error.getMessage(),//Toast.LENGTH_LONG).show();
                    error.printStackTrace();


                    HandleOnErrer(error);



                }


            })





                    ;

            com.healthyguider.healthguider.MySignleton.getInstance(FetchData.this.ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequestResult);

        }

public void AdminDecision(String DECISION,String nomhopital){

FetchData.this.decision=DECISION;
FetchData.this.nomh=nomhopital;


    String server_URL="http://"+serverAdress+"/healthProject/Users/DemandOperation.php?Decision="+
            FetchData.this.decision+"&"+
            "nomh="+FetchData.this.nomh;


    server_URL=server_URL.replace(" ","%20");



    //Toast.makeText(ctx,"1"+server_URL,//Toast.LENGTH_SHORT).show();
    JsonObjectRequest jsonObjectRequestResult = new JsonObjectRequest(Request.Method.GET, server_URL,

            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {


                        String Serverresponce =response.getString("responce");

                        ViewDemands.wronginfos.setText(Serverresponce);
                        ViewDemands.wronginfos.setVisibility(View.VISIBLE);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {



        @Override
        public void onErrorResponse(VolleyError error) {

            //Toast.makeText(ctx.getApplicationContext(),"Error ...."+error.getMessage(),//Toast.LENGTH_LONG).show();
            error.printStackTrace();


            HandleOnErrer(error);



        }


    })





            ;

    com.healthyguider.healthguider.MySignleton.getInstance(FetchData.this.ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequestResult);



}


public void getUsers(){

    String server_URL="http://"+serverAdress+"/healthProject/Users/getUserNumber.php";


    ////Toast.makeText(ctx.getApplicationContext(),"well "+query,//Toast.LENGTH_SHORT).show();


    //     FetchData.this.server_URL="http://healthyguide.epizy.com/healthProject/v1/AutocompleteSeguestions.php";



    JsonObjectRequest jsonObjectRequestAutoComplete = new JsonObjectRequest(Request.Method.GET, server_URL,



            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {




                    try {

                        if(response.getString("responce").equals("1")) {
                            WelcomeActivity.userNumber.setText(response.getString("number"));
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                    };                                ;

                }
            }, new Response.ErrorListener() {



        @Override
        public void onErrorResponse(VolleyError error) {
            //Toast.makeText(ctx.getApplicationContext(),"Error ...."+error.getMessage(),//Toast.LENGTH_LONG).show();
            error.printStackTrace();
            AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage(error.getMessage());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();



        }


    }) {

    }

            ;

    com.healthyguider.healthguider.MySignleton.getInstance(FetchData.this.ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequestAutoComplete);


}




}
