package com.nyver.rctool.model;

import java.util.Date;

/**
 * Tracker Issue
 *
 * @author Yuri Novitsky
 */
public class Issue
{
    private String key;
    private String summary;
    private Date date;

    public Issue(String key, String summary, Date date)
    {
        this.key = key;
        this.summary = summary;
        this.date = date;
    }

    public Issue(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
