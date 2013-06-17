package com.nyver.rctool.worker;

import com.nyver.rctool.AppSettings;
import com.nyver.rctool.RCTool;
import com.nyver.rctool.vcs.VcsAdapter;
import com.nyver.rctool.vcs.VcsAdapterException;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * @author Yuri Novitsky
 */
public class VcsDiffWorker extends SwingWorker<Object, String>
{
    private String path;
    private String revision;
    private RCTool mainFrame;

    public VcsDiffWorker(String path, String revision, RCTool mainFrame) {
        this.path = path;
        this.revision = revision;
        this.mainFrame = mainFrame;
    }

    @Override
    protected Object doInBackground() throws Exception {
        publish(String.format("Fetching current revision from %s%s ...", mainFrame.getSettings().get(AppSettings.SETTING_CVS_HOST), path));
        try {
            File currentFile = mainFrame.getVcsAdapter().checkoutFile(path, revision);
            if (null != currentFile && currentFile.exists()) {
                publish(String.format("Fetching previous revision from %s%s ...", mainFrame.getSettings().get(AppSettings.SETTING_CVS_HOST), path));
                File previousFile = mainFrame.getVcsAdapter().checkoutFile(path, revision, true);
                if (null != previousFile && previousFile.exists()) {

                    String command = String.format("\"%s\" \"%s\" \"%s\"", mainFrame.getSettings().get(AppSettings.SETTING_DIFFER), previousFile.getPath(), currentFile.getPath());

                    publish(String.format("Executing %s ...", command));
                    Runtime.getRuntime().exec(command);
                    publish("Done");
                }
            }
        } catch(VcsAdapterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, e.getMessage(), "Error", JOptionPane.OK_OPTION);
        }

        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        for(String chunk: chunks) {
            mainFrame.getStatusBarLabel().setText(chunk);
        }
    }
}
