package com.nyver.rctool;

import javax.swing.*;

/**
 * RCTool main class
 *
 * @author Yuri Novitsky
 */
public class RCTool extends JFrame
{
    private int WINDOW_STARTUP_WIDTH = 800;
    private int WINDOW_STARTUP_HEIGHT = 600;

    private JPanel MainPanel;
    private JSplitPane VerticalSplitPane;

    public RCTool()
    {
        super("RCTool");
        setContentPane(MainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(WINDOW_STARTUP_WIDTH, WINDOW_STARTUP_HEIGHT);
        setVisible(true);
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
