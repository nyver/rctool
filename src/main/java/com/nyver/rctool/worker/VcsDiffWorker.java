package com.nyver.rctool.worker;

import com.nyver.rctool.AppSettings;
import com.nyver.rctool.vcs.VcsAdapter;

import javax.swing.*;
import java.io.File;

/**
 * @author Yuri Novitsky
 */
public class VcsDiffWorker extends SwingWorker
{
    private String path;
    private String revision;
    private VcsAdapter vcsAdapter;
    private AppSettings settings;

    public VcsDiffWorker(String path, String revision, VcsAdapter vcsAdapter, AppSettings settings) {
        this.path = path;
        this.revision = revision;
        this.vcsAdapter = vcsAdapter;
        this.settings = settings;
    }

    @Override
    protected Object doInBackground() throws Exception {
        File file = vcsAdapter.checkoutFile(path, revision);
        File prevFile = vcsAdapter.checkoutFile(path, revision, true);

        System.out.println(file.getPath());
        System.out.println(prevFile.getPath());

        String command = String.format("\"%s\" \"%s\" \"%s\"", settings.get(AppSettings.SETTING_DIFFER), file.getPath(), prevFile.getPath());
        System.out.println(command);

        Runtime.getRuntime().exec(command);

        return null;
    }
}
