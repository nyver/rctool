package com.nyver.rctool.listener;

import com.nyver.rctool.AppSettings;
import com.nyver.rctool.vcs.VcsAdapter;
import com.nyver.rctool.worker.VcsDiffWorker;
import org.jdesktop.swingx.JXTreeTable;
import org.tigris.subversion.svnclientadapter.SVNClientException;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

/**
 * @author Yuri Novitsky
 */
public class CvsPropertiesMouseListener implements MouseListener
{
    private JTable vcsPropertiesTable;
    private JXTreeTable vcsTreeTable;
    private VcsAdapter vcsAdapter;
    private AppSettings settings;

    public CvsPropertiesMouseListener(JTable cvsPropertiesTable, JXTreeTable cvsTreeTable, VcsAdapter vcsAdapter, AppSettings settings) {
        this.vcsPropertiesTable = cvsPropertiesTable;
        this.vcsTreeTable = cvsTreeTable;
        this.vcsAdapter = vcsAdapter;
        this.settings = settings;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int row = vcsPropertiesTable.getSelectedRow();
            int rowRevision = vcsTreeTable.getSelectedRow();
            if ((row >= 0) && (rowRevision >= 0)) {
                String path = (String) vcsPropertiesTable.getValueAt(row, 1);
                String revision = (String) vcsTreeTable.getValueAt(rowRevision, 0);
                if (!path.isEmpty() && !revision.isEmpty()) {
                    new VcsDiffWorker(path, revision, vcsAdapter, settings).execute();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
