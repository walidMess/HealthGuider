package com.healthyguider.healthguider.ApiConfig;

/**
 * Created by wezzy on 06/04/2018.
 */

public class Hospital {
    private String hopitalee_id;
    private String nomh;
    private String adresse;
    private String Commune;
    private String Daira;
    private String GPS;
    private String mobile;
    private Double Latitude;
    private Double Longitude;
    private Double Distance;
    private String Specialite;
    private String HeurO;
    private int H_id;


    public  Hospital(){

    }

    public Hospital(String H_id,String nomh, String adresse, String Commune, String Latitude, String Longitude, String mobile, String heurO, String specialite, String Distance){
        setNomh(nomh);
        setAdresse(adresse);
        setCommune(Commune);
        setLatitude(Double.parseDouble(Latitude));
        setLongitude(Double.parseDouble(Longitude));
        setMobile(mobile);
        setHeurO(heurO);
        setSpecialite(specialite);
        setDistance(Double.parseDouble(Distance));

    }

    public Hospital(String H_id,String nomh, String adresse, String Commune, String Latitude, String Longitude,String mobile,String heurO,String specialite){
        setNomh(nomh);
        setAdresse(adresse);
        setCommune(Commune);
        setLatitude(Double.parseDouble(Latitude));
        setLongitude(Double.parseDouble(Longitude));
        setMobile(mobile);
        setHeurO(heurO);
        setSpecialite(specialite);


    }

   /* public Hospital(String H_id,String nomh, String adresse, String Commune, String Latitude, String Longitude,String mobile,String heurO){
        setNomh(nomh);
        setAdresse(adresse);
        setCommune(Commune);
        setLatitude(Double.parseDouble(Latitude));
        setLongitude(Double.parseDouble(Longitude));
        setMobile(mobile);
        setHeurO(heurO);


    }*/

    public int getH_id() {
        return H_id;
    }

    public void setH_id(int h_id) {
        H_id = h_id;
    }


    public String getHopitalee_id() {
        return hopitalee_id;
    }

    public void setHopitalee_id(String hopitalee_id) {
        this.hopitalee_id = hopitalee_id;
    }

    public String getNomh() {
        return nomh;
    }

    public void setNomh(String nomh) {
        nomh.replace(" ","%20");
        this.nomh = nomh;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {

        adresse.replace(" ","%20");
        this.adresse = adresse;
    }

    public String getCommune() {
        return Commune;
    }

    public void setCommune(String commune) {
        Commune = commune;
    }

    public String getDaira() {
        return Daira;
    }

    public void setDaira(String daira) {
        daira.replace(" ","%20");
        Daira = daira;

    }

    public String getGPS() {
        return GPS;
    }

    public void setGPS(String GPS)
    {
        GPS.replace(" ","%20");

        this.GPS = GPS;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        mobile.replace(" ","%20");
        this.mobile = mobile;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getDistance() {
        return Distance;
    }

    public void setDistance(Double distance) {
        Distance = distance;
    }

    public String getSpecialite() {


        return Specialite;
    }

    public void setSpecialite(String specialite)
    {
        specialite.replace(" ","%20");

        Specialite = specialite;
    }

    public String getHeurO() {
        return HeurO;
    }

    public void setHeurO(String heurO) {

        heurO.replace(" ","%20");

        HeurO = heurO;
    }
}
