package com.nyver.rctool;

import com.nyver.rctool.csv.CvsAdapter;
import com.nyver.rctool.csv.CvsAdapterException;
import com.nyver.rctool.model.Issue;
import com.nyver.rctool.model.Revision;
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
        SwingWorker taskFetchRevisions = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                cvsTreeTable.setEnabled(false);

                CvsTreeTableModel loadModel = new CvsTreeTableModel();
                loadModel.setColumns(new String[] {""});
                loadModel.add(new Revision(String.format("Fetching revisions from repository (%s)...", settings.get(AppSettings.SETTING_CVS_HOST))));
                cvsTreeTable.setTreeTableModel(loadModel);

                CvsAdapter cvsAdapter = CvsAdapter.factory(
                    settings.get(AppSettings.SETTING_CVS_TYPE),
                    settings.get(AppSettings.SETTING_CVS_HOST),
                    settings.get(AppSettings.SETTING_CVS_USER),
                    settings.get(AppSettings.SETTING_CVS_PASSWORD)
                );

                CvsTreeTableModel model = new CvsTreeTableModel(cvsAdapter.getRevisions());
                cvsTreeTable.setTreeTableModel(model);
                cvsTreeTable.setEnabled(true);
                return null;
            }
        };

        taskFetchRevisions.execute();
    }

    private void initTrackerTreeTable()
    {
        SwingWorker taskFetchIssues = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                trackerTreeTable.setEnabled(false);

                TrackerTreeTableModel loadModel = new TrackerTreeTableModel();
                loadModel.setColumns(new String[] {""});
                loadModel.add(new Issue(String.format("Fetching issues from tracker (%s)...", settings.get(AppSettings.SETTING_TRACKER_HOST))));
                trackerTreeTable.setTreeTableModel(loadModel);

                TrackerAdapter trackerAdapter = TrackerAdapter.factory(
                        settings.get(AppSettings.SETTING_TRACKER_TYPE),
                        settings.get(AppSettings.SETTING_TRACKER_HOST),
                        settings.get(AppSettings.SETTING_TRACKER_USER),
                        settings.get(AppSettings.SETTING_TRACKER_PASSWORD)
                );

                TrackerTreeTableModel model = new TrackerTreeTableModel(trackerAdapter.getIssues());
                trackerTreeTable.setTreeTableModel(model);
                trackerTreeTable.setEnabled(true);
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
