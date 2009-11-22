package org.kuokuo.server;

import com.google.gdata.data.douban.SubjectEntry;

public class DoubanResource
{
    public String title;
    public String id;
    public String imageURL;
    
    public DoubanResource()
    {
    }

    public DoubanResource(SubjectEntry entry)
    {
        title=entry.getTitle().getPlainText();
        imageURL=entry.getLink("image", null).getHref();
    }

}
