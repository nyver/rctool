package com.nyver.rctool.worker;

import com.nyver.rctool.AppSettings;
import com.nyver.rctool.csv.VcsAdapter;
import com.nyver.rctool.model.Filter;
import com.nyver.rctool.model.Revision;
import com.nyver.rctool.treetable.VcsTreeTableModel;
import org.jdesktop.swingx.JXTreeTable;

import javax.swing.*;

/**
 * Cvs Worker
 *
 * @author Yuri Novitsky
 */
public class CvsWorker extends SwingWorker
{

    private JXTreeTable treeTable;
    private VcsAdapter adapter;
    private AppSettings settings;
    private Filter filter;
    private VcsTreeTableModel model = new VcsTreeTableModel();

    public CvsWorker(JXTreeTable treeTable)
    {
        this.treeTable = treeTable;
    }

    public CvsWorker(JXTreeTable treeTable, VcsAdapter adapter, AppSettings settings, Filter filter)
    {
        this.treeTable = treeTable;
        this.adapter = adapter;
        this.settings = settings;
        this.filter = filter;
    }

    @Override
    protected Object doInBackground() throws Exception
    {
        treeTable.setEnabled(false);

        VcsTreeTableModel loadModel = new VcsTreeTableModel();
        loadModel.setColumns(new String[] {""});
        loadModel.add(new Revision(String.format("Fetching revisions from repository (%s)...", settings.get(AppSettings.SETTING_CVS_HOST))));
        treeTable.setTreeTableModel(loadModel);

        model = new VcsTreeTableModel(adapter.getRevisions(filter));
        return null;
    }

    @Override
    protected void done() {
        treeTable.setTreeTableModel(model);
        treeTable.setEnabled(true);
    }
}
