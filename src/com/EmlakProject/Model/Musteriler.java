package com.EmlakProject.Model;

import com.EmlakProject.Helper.DB_Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Musteriler {

    private int id;
    private String ad;
    private String soyad;
    private String numara;
    private String mail;
    private String uyelik_tipi;
    private String adres;

    public Musteriler(int id, String ad, String soyad, String numara, String mail, String uyelik_tipi, String adres) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.numara = numara;
        this.mail = mail;
        this.uyelik_tipi = uyelik_tipi;
        this.adres = adres;
    }

    public String getAd() {
        return ad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static ArrayList<Musteriler> getList() {
        ArrayList<Musteriler> musteriList = new ArrayList<>();
        Musteriler obj;
        String query = "Select * from Musteriler";

        try {
            Statement st = DB_Connector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Musteriler(rs.getInt("ID"), rs.getString("Ad"), rs.getString("Soyad"), rs.getString("Numara"), rs.getString("Mail"),
                        rs.getString("Uyelik_Tipi"), rs.getString("Adres"));
                musteriList.add(obj);
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return musteriList;
    }

    public static boolean add(String Ad, String Soyad, String Numara, String Mail, String Uyelik_Tipi, String Adres) {
        String query = "INSERT INTO Musteriler(Ad,Soyad,Numara,Mail,Uyelik_Tipi,Adres) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pr = DB_Connector.getInstance().prepareStatement(query);
            pr.setString(1, Ad);
            pr.setString(2, Soyad);
            pr.setString(3, Numara);
            pr.setString(4, Mail);
//            pr.setString(5, "Select Uyelik_Tipi from UyelikTip where Uyelik_Tipi='" + Uyelik_Tipi + "'");
            pr.setString(5,Uyelik_Tipi);
            pr.setString(6, Adres);
            return pr.executeUpdate() != -1;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

}
