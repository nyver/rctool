package com.nyver.rctool;

import javax.swing.*;
import java.io.IOException;

/**
 * RCTool main class
 *
 * @author Yuri Novitsky
 */
public class RCTool extends JFrame
{
    private JPanel MainPanel;
    private JSplitPane VerticalSplitPane;
    private JSplitPane HorizontalSplitPane;

    AppSettings settings = new AppSettings();

    public RCTool()
    {
        super("RCTool");

        try {
            settings.load();

            setContentPane(MainPanel);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setSize(Integer.parseInt(settings.get(AppSettings.SETTING_WINDOW_START_WIDTH)), Integer.parseInt(settings.get(AppSettings.SETTING_WINDOW_START_HEIGHT)));
            setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.OK_OPTION);
        }
    }

    public static void main(String[] args)
    {
        AppStarter starter = new AppStarter(args);
        SwingUtilities.invokeLater(starter);
    }
}


class AppStarter extends Thread
{
    private String[] args;

    public AppStarter(String[] args)
    {
        this.args = args;
    }

    public void run()
    {
        RCTool rcTool = new RCTool();
    }
}
