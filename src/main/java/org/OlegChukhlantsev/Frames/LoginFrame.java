package org.OlegChukhlantsev.Frames;

import org.OlegChukhlantsev.CommonManagers.FrameManagers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {

    private JLabel labelUsername = new JLabel("Username");
    private JLabel labelPass = new JLabel("Password");

    private JTextField fieldUsername = new JTextField();
    private JTextField fieldPass = new JTextField();


    private Font labelFont = new Font(null, Font.BOLD, 15);

    private JButton login = new JButton("Login");
    private JLabel  labelNewAcc = new JLabel("Create new account");
    private  JLabel labelFirstTime = new JLabel("First time here?");


    public LoginFrame() {

        FrameManagers.setDefaultSettingsToFrame(this);
        this.setSize(450, 270);
        this.setTitle("Login");

        labelFirstTime.setFont(labelFont);
        labelFirstTime.setBounds(100,160,150,30);

        createLinkNewAcc();
        labelNewAcc.setBounds(220,160,250,30);
        labelNewAcc.setFont(labelFont);

        labelUsername.setBounds(40, 50, 100, 30);
        labelUsername.setFont(labelFont);

        labelPass.setBounds(40,90,100,30);
        labelPass.setFont(labelFont);

        fieldUsername.setBounds(130,50,250,30);
        fieldPass.setBounds(130,90,250,30);

        login.setBounds(270,130,110,30);

        this.add(labelUsername);
        this.add(labelPass);
        this.add(fieldUsername);
        this.add(fieldPass);
        this.add(login);
        this.add(labelNewAcc);
        this.add(labelFirstTime);

    }

    private void createLinkNewAcc()
    {
        labelNewAcc.setForeground(Color.BLUE);
        labelNewAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelNewAcc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("New user was created");
            }

            //при наведении мыши меняем текст на ссылку, чтобы он подчеркивался
            @Override
            public void mouseEntered(MouseEvent e) {
                labelNewAcc.setText("<html><a href=''>Create new account</a></html>");

            }

            //при уведении мыши меняем текст на обычный, чтобы он не подчеркивался
            @Override
            public void mouseExited(MouseEvent e) {
                labelNewAcc.setText("Create new account");
            }

        });

    }
}
