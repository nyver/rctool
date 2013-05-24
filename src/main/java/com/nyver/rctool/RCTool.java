package com.nyver.rctool;

import com.nyver.rctool.model.RevisionList;
import com.nyver.rctool.treetable.CvsTreeTableModel;
import org.jdesktop.swingx.JXTreeTable;
import org.tigris.subversion.svnclientadapter.SVNClientException;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

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
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.OK_OPTION);
            System.exit(0);
        }

        try {
            initCvsTreeTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.OK_OPTION);
        }


        setVisible(true);
    }

    private void initSettings()
    {
        setSize(settings.getInt(AppSettings.SETTING_WINDOW_START_WIDTH), settings.getInt(AppSettings.SETTING_WINDOW_START_HEIGHT));
        VerticalSplitPane.setDividerLocation(settings.getInt(AppSettings.SETTING_VERTICAL_PANE_DIVIDER_LOCATION));
        HorizontalSplitPane.setDividerLocation(settings.getInt(AppSettings.SETTING_HORIZONTAL_PANE_DIVIDER_LOCATION));
    }

    private void initCvsTreeTable() throws MalformedURLException, SVNClientException, ParseException {
        RevisionList list = new RevisionList(
                settings.get(AppSettings.SETTING_SVN_HOST),
                settings.get(AppSettings.SETTING_SVN_USER),
                settings.get(AppSettings.SETTING_SVN_PASSWORD)
        );

        cvsTreeTable.setTreeTableModel(new CvsTreeTableModel(list.getList()));
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
