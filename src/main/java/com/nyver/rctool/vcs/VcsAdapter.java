package com.nyver.rctool.vcs;

import com.nyver.rctool.model.Filter;
import com.nyver.rctool.model.Revision;

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
}
