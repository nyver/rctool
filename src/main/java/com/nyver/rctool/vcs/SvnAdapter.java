package com.nyver.rctool.vcs;

import com.nyver.rctool.model.Filter;
import com.nyver.rctool.model.Revision;
import com.nyver.rctool.model.RevisionChange;
import org.apache.commons.io.IOUtils;
import org.jdesktop.swingx.treetable.TreeTableNode;
import org.tigris.subversion.svnclientadapter.*;
import org.tigris.subversion.svnclientadapter.svnkit.SvnKitClientAdapterFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * SVN Adapter class
 *
 * @author Yuri Novitsky
 */
public class SvnAdapter extends VcsAdapter
{

    public static String TYPE = "svn";

    private static int REVISIONS_PER_REQUEST = 100;

    private SVNUrl svnUrl;
    private ISVNInfo info;

    private ISVNClientAdapter svnClient;

    private static boolean isSet = false;

    public SvnAdapter(String host, String user, String password) throws SVNClientException, MalformedURLException
    {
        super(host, user, password);
    }

    @Override
    public void setup() throws VcsAdapterException
    {
        try {
            if (!isSet) {
                SvnKitClientAdapterFactory.setup();
                isSet = true;
            }
            svnClient = SVNClientAdapterFactory.createSVNClient(SvnKitClientAdapterFactory.SVNKIT_CLIENT);
            svnClient.setUsername(user);
            svnClient.setPassword(password);
            svnUrl = new SVNUrl(host);
        } catch (SVNClientException e) {
            e.printStackTrace();
            throw new VcsAdapterException(e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new VcsAdapterException(e.getMessage());
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

    public SVNRevision.Number getLastRevision() throws VcsAdapterException {
        try {
            return getInfo().getLastChangedRevision();
        } catch (SVNClientException e) {
            e.printStackTrace();
            throw new VcsAdapterException(e.getMessage());
        }
    }


    public ISVNInfo getInfo() throws SVNClientException
    {
        if (null == info) {
            info = getClient().getInfo(svnUrl);
        }
        return info;
    }

    public List<RevisionChange> getChanges(ISVNLogMessage message)
    {
        List<RevisionChange> changes = new ArrayList<RevisionChange>();
        ISVNLogMessageChangePath[] changePaths = message.getChangedPaths();
        if (changePaths.length > 0) {
            for(ISVNLogMessageChangePath path: changePaths) {
                changes.add(new RevisionChange(path.getPath(), path.getAction()));
            }
        }
        return changes;
    }

    @Override
    public List<Revision> getRevisions(Filter filter) throws VcsAdapterException
    {
        try {
            ArrayList<Revision> revisions = new ArrayList<Revision>();

            long startRevisionNumber = getLastRevision().getNumber() - REVISIONS_PER_REQUEST;
            long endRevisionNumber = 0;

            ISVNLogMessage[] messages = getClient().getLogMessages(svnUrl, SVNRevision.getRevision(String.valueOf(startRevisionNumber)), SVNRevision.HEAD);

            boolean isOutOfLimit = false;

            while(messages.length > 0) {
                if (messages.length > 0) {
                    for(int i=0; i < messages.length; i++) {
                        ISVNLogMessage message = messages[i];
                        if (message.getDate().equals(filter.getStartDate())
                                || message.getDate().equals(filter.getEndDate())
                                || (message.getDate().after(filter.getStartDate()) && message.getDate().before(filter.getEndDate()))) {

                            revisions.add(
                                    new Revision(
                                            String.valueOf(message.getRevision().getNumber()),
                                            message.getDate(),
                                            message.getMessage(),
                                            message.getAuthor(),
                                            getChanges(message)
                                    )
                            );
                        }

                        if (message.getDate().before(filter.getStartDate())) {
                            isOutOfLimit = true;
                        }
                    }
                }

                if (isOutOfLimit) {
                    break;
                }

                endRevisionNumber = startRevisionNumber;
                startRevisionNumber = endRevisionNumber - REVISIONS_PER_REQUEST;

                messages = getClient().getLogMessages(svnUrl, SVNRevision.getRevision(String.valueOf(startRevisionNumber)), SVNRevision.HEAD);
            }

            return revisions;
        } catch (SVNClientException e) {
            e.printStackTrace();
            throw new VcsAdapterException(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new VcsAdapterException(e.getMessage());
        }
    }

    protected String getTempDir()
    {
        return System.getProperty("java.io.tmpdir");
    }

    protected String getCheckoutFileName(String path, String revision)
    {
        return new File(path).getName() + "." + revision;
    }

    @Override
    public File checkoutFile(String path, String revision, boolean previousRevision) throws VcsAdapterException {

        String tempDir = getTempDir();

        if (null != path && !path.isEmpty()
                && null != revision && !revision.isEmpty()
                && null != tempDir && !tempDir.isEmpty()) {

            if (previousRevision) {
                revision = String.valueOf(Integer.parseInt(revision) - 1);
            }

            String url = getHost() + path.replace("\\", "/");
            String filePath = tempDir + getCheckoutFileName(path, revision);

            File file = new File(filePath);

            try {
                InputStream inputStream;
                if (previousRevision) {
                    inputStream = getClient().getContent(
                            new SVNUrl(url),
                            SVNRevision.getRevision(revision)
                    );
                } else {
                    inputStream = getClient().getContent(new SVNUrl(url), SVNRevision.getRevision(revision));
                }
                OutputStream outputStream = new FileOutputStream(file, false);

                IOUtils.copy(inputStream, outputStream);

                outputStream.close();
                inputStream.close();

                return file;
            } catch (Exception e) {
                e.printStackTrace();
                throw new VcsAdapterException(e.getMessage());
            }
        }

        return null;
    }

}
