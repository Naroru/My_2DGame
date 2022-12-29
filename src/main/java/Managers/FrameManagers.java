package Managers;

import javax.swing.*;
import java.awt.*;

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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
