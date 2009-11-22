package org.kuokuo.client.data;

import java.io.Serializable;

import com.google.gdata.data.douban.SubjectEntry;

public class DoubanResource implements Serializable
{
    private static final long serialVersionUID = 8903939680575778710L;

    public String title;
    public String imageURL;
    public String selfURL;
    
    public DoubanResource()
    {
    }
}