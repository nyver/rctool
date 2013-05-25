package com.nyver.rctool;

import com.nyver.rctool.adapter.CvsAdapter;
import com.nyver.rctool.adapter.CvsAdapterException;
import com.nyver.rctool.model.RevisionList;
import com.nyver.rctool.treetable.CvsTreeTableModel;
import org.jdesktop.swingx.JXStatusBar;
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
    private JLabel StatusBarLabel;
    private JXStatusBar StatusBar;

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

        SwingWorker task = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                StatusBarLabel.setText(String.format("Fetching revisions from repository (%s)...", settings.get(AppSettings.SETTING_CVS_HOST)));
                RevisionList list = new RevisionList(
                        CvsAdapter.factory(
                                settings.get(AppSettings.SETTING_CVS_TYPE),
                                settings.get(AppSettings.SETTING_CVS_HOST),
                                settings.get(AppSettings.SETTING_CVS_USER),
                                settings.get(AppSettings.SETTING_CVS_PASSWORD)
                        )
                );
                CvsTreeTableModel model = new CvsTreeTableModel(list.getList());
                cvsTreeTable.setTreeTableModel(model);
                StatusBarLabel.setText("Complete");
                return null;
            }
        };

        task.execute();

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
