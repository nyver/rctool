package com.nyver.rctool.model;

import com.nyver.rctool.adapter.CvsAdapter;
import com.nyver.rctool.adapter.CvsAdapterException;
import com.nyver.rctool.adapter.SvnAdapter;
import org.tigris.subversion.svnclientadapter.ISVNInfo;
import org.tigris.subversion.svnclientadapter.ISVNLogMessage;
import org.tigris.subversion.svnclientadapter.SVNClientException;
import org.tigris.subversion.svnclientadapter.SVNRevision;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Revision List class
 *
 * @author Yuri Novitsky
 */
public class RevisionList {

    private CvsAdapter cvsAdapter;
    private Revision[] revisions;

    public RevisionList(CvsAdapter adapter)
    {
        cvsAdapter = adapter;
    }

    public CvsAdapter getAdapter()
    {
        return cvsAdapter;
    }

    public ArrayList<Revision> getList() throws CvsAdapterException {
        return getAdapter().getRevisions();
    }
}
