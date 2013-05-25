package com.nyver.rctool.tracker;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;
import com.nyver.rctool.model.Issue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Jira Adapter
 *
 * @author Yuri Novitsky
 */
public class JiraAdapter extends TrackerAdapter
{
    public static String TYPE = "jira";

    JiraRestClient client;

    public JiraAdapter(String host, String user, String password)
    {
        super(host, user, password);
    }

    public void setClient(JiraRestClient client)
    {
        this.client = client;
    }

    public JiraRestClient getClient()
    {
        return client;
    }

    public void setup() throws TrackerAdapterException
    {
        final JerseyJiraRestClientFactory factory = new JerseyJiraRestClientFactory();
        try {
            client =  factory.createWithBasicHttpAuthentication(new URI(host), user, password);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new TrackerAdapterException(e.getMessage());
        }
    }

    public ArrayList<Issue> getIssues()
    {
        ArrayList<Issue> issues = new ArrayList<Issue>();

        final NullProgressMonitor pm = new NullProgressMonitor();

        SearchResult result = getClient().getSearchClient().searchJql(
           String.format("assignee = %s" , "nyver"),
           pm
        );

        if (result.getTotal() > 0) {
            for(BasicIssue basicIssue: result.getIssues()) {
                com.atlassian.jira.rest.client.domain.Issue issue = getClient().getIssueClient().getIssue(basicIssue.getKey(), pm);

                issues.add(new Issue(issue.getKey(), issue.getSummary()));
            }
        }

        return issues;
    }
}
