package org.OlegChukhlantsev.CommonManagers;

import javax.swing.*;

public class FrameManagers {

    public static void showWindow(JFrame frame)
    {
        frame.setVisible(true);
    }


    public static void setDefaultSettingsToFrame(JFrame frame)
    {
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(frame);
        frame.setVisible(true);
    }


}
