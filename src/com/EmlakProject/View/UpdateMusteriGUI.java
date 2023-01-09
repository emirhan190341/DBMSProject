package com.EmlakProject.View;

import com.EmlakProject.Helper.Config;
import com.EmlakProject.Helper.Helper;
import com.EmlakProject.Model.Musteriler;

import javax.swing.*;

public class UpdateMusteriGUI  extends  JFrame{
    private JPanel wrapper;
    private JTextField fld_musteri_name;
    private JButton btn_update;
    private Musteriler musteriler;

     public UpdateMusteriGUI(Musteriler musteriler){
         this.musteriler =  musteriler;
         add(wrapper);
         setSize(300,150);
         setLocation(Helper.screenCenterPosition("x",getSize()),Helper.screenCenterPosition("y",getSize()));
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         setTitle(Config.PROJECT_TITLE);
         setVisible(true);
    }





}
