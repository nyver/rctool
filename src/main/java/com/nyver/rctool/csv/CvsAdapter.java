package com.nyver.rctool.csv;

import com.nyver.rctool.model.Filter;
import com.nyver.rctool.model.Revision;

import java.util.ArrayList;
import java.util.Date;

/**
 * Cvs Adapter abstract class
 *
 * @author Yuri Novitsky
 */
public abstract class CvsAdapter
{

    public static String TYPE_SVN = "svn";

    protected String host;
    protected String user;
    protected String password;

    protected CvsAdapter(String host, String user, String password)
    {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public abstract void setup() throws CvsAdapterException;

    public abstract ArrayList<Revision> getRevisions(Filter filter) throws CvsAdapterException;

    public static CvsAdapter factory(String type, String host, String user, String password) throws CvsAdapterException
    {
        CvsAdapter adapter;
        try {
            adapter = new SvnAdapter(host, user, password);
            adapter.setup();
            return adapter;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CvsAdapterException(e.getMessage());
        }
    }
}
