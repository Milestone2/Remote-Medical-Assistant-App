package com.example.android.milestone.models;


import org.json.JSONException;
import org.json.JSONObject;

public class User {

    //Attributs
    private String dateN;
    private String groupeSanguin;
    private String nom;
    private String prenom;
    private String placeOB;
    private double weight;
    private String affDoctor;

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

    public User fromJSON(JSONObject json){
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
}
