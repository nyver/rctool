package com.nyver.rctool.model;

import java.util.Date;

/**
 * Filter class
 *
 * @author Yuri Novitsky
 */
public class Filter
{
    private Date startDate;
    private Date endDate;
    private String jql;

    public Filter(Date startDate, Date endDate, String jql)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.jql = jql;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getJql() {
        return jql;
    }

    public void setJql(String jql) {
        this.jql = jql;
    }

}
