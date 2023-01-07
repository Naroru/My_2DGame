package Frames;

import Managers.FrameManagers;

import javax.swing.*;
import java.awt.*;

import static javax.imageio.ImageIO.read;


public class StartFrame extends JFrame   {

    private JLabel labelChoose = new JLabel("Choose a way to work");
    private JButton useWeb = new JButton("Start game");
    private JButton usePC = new JButton("Use PC version");


    private Font startFrameFont = new Font(null,Font.BOLD,14);

   public StartFrame ()
   {
       FrameManagers.setDefaultSettingsToFrame(this);
       this.setSize(440,150);
       this.setTitle("Launch");

       labelChoose.setFont(startFrameFont);
       labelChoose.setBounds(110,20,300,30);
       labelChoose.setFont(new Font(null,Font.BOLD,16));


       useWeb.setBounds(50,65, 160, 30);
       useWeb.setFont(startFrameFont);
       useWeb.addActionListener(e -> onStartGameButtonClick());

       usePC.setBounds(240,65, 160, 30);
       usePC.setFont(startFrameFont);
       usePC.addActionListener(e -> onUsePCButtonClick());



       this.add(useWeb);
       this.add(usePC);
       this.add(labelChoose);
   }



    private void onStartGameButtonClick()
    {
        FrameManagers.showWindow(new Game());
        this.dispose();
    }

    private void onUsePCButtonClick()
    {

        //FrameManagers.showWindow(new LoginFrame());
        //this.dispose();
    }

}
