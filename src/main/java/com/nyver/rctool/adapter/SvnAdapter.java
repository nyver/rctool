package com.nyver.rctool.adapter;

import org.tigris.subversion.svnclientadapter.*;
import org.tigris.subversion.svnclientadapter.svnkit.SvnKitClientAdapterFactory;

import java.net.MalformedURLException;
import java.text.ParseException;

/**
 * SVN Adapter class
 *
 * @author Yuri Novitsky
 */
public class SvnAdapter {

    private String host;
    private String user;
    private String password;
    private SVNUrl svnUrl;
    private ISVNInfo info;

    private ISVNClientAdapter svnClient;

    public SvnAdapter(String host, String user, String password) throws SVNClientException, MalformedURLException {
        this.host = host;
        this.user = user;
        this.password = password;
        setup();
    }

    public void setup() throws SVNClientException, MalformedURLException
    {
        SvnKitClientAdapterFactory.setup();
        svnClient = SVNClientAdapterFactory.createSVNClient(SvnKitClientAdapterFactory.SVNKIT_CLIENT);
        svnClient.setUsername(user);
        svnClient.setPassword(password);
        svnUrl = new SVNUrl(host);
    }

    public ISVNClientAdapter getClient()
    {
        return svnClient;
    }

    public SVNRevision.Number getLastRevision() throws SVNClientException
    {
        return getInfo().getLastChangedRevision();
    }


    public ISVNInfo getInfo() throws SVNClientException
    {
        if (null == info) {
            info = getClient().getInfo(svnUrl);
        }
        return info;
    }

    public ISVNLogMessage[] getLatestRevisions() throws SVNClientException, ParseException {
        return getClient().getLogMessages(svnUrl, SVNRevision.getRevision(String.valueOf(getLastRevision().getNumber() - 10)), SVNRevision.HEAD);
    }

}
