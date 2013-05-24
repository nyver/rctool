package com.nyver.rctool.model;

import com.nyver.rctool.adapter.SvnAdapter;
import org.tigris.subversion.svnclientadapter.ISVNInfo;
import org.tigris.subversion.svnclientadapter.ISVNLogMessage;
import org.tigris.subversion.svnclientadapter.SVNClientException;
import org.tigris.subversion.svnclientadapter.SVNRevision;

import java.net.MalformedURLException;
import java.text.ParseException;

/**
 * Revision List class
 *
 * @author Yuri Novitsky
 */
public class RevisionList {

    private SvnAdapter svnAdapter;
    private ISVNLogMessage[] revisions;

    public RevisionList(String host, String user, String password) throws SVNClientException, MalformedURLException {
        svnAdapter = new SvnAdapter(host, user, password);
    }

    public SvnAdapter getAdapter()
    {
        return svnAdapter;
    }

    public ISVNLogMessage[] getList() throws ParseException, SVNClientException
    {
        return getAdapter().getLatestRevisions();
    }
}
