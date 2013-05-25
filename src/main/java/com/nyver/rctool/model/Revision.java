package com.nyver.rctool.model;

import java.util.Date;

/**
 * Revision class
 *
 * @author Yuri Novitsky
 */
public class Revision
{

    private String revision;
    private String comment;
    private String author;
    private Date date;

    public Revision(String revision, Date date, String comment, String author)
    {
        this.revision = revision;
        this.date = date;
        this.comment = comment;
        this.author = author;
    }

    public Revision(String revision)
    {
        this.revision = revision;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
