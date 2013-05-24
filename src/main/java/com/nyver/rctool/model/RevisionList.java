package com.nyver.rctool.model;

import com.nyver.rctool.adapter.SvnAdapter;
import org.tigris.subversion.svnclientadapter.ISVNInfo;
import org.tigris.subversion.svnclientadapter.SVNClientException;

import java.net.MalformedURLException;

/**
 * Revision List class
 *
 * @author Yuri Novitsky
 */
public class RevisionList {

    private SvnAdapter svnAdapter;

    public RevisionList(String host, String user, String password) throws SVNClientException {
        svnAdapter = new SvnAdapter(host, user, password);
    }

    public void getList() throws MalformedURLException, SVNClientException {
        ISVNInfo info = svnAdapter.getInfo();
    }
}
