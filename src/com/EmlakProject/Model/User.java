package com.EmlakProject.Model;

import com.EmlakProject.Helper.DB_Connector;
import com.EmlakProject.Helper.Helper;

import java.sql.*;
import java.util.ArrayList;

public class User {
    private int id;
    private String fullName;
    private String username;
    private String pass;
    private String title;

    public User() {
    }

    public User(int id, String fullName, String username, String pass, String title) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.pass = pass;
        this.title = title;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //databasedeki kullancilari burada tutacagiz.
    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "Select * from people";
        User obj;

        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Helper.getClassForName();
            Statement st = DB_Connector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("ID"));
                obj.setFullName(rs.getString("fullName"));
                obj.setUsername(rs.getString("userName"));
                obj.setPass(rs.getString("pass"));
                obj.setTitle(rs.getString("title"));
                userList.add(obj);


            }

            rs.close();
            st.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userList;
    }


    public static boolean add(String fullName, String username, String pass, String title) {

        String query = "INSERT INTO people (fullName,username,pass,title) VALUES (?,?,?,?)";
        User findUser = User.getFetch(username);
        if (findUser != null) {
            Helper.showMsg("Bu kullanici adi daha onceden kullanildi!");
            return false;
        }
        try {
            PreparedStatement pr = DB_Connector.getInstance().prepareStatement(query);
            pr.setString(1, fullName);
            pr.setString(2, username);
            pr.setString(3, pass);
            pr.setString(4, title);


            int response = pr.executeUpdate();

            if (response == -1) {
                Helper.showMsg("error");
            }
            pr.close();
            return response != -1;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());

        }
        return true;
    }


    public static User getFetch(String username) {
        User obj = null;
        String query = "Select * from people where username =?";
        try {
            PreparedStatement pr = DB_Connector.getInstance().prepareStatement(query);
            pr.setString(1, username);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setFullName(rs.getString("fullName"));
                obj.setUsername(rs.getString("userName"));
                obj.setPass(rs.getString("pass"));
                obj.setTitle(rs.getString("title"));

            }

            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }

        return obj;
    }

    public static User getFetch(String username, String pass) {
        User obj = null;
        String query = "Select * from people where username =? AND pass =?";
        try {
            PreparedStatement pr = DB_Connector.getInstance().prepareStatement(query);
            pr.setString(1, username);
            pr.setString(2, pass);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                switch (rs.getString("title")) {
                    case "admin":
                        obj = new Operator();
                        break;
                    case "sekreter":
                        obj = new Sekreter();
                    case "danisman":
                        obj = new Danisman();
                    default:
                        obj = new User();
                }
                obj.setId(rs.getInt("id"));
                obj.setFullName(rs.getString("fullName"));
                obj.setUsername(rs.getString("userName"));
                obj.setPass(rs.getString("pass"));
                obj.setTitle(rs.getString("title"));
            }
            pr.close();
            rs.close();

        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }



    public static boolean delete(int id) {
        String query = "DELETE FROM PEOPLE WHERE ID=?";
        try {
            PreparedStatement pr = DB_Connector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }


    //if ile git title'nin satici alici vb. seylerden farkli bir sey olmayacagini dogrula kod yaz

    public static boolean update(int id, String fullName, String username, String pass, String title) {
        String query = "UPDATE people SET fullName=?,username=?,pass=?,title=? WHERE ID = ?";
        User findUser = User.getFetch(username);
        //tabloda guncellenen deger databasedekine esitse, yani adi guncellerken ayni seyi bir daha guncelledigimizde hat almamak icin
        if (findUser != null && findUser.getId() != id) {
            Helper.showMsg("Bu kullanici adi daha onceden kullanildi!");
            return false;
        }
        try {
            PreparedStatement pr = DB_Connector.getInstance().prepareStatement(query);
            pr.setString(1, fullName);
            pr.setString(2, username);
            pr.setString(3, pass);
            pr.setString(4, title);
            pr.setInt(5, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return true;

    }

    public static ArrayList<User> searchUserList(String query) {
        ArrayList<User> userList = new ArrayList<>();
        User obj;

        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Helper.getClassForName();
            Statement st = DB_Connector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("ID"));
                obj.setFullName(rs.getString("fullName"));
                obj.setUsername(rs.getString("userName"));
                obj.setPass(rs.getString("pass"));
                obj.setTitle(rs.getString("title"));
                userList.add(obj);

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userList;
    }

    //dbde olusturdugumuz storedProcedure'u burada cagiriyoruz.
    public static ArrayList<User> searchQuery(String fullName, String userName, String title) {

        String query = "{call AramaYap(?,?)}";
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            CallableStatement cstmt = DB_Connector.getInstance().prepareCall(query);
            cstmt.setString(1,fullName);
            cstmt.setString(2,userName);
            ResultSet rs = cstmt.executeQuery();

            while (rs.next()) {

                obj = new User();
                obj.setId(rs.getInt("ID"));
                obj.setFullName(rs.getString("fullName"));
                obj.setUsername(rs.getString("userName"));
                obj.setPass(rs.getString("pass"));
                obj.setTitle(rs.getString("title"));
                userList.add(obj);
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  userList;
    }


    //arama butonu
    public static ArrayList<User> storedProcedure(String fullName, String userName, String title){
        Statement stmt = null;
        ArrayList<User> userList = new ArrayList<>();
        User obj;

        try {
            String sql = "EXEC [dbo].[AramaYap] @firstName = '" + fullName +  "', @fullName = '" + userName + "'";
            stmt =  DB_Connector.getInstance().createStatement();
            ResultSet rs =  stmt.executeQuery(sql);
            while (rs.next()) {

                obj = new User();
                obj.setId(rs.getInt("ID"));
                obj.setFullName(rs.getString("fullName"));
                obj.setUsername(rs.getString("userName"));
                obj.setPass(rs.getString("pass"));
                obj.setTitle(rs.getString("title"));
                userList.add(obj);
            }

            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return  userList;
    }


}
