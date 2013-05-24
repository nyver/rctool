package com.nyver.rctool.treetable;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.tigris.subversion.svnclientadapter.ISVNLogMessage;

/**
 * CvsTreeTableModel
 *
 * @author Yuri Novitsky
 */
public class CvsTreeTableModel extends AbstractTreeTableModel
{

    private String[] columns = {"Revision", "Comment"};
    protected ISVNLogMessage[] root;

    public CvsTreeTableModel(ISVNLogMessage[] root)
    {
        super(root);
        this.root = root;
    }

    @Override
    public Object getRoot()
    {
        return root;
    }

    @Override
    public int getColumnCount()
    {
        return columns.length;
    }

    @Override
    public Class<?> getColumnClass(int column)
    {
        return String.class;
    }

    @Override
    public String getColumnName(int column)
    {
        return columns[column];
    }

    @Override
    public Object getValueAt(Object o, int i)
    {

        if (o instanceof ISVNLogMessage) {
            ISVNLogMessage revision = (ISVNLogMessage) o;
            switch(i) {
                case 0: return revision.getRevision().getNumber();
                case 1: return revision.getMessage();
            }
        }

        return null;
    }

    @Override
    public Object getChild(Object parent, int index)
    {
        ISVNLogMessage[] revisions = (ISVNLogMessage[]) parent;
        return revisions[index];
    }

    @Override
    public int getChildCount(Object parent)
    {
        if (parent instanceof ISVNLogMessage[]) {
            ISVNLogMessage[] revisions = (ISVNLogMessage[]) parent;
            return revisions.length;
        }

        return 0;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child)
    {
        if (parent instanceof ISVNLogMessage[]) {
            ISVNLogMessage[] revisions = (ISVNLogMessage[]) parent;
            ISVNLogMessage revision = (ISVNLogMessage) child;
            for(int i=0; i <= revisions.length; i++) {
                if (revisions[i] == revision) {
                    return i;
                }
            }
        }
        return 0;
    }

}
