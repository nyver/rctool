package com.nyver.rctool.treetable;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

/**
 * CvsTreeTableModel
 *
 * @author Yuri Novitsky
 */
public class CvsTreeTableModel extends AbstractTreeTableModel {

    @Override
    public Object getRoot() {
        return "root";
    }

    @Override
    public int getColumnCount() {
        return 2;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return String.class;
    }

    @Override
    public String getColumnName(int column) {
        return "asd";
    }

    @Override
    public Object getValueAt(Object o, int i) {
        return "value" + i;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent.equals("root")) {
            return "child";
        }

        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        return 2;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }
}
