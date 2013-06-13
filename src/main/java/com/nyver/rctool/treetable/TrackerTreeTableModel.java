package com.nyver.rctool.treetable;

import com.nyver.rctool.model.Issue;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import java.util.ArrayList;

/**
 * Description
 *
 * @author Yuri Novitsky
 */
public class TrackerTreeTableModel extends AbstractTreeTableModel
{
    private String[] columns = {"Key", "Date", "Summary"};
    protected ArrayList<Issue> root = new ArrayList<Issue>();

    public TrackerTreeTableModel()
    {
        super(null);
    }

    public TrackerTreeTableModel(ArrayList<Issue> root)
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

        if (o instanceof Issue) {
            Issue issue = (Issue) o;
            switch(i) {
                case 0: return issue.getKey();
                case 1: return issue.getDate();
                case 2: return issue.getSummary();
            }
        }

        return null;
    }

    @Override
    public Object getChild(Object parent, int index)
    {
        ArrayList<Issue> issues = (ArrayList<Issue>) parent;
        return issues.get(index);
    }

    @Override
    public int getChildCount(Object parent)
    {
        if (parent instanceof ArrayList) {
            ArrayList<Issue> issues = (ArrayList<Issue>) parent;
            return issues.size();
        }

        return 0;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child)
    {
        if (parent instanceof ArrayList) {

            ArrayList<Issue> issues = (ArrayList<Issue>) parent;
            Issue issue = (Issue) child;

            for(int i=0; i < issues.size(); i++) {
                if (issues.get(i) == issue) {
                    return i;
                }
            }
        }
        return 0;
    }

    public void add(Issue issue)
    {
        root.add(issue);
    }

    public void setColumns(String[] columns)
    {
        this.columns = columns;
    }


}
