package com.example.android.milestone.models;


import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import weborb.service.MapToProperty;

public class Contact implements Serializable {

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

    public int getPhone() {
        return Phone;
    }

    public int getPhone2() {
        return Phone2;
    }

    private int Phone;
    private int Phone2;
    private String Email;
    private String id;

    @MapToProperty( property = "created" )
    public Date created;

    @MapToProperty( property = "updated" )
    public Date updated;

    @MapToProperty( property = "objectId" )
    public String objectId;

    public String getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getObjectID() {
        return objectId;
    }

    public void setObjectID(String objectId) {
        this.objectId = objectId;
    }




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

    public Contact(Contact e){
        this.Nom = e.getNom();
        this.Prenom = e.getPrenom();
        this.Phone = e.getPhone();
        this.Phone2 = e.getPhone2();
        e.objectId = e.getObjectID();
        this.Email = e.getEmail();
    }
}
