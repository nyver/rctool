package com.nyver.rctool;

import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import com.nyver.rctool.listener.VcsPropertiesMouseListener;
import com.nyver.rctool.listener.CvsTreeSelectionListener;
import com.nyver.rctool.menu.VcsPropertiesTablePopupMenu;
import com.nyver.rctool.vcs.VcsAdapter;
import com.nyver.rctool.vcs.VcsAdapterException;
import com.nyver.rctool.model.Filter;
import com.nyver.rctool.tracker.TrackerAdapter;
import com.nyver.rctool.tracker.TrackerAdapterException;
import com.nyver.rctool.treetable.filter.JXTreeTableFilter;
import com.nyver.rctool.worker.VcsWorker;
import com.nyver.rctool.worker.TrackerWorker;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTreeTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * RCTool main class
 *
 * @author Yuri Novitsky
 */
public class RCTool extends JFrame
{
    private VcsAdapter vcsAdapter;
    private TrackerAdapter trackerAdapter;

    private JPanel MainPanel;
    private JSplitPane VerticalSplitPane;
    private JSplitPane HorizontalSplitPane;
    private JXTreeTable vcsTreeTable;
    private JXTreeTable trackerTreeTable;
    private JPanel filterByPeriodPanel;
    private JButton filterButton;
    private JPanel filterPanel;
    private JLabel filterPeriodStartLabel;
    private JLabel filterPeriodEndLabel;
    private JXDatePicker filterPeriodStartDatePicker;
    private JXDatePicker filterPeriodEndDatePicker;
    private JTable vcsPropertiesTable;
    private JSplitPane vcsSplitPane;
    private JPanel statusBar;
    private JLabel statusBarLabel;
    private JPanel filterByJQLPanel;
    private JTextArea filterJQLTextArea;

    private AppSettings settings = new AppSettings();

    private VcsPropertiesTablePopupMenu vcsPropertiesTablePopupMenu;

