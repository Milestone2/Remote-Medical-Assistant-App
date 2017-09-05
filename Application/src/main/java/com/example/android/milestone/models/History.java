package com.example.android.milestone.models;


import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weborb.service.MapToProperty;

/**
 * Created by Emmanuel Roodly on 01/09/2017.
 */

public class History {

    public String gethId() {
        return hId;
    }

    public void sethId(String hId) {
        this.hId = hId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDateH() {
        return dateH;
    }

    public void setDateH(String dateH) {
        this.dateH = dateH;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }



    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    private String hId;
    private String objectId;
    private Date created;
    private String ownerId;
    private Date updated;
    private String summary;
    private String doctor;
    private String dateH;
    private String detail;


    public History(){
        this.summary = "summary1";
        this.doctor = "doctor";
        this.hId = "123456";
        this.dateH = "25-janv-2017";
        this.detail = "detail23";

    }


    public static History fromHashMap(Map hashMap){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(hashMap.get("dateH"));

        History history = new History();
        try {
            history.dateH = date;
            history.detail = hashMap.get("detail").toString();
            history.doctor = hashMap.get("doctor").toString();
            history.summary = hashMap.get("summary").toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return history;
    }

    public static ArrayList<History> fromMap(List<Map> map){
        ArrayList<History> histories = new ArrayList<>();
        for( int i = 0; i <= map.size(); i++){
            try{
                History h = History.fromHashMap(map.get(i));
                if(h != null){
                    histories.add(h);
                }
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }

        return histories;
    }


}
