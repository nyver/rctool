package com.nyver.rctool.adapter;

import org.tigris.subversion.svnclientadapter.*;
import org.tigris.subversion.svnclientadapter.javahl.JhlClientAdapterFactory;
import org.apache.subversion.javahl.ClientException;

import java.net.MalformedURLException;

/**
 * SVN Adapter class
 *
 * @author Yuri Novitsky
 */
public class SvnAdapter {

    private String host;
    private String user;
    private String password;

    private ISVNClientAdapter svnClient;

    public SvnAdapter(String host, String user, String password) throws SVNClientException {
        this.host = host;
        this.user = user;
        this.password = password;
        setup();
    }

    public void setup() throws SVNClientException
    {
        JhlClientAdapterFactory.setup();
        svnClient = SVNClientAdapterFactory.createSVNClient(JhlClientAdapterFactory.JAVAHL_CLIENT);
        svnClient.setUsername(user);
        svnClient.setPassword(password);
    }

    public ISVNClientAdapter getClient()
    {
        return svnClient;
    }

    public ISVNInfo getInfo() throws MalformedURLException, SVNClientException {
        return svnClient.getInfo(new SVNUrl(host));
    }

}
