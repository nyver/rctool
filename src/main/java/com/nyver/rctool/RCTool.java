package com.nyver.rctool;

import com.nyver.rctool.csv.CvsAdapter;
import com.nyver.rctool.csv.CvsAdapterException;
import com.nyver.rctool.model.Filter;
import com.nyver.rctool.model.Revision;
import com.nyver.rctool.tracker.TrackerAdapter;
import com.nyver.rctool.tracker.TrackerAdapterException;
import com.nyver.rctool.treetable.CvsTreeTableModel;
import com.nyver.rctool.worker.CvsWorker;
import com.nyver.rctool.worker.TrackerWorker;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.JXTreeTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;

/**
 * RCTool main class
 *
 * @author Yuri Novitsky
 */
public class RCTool extends JFrame
{
    private CvsAdapter cvsAdapter;
    private TrackerAdapter trackerAdapter;

    private JPanel MainPanel;
    private JSplitPane VerticalSplitPane;
    private JSplitPane HorizontalSplitPane;
    private JXTreeTable cvsTreeTable;
    private JLabel StatusBarLabel;
    private JXStatusBar StatusBar;
    private JXTreeTable trackerTreeTable;
    private JPanel FilterByPeriodPanel;
    private JButton FilterButton;
    private JPanel FilterPanel;
    private JLabel PeriodStartLabel;
    private JLabel PeriodEndLabel;
    private JXDatePicker PeriodStartDatePicker;
    private JXDatePicker PeriodEndDatePicker;

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
            initFilters();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.OK_OPTION);
            System.exit(0);
        }

        try {
            initCvsAdapter();
            initCvsTreeTable();
        } catch (CvsAdapterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.OK_OPTION);
        }

        try {
            initTrackerAdapter();
            initTrackerTreeTable();
        } catch (TrackerAdapterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.OK_OPTION);
        }
        setVisible(true);
    }

    public Filter getFilter()
    {
        return new Filter(PeriodStartDatePicker.getDate(), PeriodEndDatePicker.getDate());
    }

    private void initSettings()
    {
        setSize(settings.getInt(AppSettings.SETTING_WINDOW_START_WIDTH), settings.getInt(AppSettings.SETTING_WINDOW_START_HEIGHT));
        VerticalSplitPane.setDividerLocation(settings.getInt(AppSettings.SETTING_VERTICAL_PANE_DIVIDER_LOCATION));
        HorizontalSplitPane.setDividerLocation(settings.getInt(AppSettings.SETTING_HORIZONTAL_PANE_DIVIDER_LOCATION));
    }

    private void initCvsAdapter() throws CvsAdapterException
    {
        cvsAdapter = CvsAdapter.factory(
                settings.get(AppSettings.SETTING_CVS_TYPE),
                settings.get(AppSettings.SETTING_CVS_HOST),
                settings.get(AppSettings.SETTING_CVS_USER),
                settings.get(AppSettings.SETTING_CVS_PASSWORD)
        );

    }

    private void initCvsTreeTable() throws CvsAdapterException
    {
        new CvsWorker(cvsTreeTable, cvsAdapter, settings, getFilter()).execute();
    }

    private void initTrackerAdapter() throws TrackerAdapterException {

        trackerAdapter = TrackerAdapter.factory(
                settings.get(AppSettings.SETTING_TRACKER_TYPE),
                settings.get(AppSettings.SETTING_TRACKER_HOST),
                settings.get(AppSettings.SETTING_TRACKER_USER),
                settings.get(AppSettings.SETTING_TRACKER_PASSWORD)
        );
    }

    private void initTrackerTreeTable()
    {
        new TrackerWorker(trackerTreeTable, trackerAdapter, settings, getFilter()).execute();
    }

    private void initFilters()
    {
        initFilterByPeriod();

        FilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CvsWorker(cvsTreeTable, cvsAdapter, settings, getFilter()).execute();
                new TrackerWorker(trackerTreeTable, trackerAdapter, settings, getFilter()).execute();
            }
        });
    }

    private void initFilterByPeriod()
    {
        PeriodStartDatePicker.setDate(Calendar.getInstance().getTime());
        PeriodEndDatePicker.setDate(Calendar.getInstance().getTime());
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
