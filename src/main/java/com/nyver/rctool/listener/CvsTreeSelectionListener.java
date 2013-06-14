package com.nyver.rctool.listener;

import com.nyver.rctool.model.Revision;
import com.nyver.rctool.treetable.VcsPropertiesTableModel;
import com.nyver.rctool.treetable.VcsTreeTableModel;
import org.jdesktop.swingx.JXTreeTable;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 * @author Yuri Novitsky
 */
public class CvsTreeSelectionListener implements TreeSelectionListener
{
    private JXTreeTable cvsTreeTable;
    private JTable cvsPropertiesTable;

    public CvsTreeSelectionListener(JXTreeTable cvsTreeTable, JTable cvsPropertiesTable) {
        this.cvsTreeTable = cvsTreeTable;
        this.cvsPropertiesTable = cvsPropertiesTable;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        VcsTreeTableModel model = (VcsTreeTableModel) cvsTreeTable.getTreeTableModel();
        Revision revision = model.getItem(cvsTreeTable.convertRowIndexToModel(cvsTreeTable.getSelectedRow()));
        VcsPropertiesTableModel cvsPropertiesModel = new VcsPropertiesTableModel(revision.getChanges());
        cvsPropertiesTable.setModel(cvsPropertiesModel);
        cvsPropertiesTable.removeAll();
    }
}