    public RCTool()
    {
        super("RCTool");

        try {
            settings.load();
            setContentPane(MainPanel);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent event) {
                    super.windowClosing(event);
                    try {
                        saveSettings();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            pack();
            initSettings();
            initFilters();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.OK_OPTION);
            System.exit(0);
        }

        initSwingBits();

        try {
            initVcsAdapter();
            initVcsTreeTable();
            initVcsPropertiesTable();
        } catch (VcsAdapterException e) {
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
        filterJQLTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setVisible(true);

    }

    public AppSettings getSettings() {
        return settings;
    }

    public VcsAdapter getVcsAdapter() {
        return vcsAdapter;
    }

    public JXTreeTable getVcsTreeTable() {
        return vcsTreeTable;
    }

    public JTable getVcsPropertiesTable() {
        return vcsPropertiesTable;
    }

    public JLabel getStatusBarLabel() {
        return statusBarLabel;
    }

    public VcsPropertiesTablePopupMenu getVcsPropertiesTablePopupMenu() {
        return vcsPropertiesTablePopupMenu;
    }

    public Filter getFilter()
    {
        return new Filter(filterPeriodStartDatePicker.getDate(), filterPeriodEndDatePicker.getDate(), filterJQLTextArea.getText());
    }

    private SimpleDateFormat getSettingDateFormat()
    {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    private void initSettings()
    {
        setSize(settings.getInt(AppSettings.SETTING_WINDOW_START_WIDTH), settings.getInt(AppSettings.SETTING_WINDOW_START_HEIGHT));
        VerticalSplitPane.setDividerLocation(settings.getInt(AppSettings.SETTING_VERTICAL_PANE_DIVIDER_LOCATION));
        HorizontalSplitPane.setDividerLocation(settings.getInt(AppSettings.SETTING_HORIZONTAL_PANE_DIVIDER_LOCATION));
        vcsSplitPane.setDividerLocation(settings.getInt(AppSettings.SETTING_CVS_PANE_DIVIDER_LOCATION));
        filterJQLTextArea.setText(settings.get(AppSettings.SETTING_FILTER_JQL));
    }

    private void saveSettings() throws IOException
    {
        Dimension dimension = getSize();
        settings.set(AppSettings.SETTING_WINDOW_START_WIDTH, dimension.getWidth());
        settings.set(AppSettings.SETTING_WINDOW_START_HEIGHT, dimension.getHeight());
        settings.set(AppSettings.SETTING_VERTICAL_PANE_DIVIDER_LOCATION, VerticalSplitPane.getDividerLocation());
        settings.set(AppSettings.SETTING_HORIZONTAL_PANE_DIVIDER_LOCATION, HorizontalSplitPane.getDividerLocation());
        settings.set(AppSettings.SETTING_CVS_PANE_DIVIDER_LOCATION, vcsSplitPane.getDividerLocation());

        SimpleDateFormat dateFormat = getSettingDateFormat();

        settings.set(AppSettings.SETTING_FILTER_DATE_FROM, dateFormat.format(filterPeriodStartDatePicker.getDate()));
        settings.set(AppSettings.SETTING_FILTER_DATE_TO, dateFormat.format(filterPeriodEndDatePicker.getDate()));
        settings.set(AppSettings.SETTING_FILTER_JQL, filterJQLTextArea.getText());

        settings.save();
    }

    private void initVcsAdapter() throws VcsAdapterException
    {
        vcsAdapter = VcsAdapter.factory(
                settings.get(AppSettings.SETTING_CVS_TYPE),
                settings.get(AppSettings.SETTING_CVS_HOST),
                settings.get(AppSettings.SETTING_CVS_USER),
                settings.get(AppSettings.SETTING_CVS_PASSWORD)
        );
    }

    private void initVcsTreeTable() throws VcsAdapterException
    {
        new VcsWorker(vcsTreeTable, vcsAdapter, settings, getFilter()).execute();

        vcsTreeTable.setColumnControlVisible(true);

        vcsTreeTable.addTreeSelectionListener(
            new CvsTreeSelectionListener(vcsTreeTable, vcsPropertiesTable)
        );
    }

    private void initVcsPropertiesTablePopupMenu()
    {
        vcsPropertiesTablePopupMenu = new VcsPropertiesTablePopupMenu(this);
    }

    private void initVcsPropertiesTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        vcsPropertiesTable.setModel(model);
        vcsPropertiesTable.addMouseListener(
            new VcsPropertiesMouseListener(this)
        );

        initVcsPropertiesTablePopupMenu();
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

        trackerTreeTable.setColumnControlVisible(true);

    }

    private void initFilters()
    {
        initFilterByPeriod();

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VcsWorker(vcsTreeTable, vcsAdapter, settings, getFilter()).execute();
                new TrackerWorker(trackerTreeTable, trackerAdapter, settings, getFilter()).execute();
            }
        });
    }

    private void initFilterByPeriod()
    {
        String filterDateFrom = settings.get(AppSettings.SETTING_FILTER_DATE_FROM);
        String filterDateTo = settings.get(AppSettings.SETTING_FILTER_DATE_TO);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = getSettingDateFormat();

        try {
            if (!filterDateFrom.isEmpty()) {
                calendar.setTime(dateFormat.parse(filterDateFrom));
                filterPeriodStartDatePicker.setDate(calendar.getTime());
            } else {
                filterPeriodStartDatePicker.setDate(Calendar.getInstance().getTime());
            }

            if (!filterDateFrom.isEmpty()) {
                calendar.setTime(dateFormat.parse(filterDateTo));
                filterPeriodEndDatePicker.setDate(calendar.getTime());
            } else {
                filterPeriodEndDatePicker.setDate(Calendar.getInstance().getTime());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initSwingBits()
    {
        TableRowFilterSupport.forFilter(new JXTreeTableFilter(vcsTreeTable)).searchable(true).apply();
        TableRowFilterSupport.forFilter(new JXTreeTableFilter(trackerTreeTable)).searchable(true).apply();
        TableRowFilterSupport.forTable(vcsPropertiesTable).searchable(true).apply();
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
