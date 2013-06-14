package com.nyver.rctool.vcs;

import com.nyver.rctool.model.Filter;
import com.nyver.rctool.model.Revision;
import org.tigris.subversion.svnclientadapter.SVNClientException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

/**
 * Cvs Adapter abstract class
 *
 * @author Yuri Novitsky
 */
public abstract class VcsAdapter
{

    public static String TYPE_SVN = "svn";

    protected String host;
    protected String user;
    protected String password;

    protected VcsAdapter(String host, String user, String password)
    {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void setup() throws VcsAdapterException;

    public abstract List<Revision> getRevisions(Filter filter) throws VcsAdapterException;

    public static VcsAdapter factory(String type, String host, String user, String password) throws VcsAdapterException
    {
        VcsAdapter adapter;
        try {
            adapter = new SvnAdapter(host, user, password);
            adapter.setup();
            return adapter;
        } catch (Exception e) {
            e.printStackTrace();
            throw new VcsAdapterException(e.getMessage());
        }
    }

    public abstract File checkoutFile(String path, String revision, boolean previousRevision) throws VcsAdapterException;

    public File checkoutFile(String path, String revision) throws VcsAdapterException
    {
        return checkoutFile(path, revision, false);
    }

}
