package com.EmlakProject.Helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultSetToExcelApp {


    public static void main(String[] args) {


        Connection con;
        String query = "Select * from Musteriler";


        {
            try {
                con = DB_Connector.getInstance();
                PreparedStatement pr = con.prepareStatement(query);
                ResultSet rs = pr.executeQuery();
                writeToExcel(rs,"Musteriler1.xlsx");

            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeToExcel(ResultSet rs,String fileName) throws SQLException, IOException {
        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> columns = new ArrayList<String>(){{
           for(int i=1;i <= rsmd.getColumnCount();i++){
               add(rsmd.getColumnLabel(i));
           }
        }};

        try (Workbook book = new XSSFWorkbook()){
            Sheet sheet = book.createSheet();
            Row header = sheet.createRow(0);

            for(int i = 0; i < columns.size();i++){
                Cell cell = header.createCell(i);
                cell.setCellValue(columns.get(i));
            }

            int rowIndex = 0;
            while (rs.next()){
                Row row = sheet.createRow(++rowIndex);
                for(int i = 0; i < columns.size();i++){
                    Cell cell = row.createCell(i);
                     String val = Objects.toString(rs.getObject(columns.get(i)), "");
                     cell.setCellValue(val);
                }
            }

            try(FileOutputStream fos = new FileOutputStream(fileName)){
                book.write(fos);
            }

        }




    }
}
