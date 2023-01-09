package com.EmlakProject.View;

import com.EmlakProject.Helper.Config;
import com.EmlakProject.Helper.Helper;
import com.EmlakProject.Model.Emlak;
import com.EmlakProject.Model.Musteriler;
import com.EmlakProject.Model.Sekreter;
import com.EmlakProject.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SekreterGUI  extends JFrame {


    private JPanel wrapper;
    private JTable tbl_emlak_list;
    private JTabbedPane pnl_ilan_list;
    private JScrollPane scrl_emlak_list;
    private JPanel pnl_emlak_add;
    private JTextField fld_emlak_id;
    private JTextField fld_emlak_numara;
    private JTextField fld_emlak_konuk_turu;
    private JTextField fld_emlak_ilan_turu;
    private JTextField fld_emlak_sehir;
    private JTextField fld_emlak_fiyat;
    private JTextField fld_emlak_alan;
    private JButton btn_emlak_add;
    private JScrollPane scrl_musteri_list;
    private JTable tbl_musteri_list;
    private JTextField fld_musteri_ad;
    private JTextField fld_musteri_soyad;
    private JTextField fld_musteri_numara;
    private JTextField fld_musteri_mail;
    private JTextField fld_musteri_uyelik_tipi;
    private JTextField fld_musteri_adres;
    private JButton btn_musteri_add;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_logout;

    private JPopupMenu emlakMenu; //burada popup menu ayarlayacagiz
    private DefaultTableModel mdl_emlak_list;
    private Object[] row_emlak_list;  //ilanlar tablosundaki rowlari temsil eden object

    private DefaultTableModel mdl_musteri_list; //musteri tablosunun modelini ayarliyoruz
    private Object[] row_musteri_list;




    public SekreterGUI() {

        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.screenCenterPosition("x", getSize()), Helper.screenCenterPosition("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        //operatorGUI da olan hos geldiniz textine ozellik ekledik.
        lbl_welcome.setText("Hos geldin: Sekreter");

        // ilanlar tablosunda ilan guncelle sil islemleri
        // ilan ustune sag tiklayinca ortaya cikar
        emlakMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Guncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        emlakMenu.add(updateMenu);
        emlakMenu.add(deleteMenu);


        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_emlak_list.getValueAt(tbl_emlak_list.getSelectedRow(), 0).toString());
            UpdateEmlakGUI updateGUI = new UpdateEmlakGUI(Emlak.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadEmlakModel();
                }
            });
        });


        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int select_id = Integer.parseInt(tbl_emlak_list.getValueAt(tbl_emlak_list.getSelectedRow(), 0).toString());
                if (Emlak.delete(select_id)) {
                    Helper.showMsg("Tablodan bir ilan silindi");
                    loadEmlakModel();
                } else {
                    Helper.showMsg("error");
                }
            }

        });


        //ilanlar tablosu
        mdl_emlak_list = new DefaultTableModel();
        Object[] col_emlak_list = {"ID", "İlan Numarası", "Konuk Türü", "İlan Türü", "Şehir", "Fiyat", "Alan(metrekare)"};
        mdl_emlak_list.setColumnIdentifiers(col_emlak_list);
        row_emlak_list = new Object[col_emlak_list.length];

        loadEmlakModel();

        tbl_emlak_list.setModel(mdl_emlak_list);
        tbl_emlak_list.setComponentPopupMenu(emlakMenu);//popup menuyu set model diyerek uygulamaya ekliyoruz
        tbl_emlak_list.getTableHeader().setReorderingAllowed(false); // programda baslik tablolarin yerlerini degistirmeyi durdurur
        tbl_emlak_list.getColumnModel().getColumn(0).setMaxWidth(50);


        // burada ilanlar tablosundaki popup menude sadece 1 ilana tiklama ayarini yapiyoruz
        tbl_emlak_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_emlak_list.rowAtPoint(point);// burada mousenin hangi rowda oldugunu gosteriyoruz. bu sayede hangi kullaniciyi guncelleyip silecegimizi ayarlayacagiz.
                tbl_emlak_list.setRowSelectionInterval(selected_row, selected_row);
                //fakat bos yere tiklayinca guncelle/sil kapaniyor direk kapanmasini arastir 5. bolum 30. dakika
            }
        });

        //ilan ekleme butonunu aktif hale getiriyoruz
        btn_emlak_add.addActionListener(e -> {

            if
            ((Helper.isFieldEmpty(fld_emlak_id) || Helper.isFieldEmpty(fld_emlak_numara) || Helper.isFieldEmpty(fld_emlak_konuk_turu) || Helper.isFieldEmpty(fld_emlak_ilan_turu) || Helper.isFieldEmpty(fld_emlak_sehir) || Helper.isFieldEmpty(fld_emlak_fiyat) || Helper.isFieldEmpty(fld_emlak_alan))) {
                Helper.showMsg("fill");
            }
            else {

                int ID = Integer.parseInt(fld_emlak_id.getText());
                int numara = Integer.parseInt(fld_emlak_numara.getText());
                String konukTuru = fld_emlak_konuk_turu.getText();
                String ilanTuru = fld_emlak_ilan_turu.getText();
                String sehir = fld_emlak_sehir.getText();
                int fiyat = Integer.parseInt(fld_emlak_fiyat.getText());
                int alan = Integer.parseInt(fld_emlak_alan.getText());

                if (Emlak.add(ID, numara, konukTuru, ilanTuru, sehir, fiyat, alan)) {

                    Helper.showMsg("done");
                    loadEmlakModel();

                    fld_emlak_id.setText(null);
                    fld_emlak_numara.setText(null);
                    fld_emlak_konuk_turu.setText(null);
                    fld_emlak_ilan_turu.setText(null);
                    fld_emlak_sehir.setText(null);
                    fld_emlak_fiyat.setText(null);
                    fld_emlak_alan.setText(null);

                } else {
                    Helper.showMsg("error");
                }

            }

        });


        //cikis yap butonunu aktif hale getirmek
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });


        //musteri tablosu
        //musteri tablosu

        mdl_musteri_list = new DefaultTableModel();
        Object[] col_musteri_list = {"ID","Ad","Soyad","Numara","Mail","Uyelik Tipi","Adres"};
        mdl_musteri_list.setColumnIdentifiers(col_musteri_list);
        row_musteri_list = new Object[col_musteri_list.length];
        loadMusteriModel();

        tbl_musteri_list.setModel(mdl_musteri_list);
        tbl_musteri_list.getTableHeader().setReorderingAllowed(false);
        tbl_musteri_list.getColumnModel().getColumn(0).setMaxWidth(50); //burasi id column kismini daha kucuk gosterir



        btn_musteri_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_musteri_ad) || Helper.isFieldEmpty(fld_musteri_adres) || Helper.isFieldEmpty(fld_musteri_mail) || Helper.isFieldEmpty(fld_musteri_numara) || Helper.isFieldEmpty(fld_musteri_soyad) || Helper.isFieldEmpty(fld_musteri_uyelik_tipi)){
                Helper.showMsg("fill");
            }else {
                if(Musteriler.add(fld_musteri_ad.getText(),fld_musteri_soyad.getText(),fld_musteri_numara.getText(),fld_musteri_mail.getText(),fld_musteri_uyelik_tipi.getText(),fld_musteri_adres.getText())){
                    Helper.showMsg("tabloya bir musteri eklendi");
                    loadMusteriModel();
                    fld_musteri_ad.setText(null);
                    fld_musteri_soyad.setText(null);
                    fld_musteri_numara.setText(null);
                    fld_musteri_adres.setText(null);
                    fld_musteri_mail.setText(null);
                    fld_musteri_uyelik_tipi.setText(null);
                }else{
                    Helper.showMsg("error");
                }
            }
        });
    }



    private void loadMusteriModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_musteri_list.getModel();
        clearModel.setRowCount(0);

        for(Musteriler obj : Musteriler.getList()){
            int i = 0;
            row_musteri_list[i++] = obj.getId();
            row_musteri_list[i++] = obj.getAd();
            row_musteri_list[i++] = obj.getSoyad();
            row_musteri_list[i++] = obj.getNumara();
            row_musteri_list[i++] = obj.getMail();
            row_musteri_list[i++] = obj.getUyelik_tipi();
            row_musteri_list[i++] = obj.getAdres();
            mdl_musteri_list.addRow(row_musteri_list);
        }
    }

    private void loadEmlakModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_emlak_list.getModel();
        clearModel.setRowCount(0);

        for (Emlak obj : Emlak.getList()) {
            int i = 0;
            row_emlak_list[i++] = obj.getID();
            row_emlak_list[i++] = obj.getNoticeNumber();
            row_emlak_list[i++] = obj.getHousingType();
            row_emlak_list[i++] = obj.getNoticeType();
            row_emlak_list[i++] = obj.getCity();
            row_emlak_list[i++] = obj.getPrice();
            row_emlak_list[i++] = obj.getField();


            mdl_emlak_list.addRow(row_emlak_list);

        }
    }




    public static void main(String[] args) {
        Helper.setLayout();
        SekreterGUI sekreterGUI = new SekreterGUI();


    }

}
