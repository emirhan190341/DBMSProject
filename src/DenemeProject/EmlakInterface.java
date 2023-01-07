package DenemeProject;

import javax.swing.*;
import java.awt.*;

public class EmlakInterface extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JTextField fieldUserName;
    private JTextField fieldPassword;
    private JPanel wBottom;
    private JButton btn_login;

    public EmlakInterface() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }
        //add(wrapper);
        setContentPane(wrapper);
        setSize(400, 300);
        setTitle("Application Name");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2;
        setLocation(x, y);
        setVisible(true);


        btn_login.addActionListener(e -> {
            if (fieldUserName.getText().length() == 0 || fieldPassword.getText().length() == 0) {
                JOptionPane.showMessageDialog(null,"Tum alanlari doldurun!","Hata",JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }
}
