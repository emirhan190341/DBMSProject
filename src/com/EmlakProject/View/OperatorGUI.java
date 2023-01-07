package com.EmlakProject.View;

import com.EmlakProject.Model.Emlak;
import com.EmlakProject.Model.Musteriler;
import com.EmlakProject.Model.Operator;
import com.EmlakProject.Helper.*;
import com.EmlakProject.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane pnl_ilan_list;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JPasswordField fld_user_pass;
    private JLabel uyelikTipiLabel;
    private JButton btn_user_add;
    private JComboBox cbm_user_title;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_name;
    private JTextField field_sh_user_name;
    private JComboBox combo_sh_user_title;
    private JButton btn_user_sh;
    private JScrollPane scrl_emlak_list;
    private JTable tbl_emlak_list;
    private JPanel pnl_emlak_add;
    private JTextField fld_emlak_id;
    private JButton btn_emlak_add;
    private JTextField fld_emlak_numara;
    private JTextField fld_emlak_konuk_turu;
    private JTextField fld_emlak_ilan_turu;
    private JTextField fld_emlak_sehir;
    private JTextField fld_emlak_fiyat;
    private JTextField fld_emlak_alan;
    private JScrollPane scrl_musteri_list;
    private JTable tbl_musteri_list;
    private JTextField fld_musteri_ad;
    private JTextField fld_musteri_soyad;
    private JTextField fld_musteri_numara;
    private JTextField fld_musteri_mail;
    private JTextField fld_musteri_uyelik_tipi;
    private JTextField fld_musteri_adres;
    private JButton btn_musteri_add;

    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;

    private JPopupMenu emlakMenu; //burada popup menu ayarlayacagiz
    private DefaultTableModel mdl_emlak_list;
    private Object[] row_emlak_list;  //ilanlar tablosundaki rowlari temsil eden object


    private DefaultTableModel mdl_musteri_list; //musteri tablosunun modelini ayarliyoruz
    private Object[] row_musteri_list;

    private final Operator operator;

    public OperatorGUI(Operator operator) {
        this.operator = operator;


        //burada wrapper yani dis cerceve ekleniyor
        add(wrapper);

        //wrapperin boyu ayarlaniyor
        setSize(1000, 500);

        int x = Helper.screenCenterPosition("x", getSize());
        int y = Helper.screenCenterPosition("y", getSize());
        setLocation(Helper.screenCenterPosition("x", getSize()), Helper.screenCenterPosition("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setTitle(Config.PROJECT_TITLE);

        setVisible(true);

        //operatorGUI da olan hos geldiniz textine ozellik ekledik.
        lbl_welcome.setText("Hos geldin: " + operator.getFullName());

        // ModelUserList yani tabloyu olusturuyoruz
        mdl_user_list = new DefaultTableModel() {
            @Override
            // burada 0. kolonu yani id kismini degistirilemez yapiyoruz
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Ad Soyad", "Kullanici Adi", "Sifre", "Uyelik Tipi"};
        //Object[] col_user_list = {"Ad Soyad", "Kullanici Adi", "Sifre", "Uyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];

        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        //tabloda secilen degeri silme
        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_user_id.setText(selected_user_id);
            } catch (Exception exception) {

            }
        });


        //tabloda degistirilen veriler veritabaninda da guncellenmesi
        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String user_uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String user_pass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();

                if (User.update(user_id, user_name, user_uname, user_pass, user_type)) {
                    Helper.showMsg("done");
                }
                loadUserModel();
            }
        });


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
                    Helper.showMsg("done");
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



        //musteri tablosu

        mdl_musteri_list = new DefaultTableModel();
        Object[] col_musteri_list = {"ID","Ad","Soyad","Numara","Mail","Uyelik Tipi","Adres"};
        mdl_musteri_list.setColumnIdentifiers(col_musteri_list);
        row_musteri_list = new Object[col_musteri_list.length];
        loadMusteriModel();

        tbl_musteri_list.setModel(mdl_musteri_list);
        tbl_musteri_list.getTableHeader().setReorderingAllowed(false);
        tbl_musteri_list.getColumnModel().getColumn(0).setMaxWidth(50); //burasi id column kismini daha kucuk gosterir



        //kullanici ekleme
        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_pass)) {
                Helper.showMsg("fill");
            } else {
                String fullName = fld_user_name.getText();
                String username = fld_user_uname.getText();
                String pass = fld_user_pass.getText();
                String title = cbm_user_title.getSelectedItem().toString();
                if (User.add(fullName, username, pass, title)) {
                    Helper.showMsg("done");
                    loadUserModel();

                    //verileri girdikten sonra bosluklari silmek icin
                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_pass.setText(null);
                }
            }
        });

        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)) {
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if (User.delete(user_id)) {
                        Helper.showMsg("done");
                        loadUserModel();
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }
        });
        //olusturulan method ile sql serverinde sorgu yaptiktan sonra aranilan degleri geri donduruyoruz
        btn_user_sh.addActionListener(e -> {
            String fullName = fld_sh_user_name.getText();
            String userName = field_sh_user_name.getText();
            String title = combo_sh_user_title.getSelectedItem().toString();
            //String query = User.searchQuery(fullName, userName, title);

            loadUserModel(User.searchQuery(fullName, userName, title));
        });

        //cikis yap butonunu aktif hale getirmek
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });


        //ilan ekleme butonunu aktif hale getiriyoruz
        btn_emlak_add.addActionListener(e -> {
            if ((Helper.isFieldEmpty(fld_emlak_id) || Helper.isFieldEmpty(fld_emlak_numara) || Helper.isFieldEmpty(fld_emlak_konuk_turu) || Helper.isFieldEmpty(fld_emlak_ilan_turu) || Helper.isFieldEmpty(fld_emlak_sehir) || Helper.isFieldEmpty(fld_emlak_fiyat) || Helper.isFieldEmpty(fld_emlak_alan))) {
                Helper.showMsg("fill");
            } else {

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
        btn_musteri_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_musteri_ad) || Helper.isFieldEmpty(fld_musteri_adres) || Helper.isFieldEmpty(fld_musteri_mail) || Helper.isFieldEmpty(fld_musteri_numara) || Helper.isFieldEmpty(fld_musteri_soyad) || Helper.isFieldEmpty(fld_musteri_uyelik_tipi)){
                Helper.showMsg("fill");
            }else {
                if(Musteriler.add(fld_musteri_ad.getText(),fld_musteri_soyad.getText(),fld_musteri_numara.getText(),fld_musteri_mail.getText(),fld_musteri_uyelik_tipi.getText(),fld_musteri_adres.getText())){
                    Helper.showMsg("done");
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


    //her musteri tablosu guncellendiginde onu bu method ile yenileyecegiz.
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

    //ilanlar

    //tum verileri atacagimiz tabloyu olusturuyoruz
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


    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : User.getList()) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getFullName();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getTitle();
            mdl_user_list.addRow(row_user_list);

        }

    }


    //yani burada arastirdigimiz degerlerin degerlerini aliyoruz.
    public void loadUserModel(ArrayList<User> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : list) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getFullName();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getTitle();
            mdl_user_list.addRow(row_user_list);

        }

    }

    //Musteriler


//    public static void showWindow(){
//        JFrame frame = new JFrame("WINDOW 1");
//        frame.setBounds(100,100,100,100); //tiklama windowun kenarliklarini belirliyor
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // windowu kapatmaya yariyor
//        frame.getContentPane().setLayout(null); //hicbir layout istemiyoruz
//
//        frame.setVisible(true); //gorunmesini sagliyor
//    }


    public static void main(String[] args) {
        Operator op = new Operator();
        op.setId(1);
        op.setFullName("Emirhan");
        op.setPass("1234");
        op.setTitle("operator");
        op.setUsername("mustafa");

//        showWindow();

        OperatorGUI opGUI = new OperatorGUI(op);

        Helper.setLayout();
    }
}
