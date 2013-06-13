package com.nyver.rctool.treetable;

import com.ezware.oxbow.swingbits.table.filter.DistinctColumnItem;
import com.nyver.rctool.model.Revision;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 * VcsTreeTableModel
 *
 * @author Yuri Novitsky
 */
public class VcsTreeTableModel extends MyTreeTableModel<Revision>
{
    protected String[] columnNames = {"Revision", "Date", "Comment", "Author"};

    public VcsTreeTableModel()
    {
        super(null);
    }

    public VcsTreeTableModel(TreeTableNode root)
    {
        super(root);
        setColumns(columnNames);
    }

    @Override
    public Object getValueAt(Object o, int i)
    {
        if (o instanceof MutableTreeTableNode) {
            MutableTreeTableNode node = (MutableTreeTableNode) o;
            Revision revision = (Revision) node.getUserObject();
            switch(i) {
                case 0:
                    return revision.getRevision();
                case 1:
                    return revision.getDate();
                case 2:
                    return revision.getComment();
                case 3:
                    return revision.getAuthor();
            }
        }
        return null;
    }

    @Override
    protected Revision getRootUserData() {
        return new Revision("root");
    }
}
