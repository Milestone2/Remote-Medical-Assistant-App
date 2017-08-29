package com.example.android.milestone.models;



public class Contact {

    private String Nom;
    private String Prenom;
    private int Phone;
    private  int Phone2;
    private String Email;
    private  String id;


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

    public Contact(String nom, String prenom, String email, int tel, int tel2, String id) {
        this.Nom = nom;
        this.Prenom = prenom;
        this.Phone = tel;
        this.Phone2 = tel2;
        this.Email = email;
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
