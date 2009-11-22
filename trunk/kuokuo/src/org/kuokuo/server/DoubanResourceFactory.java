package org.kuokuo.server;

import org.kuokuo.client.data.DoubanResource;

import com.google.gdata.data.douban.SubjectEntry;

public class DoubanResourceFactory
{
    public static DoubanResource create(SubjectEntry entry)
    {
        DoubanResource rv=new DoubanResource();
        rv.title=entry.getTitle().getPlainText();
        rv.imageURL=entry.getLink("image", null).getHref();
        //rv.id=entry.getId();
        rv.selfURL=entry.getLink("alternate", null).getHref();
        return rv;
    }
}
