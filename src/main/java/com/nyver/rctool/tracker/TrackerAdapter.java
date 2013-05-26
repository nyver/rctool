package com.nyver.rctool.tracker;

import com.nyver.rctool.model.Filter;
import com.nyver.rctool.model.Issue;

import java.util.ArrayList;
import java.util.Date;

/**
 * TrackerAdapter abstract class
 *
 * @author Yuri Novitsky
 */
public abstract class TrackerAdapter
{
    protected String host;
    protected String user;
    protected String password;


    protected TrackerAdapter(String host, String user, String password)
    {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public abstract void setup() throws TrackerAdapterException;

    public static TrackerAdapter factory(String type, String host, String user, String password) throws TrackerAdapterException
    {
        TrackerAdapter adapter =  new JiraAdapter(host, user, password);
        adapter.setup();
        return adapter;
    }

    public abstract ArrayList<Issue> getIssues(Filter filter);

}
