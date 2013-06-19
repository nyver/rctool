package com.nyver.rctool.listener;

import com.nyver.rctool.AppSettings;
import com.nyver.rctool.RCTool;
import com.nyver.rctool.model.RevisionChange;
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
public class VcsPropertiesMouseListener implements MouseListener
{
    private RCTool mainFrame;

    public VcsPropertiesMouseListener(RCTool mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int row = mainFrame.getVcsPropertiesTable().getSelectedRow();
            int rowRevision = mainFrame.getVcsTreeTable().getSelectedRow();
            if ((row >= 0) && (rowRevision >= 0)) {
                String path = (String) mainFrame.getVcsPropertiesTable().getValueAt(row, 1);
                String revision = (String) mainFrame.getVcsTreeTable().getValueAt(rowRevision, 0);
                if (!path.isEmpty() && !revision.isEmpty()) {
                    String action = (String) mainFrame.getVcsPropertiesTable().getValueAt(row, 2);
                    if (RevisionChange.ACTION_MODIFY.equals(action)) {
                        new VcsDiffWorker(path, revision, mainFrame).execute();
                    }
                }
            }
        }

        showPopup(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        showPopup(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        showPopup(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void showPopup(MouseEvent e)
    {
        int row = mainFrame.getVcsPropertiesTable().getSelectedRow();

        if (row >= 0) {
            mainFrame.getVcsPropertiesTable().setRowSelectionInterval(row, row);
        }

        if (e.isPopupTrigger()) {
            mainFrame.getVcsPropertiesTablePopupMenu().show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
