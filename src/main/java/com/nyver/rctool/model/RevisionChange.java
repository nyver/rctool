package com.nyver.rctool.model;

import java.io.File;

/**
 * Revision change class
 *
 * @author Yuri Novitsky
 */
public class RevisionChange
{
    private File file;
    private char action;

    public RevisionChange(String path, char action) {
        this.file = new File(path);
        this.action = action;
    }

    public File getFile() {
        return file;
    }

    public void setPath(String path) {
        this.file = new File(path);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public char getAction() {
        return action;
    }

    public void setAction(char action) {
        this.action = action;
    }
}
