package com.nyver.rctool.adapter;

import com.nyver.rctool.model.Revision;
import org.tigris.subversion.svnclientadapter.ISVNLogMessage;

import java.util.ArrayList;

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

    public abstract ArrayList<Revision> getRevisions() throws CvsAdapterException;

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
