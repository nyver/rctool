package com.nyver.rctool.worker;

import com.nyver.rctool.AppSettings;
import com.nyver.rctool.csv.CvsAdapter;
import com.nyver.rctool.model.Filter;
import com.nyver.rctool.model.Revision;
import com.nyver.rctool.treetable.CvsTreeTableModel;
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
    private CvsAdapter adapter;
    private AppSettings settings;
    private Filter filter;

    public CvsWorker(JXTreeTable treeTable)
    {
        this.treeTable = treeTable;
    }

    public CvsWorker(JXTreeTable treeTable, CvsAdapter adapter, AppSettings settings, Filter filter)
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

        CvsTreeTableModel loadModel = new CvsTreeTableModel();
        loadModel.setColumns(new String[] {""});
        loadModel.add(new Revision(String.format("Fetching revisions from repository (%s)...", settings.get(AppSettings.SETTING_CVS_HOST))));
        treeTable.setTreeTableModel(loadModel);

        CvsTreeTableModel model = new CvsTreeTableModel(adapter.getRevisions(filter));
        treeTable.setTreeTableModel(model);
        treeTable.setEnabled(true);
        return null;
    }
}
