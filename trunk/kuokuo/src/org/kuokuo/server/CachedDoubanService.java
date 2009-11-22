package org.kuokuo.server;

import java.util.List;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.douban.SubjectEntry;
import com.google.gdata.data.douban.SubjectFeed;

public class CachedDoubanService
{
    private DoubanService dbService;

    public CachedDoubanService()
    {
        String apiKey = "0fc141a814ca4f3d2f43c4f83e5eedb7";
        dbService = new DoubanService("kuokuo", apiKey);// read-only service
    }

    public DoubanResource getInfo(String query, DoubanResourceType type) throws Exception
    {
        SubjectFeed feed=null;
        switch(type){
        case MOVIE:feed=dbService.findMovie(query, null, 1, 1);break;
        }
        List<SubjectEntry> list = feed.getEntries();
        if(list.size()==0){
            return null;
        }
        SubjectEntry entry = list.get(0);
        
        DoubanResource rv = new DoubanResource(entry);
        return rv;
    }

}
