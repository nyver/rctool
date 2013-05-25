package com.nyver.rctool.worker;

import com.nyver.rctool.AppSettings;
import com.nyver.rctool.model.Issue;
import com.nyver.rctool.tracker.TrackerAdapter;
import com.nyver.rctool.treetable.TrackerTreeTableModel;
import org.jdesktop.swingx.JXTreeTable;

import javax.swing.*;

/**
 * TraclerWorker
 *
 * @author Yuri Novitsky
 */
public class TrackerWorker extends SwingWorker
{

    private JXTreeTable treeTable;
    private AppSettings settings;

    public TrackerWorker(JXTreeTable treeTable) {
        this.treeTable = treeTable;
    }

    public TrackerWorker(JXTreeTable treeTable, AppSettings settings) {
        this.treeTable = treeTable;
        this.settings = settings;
    }

    @Override
    protected Object doInBackground() throws Exception {
        treeTable.setEnabled(false);

        TrackerTreeTableModel loadModel = new TrackerTreeTableModel();
        loadModel.setColumns(new String[] {""});
        loadModel.add(new Issue(String.format("Fetching issues from tracker (%s)...", settings.get(AppSettings.SETTING_TRACKER_HOST))));
        treeTable.setTreeTableModel(loadModel);

        TrackerAdapter trackerAdapter = TrackerAdapter.factory(
                settings.get(AppSettings.SETTING_TRACKER_TYPE),
                settings.get(AppSettings.SETTING_TRACKER_HOST),
                settings.get(AppSettings.SETTING_TRACKER_USER),
                settings.get(AppSettings.SETTING_TRACKER_PASSWORD)
        );

        TrackerTreeTableModel model = new TrackerTreeTableModel(trackerAdapter.getIssues());
        treeTable.setTreeTableModel(model);
        treeTable.setEnabled(true);
        return null;
    }
}
