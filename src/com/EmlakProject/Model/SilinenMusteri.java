package com.EmlakProject.Model;

import com.EmlakProject.Helper.DB_Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SilinenMusteri {

    private int id;
    private String ad;
    private String soyad;
    private String numara;
    private String mail;
    private String uyelik_tipi;
    private String adres;

    public SilinenMusteri(int id, String ad, String soyad, String numara, String mail, String uyelik_tipi, String adres) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.numara = numara;
        this.mail = mail;
        this.uyelik_tipi = uyelik_tipi;
        this.adres = adres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getNumara() {
        return numara;
    }

    public void setNumara(String numara) {
        this.numara = numara;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUyelik_tipi() {
        return uyelik_tipi;
    }

    public void setUyelik_tipi(String uyelik_tipi) {
        this.uyelik_tipi = uyelik_tipi;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }


    public static ArrayList<SilinenMusteri> getList(){
        ArrayList<SilinenMusteri> silinenList = new ArrayList<>();
        SilinenMusteri obj;
        String query = "Select * from Silinenler";

        try {
            Statement st = DB_Connector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new SilinenMusteri(rs.getInt("ID"), rs.getString("Ad"), rs.getString("Soyad"), rs.getString("Numara"), rs.getString("Mail"),
                        rs.getString("Uyelik_Tipi"), rs.getString("Adres"));
                silinenList.add(obj);
            }

            st.close();
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return silinenList;
    }


}
