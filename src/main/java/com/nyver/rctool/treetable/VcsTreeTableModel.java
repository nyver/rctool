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
public class VcsTreeTableModel extends MyTreeTableModel
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

    public Revision getRevision(int row)
    {
        MutableTreeTableNode node = (MutableTreeTableNode) getChild(root, row);
        if (null != node) {
            return (Revision) node.getUserObject();
        }
        return null;
    }

    @Override
    protected Object filterRoot(Object root)
    {
        DefaultMutableTreeTableNode revisions = new DefaultMutableTreeTableNode(new Revision("root"));
        if (null != distinctColumnItems && distinctColumnItems.size() > 0) {
            DefaultMutableTreeTableNode rootNode = (DefaultMutableTreeTableNode) root;
            for(int count = 0; count < rootNode.getChildCount(); count++) {
                DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) rootNode.getChildAt(count);
                for(int i = 0; i < getColumnCount(); i++) {
                    if (distinctColumnItems.containsKey(i)) {
                        if (distinctColumnItems.get(i).contains(new DistinctColumnItem(getValueAt(node, i), 0))) {
                            revisions.add(new DefaultMutableTreeTableNode(node.getUserObject()));
                            break;
                        }
                    }
                }
            }
        } else {
            revisions = (DefaultMutableTreeTableNode) root;
        }

        return revisions;
    }

}
