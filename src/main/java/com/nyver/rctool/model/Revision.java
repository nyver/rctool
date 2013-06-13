package com.nyver.rctool.model;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<RevisionChange> changes = new ArrayList<RevisionChange>();

    public Revision(String revision, Date date, String comment, String author, List<RevisionChange> changes)
    {
        this.revision = revision;
        this.date = date;
        this.comment = comment;
        this.author = author;
        this.changes = changes;
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

    public List<RevisionChange> getChanges() {
        return changes;
    }

    public void setChanges(List<RevisionChange> changes) {
        this.changes = changes;
    }
}
