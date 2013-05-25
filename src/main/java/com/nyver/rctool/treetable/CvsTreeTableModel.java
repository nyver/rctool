package com.nyver.rctool.treetable;

import com.nyver.rctool.model.Revision;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.tigris.subversion.svnclientadapter.ISVNLogMessage;

import java.util.ArrayList;

/**
 * CvsTreeTableModel
 *
 * @author Yuri Novitsky
 */
public class CvsTreeTableModel extends AbstractTreeTableModel
{

    private String[] columns = {"Revision", "Date", "Comment", "Author"};
    protected ArrayList<Revision> root = new ArrayList<Revision>();

    public CvsTreeTableModel()
    {
        super(null);
    }

    public CvsTreeTableModel(ArrayList<Revision> root)
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

        if (o instanceof Revision) {
            Revision revision = (Revision) o;
            switch(i) {
                case 0: return revision.getRevision();
                case 1: return revision.getDate();
                case 2: return revision.getComment();
                case 3: return revision.getAuthor();
            }
        }

        return null;
    }

    @Override
    public Object getChild(Object parent, int index)
    {
        ArrayList<Revision> revisions = (ArrayList<Revision>) parent;
        return revisions.get(index);
    }

    @Override
    public int getChildCount(Object parent)
    {
        if (parent instanceof ArrayList) {
            ArrayList<Revision> revisions = (ArrayList<Revision>) parent;
            return revisions.size();
        }

        return 0;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child)
    {
        if (parent instanceof ArrayList) {

            ArrayList<Revision> revisions = (ArrayList<Revision>) parent;
            Revision revision = (Revision) child;

            for(int i=0; i <= revisions.size(); i++) {
                if (revisions.get(i) == revision) {
                    return i;
                }
            }
        }
        return 0;
    }

}
