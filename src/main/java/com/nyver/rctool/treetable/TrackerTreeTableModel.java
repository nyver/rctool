package com.nyver.rctool.treetable;

import com.nyver.rctool.model.Issue;
import com.nyver.rctool.model.Revision;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

import java.util.ArrayList;

/**
 * Description
 *
 * @author Yuri Novitsky
 */
public class TrackerTreeTableModel extends MyTreeTableModel<Issue>
{
    private String[] columnNames = {"Key", "Date", "Summary"};

    public TrackerTreeTableModel()
    {
        super(null);
    }

    public TrackerTreeTableModel(TreeTableNode root)
    {
        super(root);
        setColumns(columnNames);
    }


    @Override
    public Object getValueAt(Object o, int i)
    {

        if (o instanceof MutableTreeTableNode) {
            MutableTreeTableNode node = (MutableTreeTableNode) o;
            Issue issue = (Issue) node.getUserObject();
            switch(i) {
                case 0: return issue.getKey();
                case 1: return issue.getDate();
                case 2: return issue.getSummary();
            }
        }

        return null;
    }

    @Override
    protected Issue getRootUserData() {
        return new Issue("root");
    }

}
