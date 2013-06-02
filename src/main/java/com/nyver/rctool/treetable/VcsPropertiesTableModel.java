package com.nyver.rctool.treetable;

import com.nyver.rctool.model.RevisionChange;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * VcsPropertiesTableModel class
 *
 * @author Yuri Novitsky
 */
public class VcsPropertiesTableModel extends AbstractTableModel
{

    private List<RevisionChange> changes;
    private String[] columns = {"Name", "Path", "Action"};

    public VcsPropertiesTableModel(List<RevisionChange> changes) {
        this.changes = changes;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public int getRowCount() {
        return changes.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RevisionChange change = changes.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return change.getFile().getName();
            case 1:
                return change.getFile().getPath();
            case 2:
                return change.getAction();
        }
        return null;
    }
}
