package com.EmlakProject.View;

import com.EmlakProject.Helper.Config;
import com.EmlakProject.Helper.Helper;

import javax.swing.*;

public class DanismanGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;

    public  DanismanGUI()   {
        add(wrapper);
        setSize(300, 150);
        setLocation(Helper.screenCenterPosition("x", getSize()), Helper.screenCenterPosition("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Helper.setLayout();
        DanismanGUI danismanGUI = new DanismanGUI();
    }
}
