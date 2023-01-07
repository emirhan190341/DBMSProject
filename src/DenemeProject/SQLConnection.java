package DenemeProject;


import java.sql.*;

public class SQLConnection {


    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //Java'da sql serverine baglanmak
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String dbUrl = "jdbc:sqlserver://DESKTOP-2Q3LG7S;databaseName=EmlakDBMS;trustServerCertificate=true";

        //Servere baglanacagimiz username ve password
        String user = "admin";
        String password = "admin";

        // execute yapacagimiz query
        String selectSql = "Select * from people1";

        Connection connection = DriverManager.getConnection(dbUrl, user, password); //baglanti kuruluyor

        Statement st = connection.createStatement(); //update select insert vs. gibi seylerin yapilmasi
        ResultSet data=st.executeQuery(selectSql);

        //sirayla degerlerin basilmasi
        while (data.next()){
            System.out.println("ID: " + data.getInt("ID"));
            System.out.println("Firstname:" + data.getString("firstName"));
            System.out.println("Lastname:" + data.getString("lastname"));
            System.out.println("Age: " + data.getInt("Age"));
        }

        if (connection != null) {
            //System.out.println("Connected");
        } else {
            System.out.println("Not connected");
        }
    }





}

