package com.EmlakProject.Model;

import com.EmlakProject.Helper.DB_Connector;
import com.EmlakProject.Helper.Helper;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Emlak {
    private int ID;
    private int noticeNumber;
    private String housingType;
    private String noticeType;
    private String city;
    private int price;
    private int field;

    public Emlak(int ID, int noticeNumber, String housingType, String noticeType, String city, int price, int field) {
        this.ID = ID;
        this.noticeNumber = noticeNumber;
        this.housingType = housingType;
        this.noticeType = noticeType;
        this.city = city;
        this.price = price;
        this.field = field;
    }

    public Emlak(int id) {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNoticeNumber() {
        return noticeNumber;
    }

    public void setNoticeNumber(int noticeNumber) {
        this.noticeNumber = noticeNumber;
    }

    public String getHousingType() {
        return housingType;
    }

    public void setHousingType(String housingType) {
        this.housingType = housingType;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    public static ArrayList<Emlak> getList() {
        ArrayList<Emlak> emlakList = new ArrayList<>();
        Emlak obj;

        try {
            Statement st = DB_Connector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("Select * from Ilanlar1");
            while (rs.next()) {
                obj = new Emlak(rs.getInt("ID"), rs.getInt("noticeNumber"), rs.getString("housingType"), rs.getString("noticeType"), rs.getString("city"), rs.getInt("price"), rs.getInt("field"));
                emlakList.add(obj);

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return emlakList;
    }

    public static ArrayList<Emlak> getListOfID() {
        ArrayList<Emlak> emlakList = new ArrayList<>();
        Emlak obj;

        try {
            Statement st = DB_Connector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("Select ID from Ilanlar1");
            while (rs.next()) {
                obj = new Emlak(rs.getInt("ID"));
                emlakList.add(obj);

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return emlakList;
    }


    //ilan ekleme
    public static boolean add(int ID, int noticeNumber, String housingType, String noticeType, String city, int price, int field) {

        String query = "INSERT INTO Ilanlar1 (ID,noticeNumber,housingType,noticeType,city,price,field) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = DB_Connector.getInstance().prepareStatement(query);
            pr.setInt(1, ID);
            pr.setInt(2, noticeNumber);
            pr.setString(3, housingType);
            pr.setString(4, noticeType);
            pr.setString(5, city);
            pr.setInt(6, price);
            pr.setInt(7, field);

            return pr.executeUpdate() != -1;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    //bura ilanlari guncelleme kismi icin

    public static boolean update(int ID, int noticeNumber, String housingType, String noticeType, String city, int price, int field) {
        String query = "UPDATE Ilanlar1 SET noticeNumber=?,housingType=?,noticeType=?,city=?,price=?,field=? WHERE ID = ?";
        try {
            PreparedStatement pr = DB_Connector.getInstance().prepareStatement(query);
            pr.setInt(1, noticeNumber);
            pr.setString(2, housingType);
            pr.setString(3, noticeType);
            pr.setString(4, city);
            pr.setInt(5, price);
            pr.setInt(6, field);
            pr.setInt(7, ID);
            return pr.executeUpdate() != -1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return true;

    }

    public static Emlak getFetch(int id) {
        Emlak obj = null;
        String query = "Select * from Ilanlar1 where ID = ?";

        try {
            PreparedStatement pr = DB_Connector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                obj = new Emlak(rs.getInt("ID"), rs.getInt("noticeNumber"), rs.getString("housingType"), rs.getString("noticeType"), rs.getString("city"), rs.getInt("price"), rs.getInt("field"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }



    public static boolean delete(int ID){
        String query = "Delete from Ilanlar1 Where ID = ?";
        try {
            PreparedStatement pr = DB_Connector.getInstance().prepareStatement(query);
            pr.setInt(1,ID);
            return pr.executeUpdate() != -1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }


}
