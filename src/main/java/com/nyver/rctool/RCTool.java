package com.nyver.rctool;

import com.nyver.rctool.csv.CvsAdapter;
import com.nyver.rctool.csv.CvsAdapterException;
import com.nyver.rctool.model.Revision;
import com.nyver.rctool.treetable.CvsTreeTableModel;
import com.nyver.rctool.worker.CvsWorker;
import com.nyver.rctool.worker.TrackerWorker;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.JXTreeTable;

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
    private JXTreeTable cvsTreeTable;
    private JLabel StatusBarLabel;
    private JXStatusBar StatusBar;
    private JXTreeTable trackerTreeTable;

    AppSettings settings = new AppSettings();

    public RCTool()
    {
        super("RCTool");

        try {
            settings.load();
            setContentPane(MainPanel);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            initSettings();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.OK_OPTION);
            System.exit(0);
        }

        try {
            initCvsTreeTable();
        } catch (CvsAdapterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.OK_OPTION);
        }

        initTrackerTreeTable();
        setVisible(true);
    }

    private void initSettings()
    {
        setSize(settings.getInt(AppSettings.SETTING_WINDOW_START_WIDTH), settings.getInt(AppSettings.SETTING_WINDOW_START_HEIGHT));
        VerticalSplitPane.setDividerLocation(settings.getInt(AppSettings.SETTING_VERTICAL_PANE_DIVIDER_LOCATION));
        HorizontalSplitPane.setDividerLocation(settings.getInt(AppSettings.SETTING_HORIZONTAL_PANE_DIVIDER_LOCATION));
    }

    private void initCvsTreeTable() throws CvsAdapterException
    {
        new CvsWorker(cvsTreeTable, settings).execute();
    }

    private void initTrackerTreeTable()
    {
        new TrackerWorker(trackerTreeTable, settings).execute();
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
