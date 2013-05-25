package com.nyver.rctool.adapter;

import com.nyver.rctool.model.Revision;
import org.tigris.subversion.svnclientadapter.*;
import org.tigris.subversion.svnclientadapter.svnkit.SvnKitClientAdapterFactory;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * SVN Adapter class
 *
 * @author Yuri Novitsky
 */
public class SvnAdapter extends CvsAdapter
{

    private SVNUrl svnUrl;
    private ISVNInfo info;

    private ISVNClientAdapter svnClient;

    public SvnAdapter(String host, String user, String password) throws SVNClientException, MalformedURLException
    {
        super(host, user, password);
    }

    @Override
    public void setup() throws CvsAdapterException {
        try {
            SvnKitClientAdapterFactory.setup();
            svnClient = SVNClientAdapterFactory.createSVNClient(SvnKitClientAdapterFactory.SVNKIT_CLIENT);
            svnClient.setUsername(user);
            svnClient.setPassword(password);
            svnUrl = new SVNUrl(host);
        } catch (SVNClientException e) {
            e.printStackTrace();
            throw new CvsAdapterException(e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new CvsAdapterException(e.getMessage());
        }
    }

    public void setClient(ISVNClientAdapter client)
    {
        this.svnClient = client;
    }

    public ISVNClientAdapter getClient()
    {
        return svnClient;
    }

    public SVNRevision.Number getLastRevision() throws CvsAdapterException {
        try {
            return getInfo().getLastChangedRevision();
        } catch (SVNClientException e) {
            e.printStackTrace();
            throw new CvsAdapterException(e.getMessage());
        }
    }


    public ISVNInfo getInfo() throws SVNClientException
    {
        if (null == info) {
            info = getClient().getInfo(svnUrl);
        }
        return info;
    }

    @Override
    public ArrayList<Revision> getRevisions() throws CvsAdapterException
    {
        try {
            ArrayList<Revision> revisions = new ArrayList<Revision>();
            //ISVNLogMessage[] messages = getClient().getLogMessages(svnUrl, SVNRevision.getRevision("0"), SVNRevision.HEAD);
            ISVNLogMessage[] messages = getClient().getLogMessages(svnUrl, SVNRevision.getRevision(String.valueOf(getLastRevision().getNumber() - 10)), SVNRevision.HEAD);
            if (messages.length > 0) {
                for(int i=0; i < messages.length; i++) {
                    revisions.add(
                        new Revision(
                            String.valueOf(messages[i].getRevision().getNumber()),
                            messages[i].getDate(),
                            messages[i].getMessage(),
                            messages[i].getAuthor()
                        )
                    );
                }
            }
            return revisions;
        } catch (SVNClientException e) {
            e.printStackTrace();
            throw new CvsAdapterException(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new CvsAdapterException(e.getMessage());
        }
    }
}
