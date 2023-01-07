package com.EmlakProject.Helper;

import com.EmlakProject.View.OperatorGUI;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //burasi tamamen operatorGUI classi icin yardimci olabilecek methodlari yapmak icin kuruldu
    //burada ekranin ortasi aliniyor uygulama baslayinca ekranin ortasina gelececk
    public static int screenCenterPosition(String axis, Dimension size) {
        int point = 0;

        switch (axis) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default:
                point = 0;

        }
        return point;

    }

    public static Class<?> getClassForName() throws ClassNotFoundException {
        return Class.forName(Config.CLASS_FOR_NAME);
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();

    }

    //burasi alanlari bos biraktigimizda cikan mesaj
    public static void showMsg(String str) {
        emlakPageTR();
        String msg;
        String title = null;
        switch (str) {
            case "fill":
                msg = "Lutfen alanlari doldurunuz";
                title = "Hata";
                break;
            case "done":
                msg = "Islem Basarili!";
                title = "Sonuc";
                break;
            case "error":
                msg = "Bir hata olustu!";
                title = "Hata!";
                break;

            default:
                msg = str;

        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);

    }

    //onaylama sorusu
    public static boolean confirm(String str) {
        emlakPageTR();
        String msg;

        switch (str) {
            case "sure":
                msg = "Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
                break;

            default:
                msg = "str";
        }

        return JOptionPane.showConfirmDialog(null, msg, "Son Kararın Mı ?", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void emlakPageTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");

    }


}

