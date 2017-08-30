package com.example.android.milestone.models;


import java.util.Date;

public class Contact {

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public void setPhone2(int phone2) {
        Phone2 = phone2;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String Nom;
    private String Prenom;
    private int Phone;
    private  int Phone2;
    private String Email;
    private  String id;
    public Date created;
    public Date updated;
    public  String objectID;


    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public int getTel() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public int getTel2() {
        return Phone2;
    }

    public Contact(String Nom, String Prenom, String Email, int Phone, int Phone2, String id) {
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Phone = Phone;
        this.Phone2 = Phone2;
        this.Email = Email;
        this.id = id;
    }

    //constructeur test
    public Contact() {
        this.Nom = "Roodly";
        this.Prenom = "Emmanuel";
        this.Phone = 37396810;
        this.Phone2 = 33271774;
        this.Email = "emmanuelroodly@yahoo.fr";
    }
}
