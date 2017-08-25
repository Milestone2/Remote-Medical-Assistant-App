package com.example.android.milestone.models;



public class Contact {

    private String nom;
    private String prenom;
    private int tel;
    private  int tel2;
    private String email;


    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public int getTel2() {
        return tel2;
    }

    public Contact(String nom, String prenom, String email, int tel, int tel2) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.tel2 = tel2;
        this.email = email;
    }

    //constructeur test
    public Contact() {
        this.nom = "Roodly";
        this.prenom = "Emmanuel";
        this.tel = 37396810;
        this.tel2 = 33271774;
        this.email = "emmanuelroodly@yahoo.fr";
    }
}
