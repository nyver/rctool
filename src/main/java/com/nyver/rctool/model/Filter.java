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

    public Filter(Date startDate, Date endDate)
    {
        this.startDate = startDate;
        this.endDate = endDate;
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
}
