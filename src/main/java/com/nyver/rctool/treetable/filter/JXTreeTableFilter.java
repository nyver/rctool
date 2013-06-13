package com.nyver.rctool.treetable.filter;

import com.ezware.oxbow.swingbits.table.filter.AbstractTableFilter;
import com.ezware.oxbow.swingbits.table.filter.DistinctColumnItem;
import com.nyver.rctool.treetable.MyTreeTableModel;
import org.jdesktop.swingx.JXTreeTable;

import javax.swing.table.TableModel;
import java.util.Collection;

public class JXTreeTableFilter extends AbstractTableFilter<JXTreeTable> {

    private static final long serialVersionUID = 1L;

    public JXTreeTableFilter(JXTreeTable table) {
        super(table);
    }

    @Override
    protected boolean execute(int i, Collection<DistinctColumnItem> distinctColumnItems) {
        MyTreeTableModel model = (MyTreeTableModel) getTable().getTreeTableModel();
        model.setDistinctColumnItems(i, distinctColumnItems);

        getTable().updateUI();

        return true;
    }


    @Override
    public void modelChanged(TableModel tableModel) {

    }
}
