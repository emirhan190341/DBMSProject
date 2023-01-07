package com.EmlakProject.View;

import com.EmlakProject.Helper.Config;
import com.EmlakProject.Helper.Helper;
import com.EmlakProject.Model.Emlak;

import javax.swing.*;

public class UpdateEmlakGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_emlak_id;
    private JTextField fld_emlak_ilan_numarasi;
    private JTextField fld_emlak_konuk_turu;
    private JTextField fld_emlak_ilan_turu;
    private JTextField fld_emlak_sehir;
    private JTextField fld_emlak_fiyat;
    private JTextField fld_emlak_alan;
    private JButton btn_update;
    private Emlak emlak;

    //burada ilan guncellemek icin arayuz olusturuyoruz
    public UpdateEmlakGUI(Emlak emlak) {
        this.emlak = emlak;
        add(wrapper);
        setSize(300, 150);
        setLocation(Helper.screenCenterPosition("x", getSize()), Helper.screenCenterPosition("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        fld_emlak_id.setText(String.valueOf(emlak.getID()));
        fld_emlak_ilan_numarasi.setText(String.valueOf(emlak.getNoticeNumber()));
        fld_emlak_konuk_turu.setText(emlak.getHousingType());
        fld_emlak_ilan_turu.setText(emlak.getNoticeType());
        fld_emlak_sehir.setText(emlak.getCity());
        fld_emlak_fiyat.setText(String.valueOf(emlak.getPrice()));
        fld_emlak_alan.setText(String.valueOf(emlak.getField()));
        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_emlak_ilan_numarasi) || Helper.isFieldEmpty(fld_emlak_konuk_turu) || Helper.isFieldEmpty(fld_emlak_ilan_turu) || Helper.isFieldEmpty(fld_emlak_sehir) || Helper.isFieldEmpty(fld_emlak_fiyat) || Helper.isFieldEmpty(fld_emlak_alan)) {

                Helper.showMsg("fill");
            } else {
                if (Emlak.update(emlak.getID(), Integer.parseInt(fld_emlak_ilan_numarasi.getText()), fld_emlak_konuk_turu.getText(), fld_emlak_ilan_turu.getText(), fld_emlak_sehir.getText(), Integer.parseInt(fld_emlak_fiyat.getText()), Integer.parseInt(fld_emlak_alan.getText()))) {
                    Helper.showMsg("done");

                }
                dispose();
            }

        });
    }

}
