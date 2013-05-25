package com.nyver.rctool.worker;

import com.nyver.rctool.AppSettings;
import com.nyver.rctool.csv.CvsAdapter;
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
    private AppSettings settings;

    public CvsWorker(JXTreeTable treeTable) {
        this.treeTable = treeTable;
    }

    public CvsWorker(JXTreeTable treeTable, AppSettings settings) {
        this.treeTable = treeTable;
        this.settings = settings;
    }

    @Override
    protected Object doInBackground() throws Exception {
        treeTable.setEnabled(false);

        CvsTreeTableModel loadModel = new CvsTreeTableModel();
        loadModel.setColumns(new String[] {""});
        loadModel.add(new Revision(String.format("Fetching revisions from repository (%s)...", settings.get(AppSettings.SETTING_CVS_HOST))));
        treeTable.setTreeTableModel(loadModel);

        CvsAdapter cvsAdapter = CvsAdapter.factory(
                settings.get(AppSettings.SETTING_CVS_TYPE),
                settings.get(AppSettings.SETTING_CVS_HOST),
                settings.get(AppSettings.SETTING_CVS_USER),
                settings.get(AppSettings.SETTING_CVS_PASSWORD)
        );

        CvsTreeTableModel model = new CvsTreeTableModel(cvsAdapter.getRevisions());
        treeTable.setTreeTableModel(model);
        treeTable.setEnabled(true);
        return null;
    }
}
