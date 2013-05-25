package com.nyver.rctool.model;

/**
 * Tracker Issue
 *
 * @author Yuri Novitsky
 */
public class Issue
{
    private String key;
    private String summary;

    public Issue(String key, String summary)
    {
        this.key = key;
        this.summary = summary;
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
}
