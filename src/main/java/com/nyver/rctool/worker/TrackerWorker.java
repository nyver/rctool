package com.nyver.rctool.worker;

import com.nyver.rctool.AppSettings;
import com.nyver.rctool.model.Filter;
import com.nyver.rctool.model.Issue;
import com.nyver.rctool.tracker.TrackerAdapter;
import com.nyver.rctool.treetable.TrackerTreeTableModel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import javax.swing.*;
import java.util.List;

/**
 * TrackerWorker
 *
 * @author Yuri Novitsky
 */
public class TrackerWorker extends SwingWorker
{

    private JXTreeTable treeTable;
    private TrackerAdapter adapter;
    private AppSettings settings;
    private Filter filter;
    private TrackerTreeTableModel model = new TrackerTreeTableModel();

    public TrackerWorker(JXTreeTable treeTable) {
        this.treeTable = treeTable;
    }

    public TrackerWorker(JXTreeTable treeTable, TrackerAdapter adapter, AppSettings settings, Filter filter) {
        this.treeTable = treeTable;
        this.adapter = adapter;
        this.settings = settings;
        this.filter = filter;
    }

    @Override
    protected Object doInBackground() throws Exception {
        treeTable.setEnabled(false);

        DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode(new Issue(String.format("Fetching issues from tracker (%s)...", settings.get(AppSettings.SETTING_TRACKER_HOST))));

        TrackerTreeTableModel loadModel = new TrackerTreeTableModel(root);
        loadModel.setColumns(new String[]{""});
        treeTable.setTreeTableModel(loadModel);
        treeTable.setRootVisible(true);

        List<Issue> issues = adapter.getIssues(filter);
        if (issues.size() > 0) {
            for(Issue issue: issues) {
                root.add(new DefaultMutableTreeTableNode(issue));
            }
        }

        model = new TrackerTreeTableModel(root);
        return null;
    }

    @Override
    protected void done() {
        treeTable.setRootVisible(false);
        treeTable.setTreeTableModel(model);
        treeTable.setEnabled(true);
    }
}
