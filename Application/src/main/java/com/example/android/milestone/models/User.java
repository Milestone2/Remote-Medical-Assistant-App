package com.example.android.milestone.models;


import com.backendless.BackendlessUser;
import com.backendless.exceptions.BackendlessException;

import org.json.JSONException;
import org.json.JSONObject;

import weborb.service.MapToProperty;

public class User {

    //Attributs
    private String dateN;
    private String groupeSanguin;
    private String nom;
    private String prenom;
    private String placeOB;
    private double weight;
    private String affDoctor;
    private String telephone;
    private String created_at;
    private String adress;
    private String ownerId;
    private String email;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @MapToProperty( property = "objectId" )
    private String objectId;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





    //Getters
    public String getDateN() {
        return dateN;
    }

    public String getGroupeSanguin() {
        return groupeSanguin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPlaceOB() {
        return placeOB;
    }

    public double getWeight() {
        return weight;
    }

    public String getAffDoctor() {
        return affDoctor;
    }



    public User(String dateN, String groupeSanguin, String nom, String prenom) {
        this.dateN = dateN;
        this.groupeSanguin = groupeSanguin;
        this.nom = nom;
        this.prenom = prenom;
    }

    //Constructeur pour Test;
    public User() {
        this.nom = "Jean";
        this.prenom = "Marie";
        this.affDoctor = "Dr. Jacob Duvivier";
        this.groupeSanguin = "O+";
        this.weight = 45.5;
        this.placeOB = "Jacmel";
    }

    public static User fromJSON(JSONObject json){
        User user = new User();
        try {
            user.nom = json.getString("nom");
            user.prenom = json.getString("prenom");
            user.weight = json.getDouble("poids");
            user.dateN = json.getString("date_naissance");
            user.affDoctor = json.getString("doctor");
            user.placeOB = json.getString("lieu_naissance");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User fromBackEnd(BackendlessUser backenduser){
        User user = new User();
        try{
            user.nom = backenduser.getProperty("Nom").toString();
            user.created_at = backenduser.getProperty("created").toString();
            user.email = backenduser.getEmail();
            user.adress = backenduser.getProperty("address").toString();
            user.affDoctor = backenduser.getProperty("Doctor").toString();
            user.ownerId = backenduser.getUserId();
            user.telephone = backenduser.getProperty("Telephone").toString();

        }catch (BackendlessException e){
            e.printStackTrace();
        }

        return user;
    }

    public User(BackendlessUser backenduser) throws BackendlessException{
        this.nom = backenduser.getProperty("Nom").toString();
        this.created_at = backenduser.getProperty("created").toString();
        this.email = backenduser.getEmail();
        this.adress = backenduser.getProperty("address").toString();
        this.affDoctor = backenduser.getProperty("Doctor").toString();
        this.ownerId = backenduser.getUserId();
        this.telephone = backenduser.getProperty("Telephone").toString();
    }
}
