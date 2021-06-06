package com.healthyguider.healthguider.Logging;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStorage {

    public static final String Sp_name="userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStorage(Context context) {
        this.userLocalDatabase = context.getSharedPreferences("userDetails" ,0);
    }

    public void storUserData(user U){

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username",U.getUsername());
        spEditor.putString("password",U.getPassword());
        spEditor.putString("privileges",U.getPrivileges());
        spEditor.putString("email",U.getEmail());

        spEditor.commit();

    }

    public user getLoggedUser(){
        String username=userLocalDatabase.getString("username","");
        String password=userLocalDatabase.getString("password","");
        String privileges=userLocalDatabase.getString("privileges","");
        String email = userLocalDatabase.getString("email","");

        user userStored = new user(username,email,privileges,password);
        return  userStored;
    }

    public void  setUserLoggedIn(Boolean isLoggedIn){


        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("isLogedIn",isLoggedIn);
        spEditor.commit();
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public Boolean IsLoggedIn(){
        if(userLocalDatabase.getBoolean("isLogedIn",false)==true){
            return true;
        }

        return false;
    }

    public Boolean isAdmin(){
        if(userLocalDatabase.getString("privileges","user" ).equals("ADMIN")){
            return true;
        }

        return false;
    }
}
