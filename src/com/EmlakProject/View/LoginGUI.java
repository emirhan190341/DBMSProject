package com.EmlakProject.View;

import com.EmlakProject.Helper.Config;
import com.EmlakProject.Helper.Helper;
import com.EmlakProject.Model.Emlak;
import com.EmlakProject.Model.Operator;
import com.EmlakProject.Model.Sekreter;
import com.EmlakProject.Model.User;

import javax.swing.*;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JButton btn_login;

    public LoginGUI() {
        add(wrapper);
        setSize(450, 450);
        setLocation(Helper.screenCenterPosition("x", getSize()), Helper.screenCenterPosition("y", getSize()));// ekranin ortasinda baslamasini sagliyor
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_pass)) {
                Helper.showMsg("fill");
            } else {
                User u = User.getFetch(fld_user_uname.getText(), fld_user_pass.getText());
                if (u == null) {
                    Helper.showMsg("Kullanıcı Bulunamadı.");
                } else {
                    switch (u.getTitle()) {
                        case "admin":
                            OperatorGUI opGUI = new OperatorGUI((Operator) u);
                            break;
                        case "sekreter":
                            SekreterGUI sekreterGUI = new SekreterGUI();
                            break;
                        case "danisman":
                            DanismanGUI danismanGUI = new DanismanGUI();



                    }
                    dispose();
                }
            }

        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
