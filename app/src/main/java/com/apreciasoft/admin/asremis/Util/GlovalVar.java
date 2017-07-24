package com.apreciasoft.admin.asremis.Util;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.apreciasoft.admin.asremis.Entity.InfoTravelEntity;
import com.apreciasoft.admin.asremis.Entity.TravelLocationEntity;
import com.apreciasoft.admin.asremis.Entity.VehicleType;
import com.apreciasoft.admin.asremis.Entity.client;
import com.apreciasoft.admin.asremis.Entity.driver;
import com.apreciasoft.admin.asremis.Entity.notification;
import com.apreciasoft.admin.asremis.Entity.paramEntity;

import java.util.List;

/**
 * Created by Admin on 02-01-2017.
 */

 public class GlovalVar extends Application {

    private String gv_base_intance = "as_nube";
    private String gv_user_name;
    private int gv_user_id;
    private String gv_user_mail;
    private  int gv_id_profile;
    private int gv_id_cliet;
    private  int gv_id_driver;
    private  int gv_id_vehichle;
    private  String gv_uri;
    private InfoTravelEntity gv_travel_current;
    private List<paramEntity> gv_param;
    private Boolean gv_logeed;
    private TravelLocationEntity locationDriverFromClient;
    private List<notification> gv_listNotifications;
    private List<InfoTravelEntity> gv_lisReservations;
    private driver gv_driverinfo;
    private client gv_clientinfo;
    private List<VehicleType> gv_listvehicleType;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }



    public GlovalVar() {
        this.gv_user_name = "";
        this.gv_user_id = 0;
        this.gv_user_mail = "";
        this.gv_id_profile = 0;
        this.gv_id_cliet = 0;
        this.gv_id_driver = 0;
        this.gv_id_vehichle = 0;
        this.gv_uri = "";
        this.gv_travel_current = null;
        this.gv_param = null;
        this.gv_logeed = false;
        this.gv_listvehicleType = null;
    }

    public List<InfoTravelEntity> getGv_lisReservations() {
        return gv_lisReservations;
    }

    public void setGv_lisReservations(List<InfoTravelEntity> gv_lisReservations) {
        this.gv_lisReservations = gv_lisReservations;
    }

    public List<VehicleType> getGv_listvehicleType() {
        return gv_listvehicleType;
    }

    public void setGv_listvehicleType(List<VehicleType> gv_listvehicleType) {
        this.gv_listvehicleType = gv_listvehicleType;
    }

    public String getGv_base_intance() {
        return gv_base_intance;
    }

    public void setGv_base_intance(String gv_base_intance) {
        this.gv_base_intance = gv_base_intance;
    }

    public driver getGv_driverinfo() {
        return gv_driverinfo;
    }

    public void setGv_driverinfo(driver gv_driverinfo) {
        this.gv_driverinfo = gv_driverinfo;
    }

    public TravelLocationEntity getLocationDriverFromClient() {
        return locationDriverFromClient;
    }

    public void setLocationDriverFromClient(TravelLocationEntity locationDriverFromClient) {
        this.locationDriverFromClient = locationDriverFromClient;
    }

    public Boolean getGv_logeed() {
        return gv_logeed;
    }

    public void setGv_logeed(Boolean gv_logeed) {
        this.gv_logeed = gv_logeed;
    }

    public List<paramEntity> getGv_param() {
        return gv_param;
    }

    public void setGv_param(List<paramEntity> gv_param) {
        this.gv_param = gv_param;
    }

    public InfoTravelEntity getGv_travel_current() {
        return gv_travel_current;
    }

    public void setGv_travel_current(InfoTravelEntity gv_travel_current) {
        this.gv_travel_current = gv_travel_current;
    }

    public String getGv_uri() {
        return gv_uri;
    }

    public void setGv_uri(String gv_uri) {
        this.gv_uri = gv_uri;
    }

    public int getGv_id_vehichle() {
        return gv_id_vehichle;
    }

    public void setGv_id_vehichle(int gv_id_vehichle) {
        this.gv_id_vehichle = gv_id_vehichle;
    }

    public int getGv_id_profile() {
        return gv_id_profile;
    }

    public void setGv_id_profile(int gv_id_profile) {
        this.gv_id_profile = gv_id_profile;
    }

    public int getGv_id_cliet() {
        return gv_id_cliet;
    }

    public void setGv_id_cliet(int gv_id_cliet) {
        this.gv_id_cliet = gv_id_cliet;
    }

    public int getGv_id_driver() {
        return gv_id_driver;
    }

    public void setGv_id_driver(int gv_id_driver) {
        this.gv_id_driver = gv_id_driver;
    }

    public String getGv_user_name() {
        return gv_user_name;
    }

    public void setGv_user_name(String gv_user_name) {
        this.gv_user_name = gv_user_name;
    }

    public int getGv_user_id() {
        return gv_user_id;
    }

    public void setGv_user_id(int gv_user_id) {
        this.gv_user_id = gv_user_id;
    }

    public String getGv_user_mail() {
        return gv_user_mail;
    }

    public void setGv_user_mail(String gv_user_mail) {
        this.gv_user_mail = gv_user_mail;
    }


    public List<notification> getGv_listNotifications() {
        return gv_listNotifications;
    }

    public void setGv_listNotifications(List<notification> gv_listNotifications) {
        this.gv_listNotifications = gv_listNotifications;
    }

    public client getGv_clientinfo() {
        return gv_clientinfo;
    }

    public void setGv_clientinfo(client gv_clientinfo) {
        this.gv_clientinfo = gv_clientinfo;
    }
}