package com.nyver.rctool;

import com.nyver.rctool.csv.CvsAdapter;
import com.nyver.rctool.csv.CvsAdapterException;
import com.nyver.rctool.model.RevisionList;
import com.nyver.rctool.tracker.TrackerAdapter;
import com.nyver.rctool.treetable.CvsTreeTableModel;
import com.nyver.rctool.treetable.TrackerTreeTableModel;
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
        CvsTreeTableModel model = new CvsTreeTableModel();
        cvsTreeTable.setTreeTableModel(model);

        SwingWorker taskFetchRevisions = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                StatusBarLabel.setText(String.format("Fetching revisions from repository (%s)...", settings.get(AppSettings.SETTING_CVS_HOST)));
                CvsAdapter cvsAdapter = CvsAdapter.factory(
                    settings.get(AppSettings.SETTING_CVS_TYPE),
                    settings.get(AppSettings.SETTING_CVS_HOST),
                    settings.get(AppSettings.SETTING_CVS_USER),
                    settings.get(AppSettings.SETTING_CVS_PASSWORD)
                );
                CvsTreeTableModel model = new CvsTreeTableModel(cvsAdapter.getRevisions());
                cvsTreeTable.setTreeTableModel(model);
                StatusBarLabel.setText("Complete");
                return null;
            }
        };

        taskFetchRevisions.execute();
    }

    private void initTrackerTreeTable()
    {
        TrackerTreeTableModel model = new TrackerTreeTableModel();
        trackerTreeTable.setTreeTableModel(model);

        SwingWorker taskFetchIssues = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                TrackerAdapter trackerAdapter = TrackerAdapter.factory(
                        settings.get(AppSettings.SETTING_TRACKER_TYPE),
                        settings.get(AppSettings.SETTING_TRACKER_HOST),
                        settings.get(AppSettings.SETTING_TRACKER_USER),
                        settings.get(AppSettings.SETTING_TRACKER_PASSWORD)
                );

                TrackerTreeTableModel model = new TrackerTreeTableModel(trackerAdapter.getIssues());
                trackerTreeTable.setTreeTableModel(model);
                return null;
            }
        };

        taskFetchIssues.execute();
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
