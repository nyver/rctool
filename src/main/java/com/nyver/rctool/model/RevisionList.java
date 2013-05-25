package com.nyver.rctool.model;

import com.nyver.rctool.csv.CvsAdapter;
import com.nyver.rctool.csv.CvsAdapterException;

import java.util.ArrayList;

/**
 * Revision List class
 *
 * @author Yuri Novitsky
 */
public class RevisionList {

    private CvsAdapter cvsAdapter;
    private Revision[] revisions;

    public RevisionList(CvsAdapter adapter)
    {
        cvsAdapter = adapter;
    }

    public CvsAdapter getAdapter()
    {
        return cvsAdapter;
    }

    public ArrayList<Revision> getList() throws CvsAdapterException {
        return getAdapter().getRevisions();
    }
}
