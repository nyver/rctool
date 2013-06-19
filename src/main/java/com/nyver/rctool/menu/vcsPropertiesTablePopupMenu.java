package com.nyver.rctool.menu;

import com.nyver.rctool.RCTool;
import com.nyver.rctool.listener.VcsPropertiesMouseListener;
import com.nyver.rctool.model.RevisionChange;
import com.nyver.rctool.worker.VcsDiffWorker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 * @author Yuri Novitsky
 */
public class VcsPropertiesTablePopupMenu extends JPopupMenu
{
    public static String CMD_DIFF = "Diff with previous revision";
    private RCTool mainFrame;

    public VcsPropertiesTablePopupMenu(final RCTool mainFrame) {
        super();

        this.mainFrame = mainFrame;

        JMenuItem menuItem = new JMenuItem(CMD_DIFF);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = mainFrame.getVcsPropertiesTable().getSelectedRow();
                int rowRevision = mainFrame.getVcsTreeTable().getSelectedRow();
                if ((row >= 0) && (rowRevision >= 0)) {
                    String path = (String) mainFrame.getVcsPropertiesTable().getValueAt(row, 1);
                    String revision = (String) mainFrame.getVcsTreeTable().getValueAt(rowRevision, 0);
                    if (!path.isEmpty() && !revision.isEmpty()) {
                        Character action = (Character) mainFrame.getVcsPropertiesTable().getValueAt(row, 2);
                        if (RevisionChange.ACTION_MODIFY.equals(action)) {
                            new VcsDiffWorker(path, revision, mainFrame).execute();
                        }
                    }
                }
            }
        });
        add(menuItem);
    }

    public VcsPropertiesTablePopupMenu(String label) {
        super(label);
    }


}
