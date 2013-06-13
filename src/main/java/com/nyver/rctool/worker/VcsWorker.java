package com.nyver.rctool.worker;

import com.nyver.rctool.AppSettings;
import com.nyver.rctool.vcs.VcsAdapter;
import com.nyver.rctool.model.Filter;
import com.nyver.rctool.model.Revision;
import com.nyver.rctool.treetable.VcsTreeTableModel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.*;
import java.util.List;

/**
 * Cvs Worker
 *
 * @author Yuri Novitsky
 */
public class VcsWorker extends SwingWorker
{

    private JXTreeTable treeTable;
    private VcsAdapter adapter;
    private AppSettings settings;
    private Filter filter;
    private VcsTreeTableModel model = new VcsTreeTableModel();

    public VcsWorker(JXTreeTable treeTable)
    {
        this.treeTable = treeTable;
    }

    public VcsWorker(JXTreeTable treeTable, VcsAdapter adapter, AppSettings settings, Filter filter)
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

        DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode(new Revision(String.format("Fetching revisions from repository (%s)...", settings.get(AppSettings.SETTING_CVS_HOST))), true);

        VcsTreeTableModel loadModel = new VcsTreeTableModel(root);
        loadModel.setColumns(new String[] {""});
        treeTable.setRootVisible(true);
        treeTable.setTreeTableModel(loadModel);

        List<Revision> revisions = adapter.getRevisions(filter);
        if (revisions.size() > 0) {
            for(Revision revision: revisions) {
                root.add(new DefaultMutableTreeTableNode(revision));
            }
        }

        model = new VcsTreeTableModel(root);
        return null;
    }

    @Override
    protected void done()
    {
        treeTable.setRootVisible(false);
        treeTable.setTreeTableModel(model);
        treeTable.setEnabled(true);
    }
}
