package org.kuokuo.server;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Logger;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.douban.SubjectEntry;
import com.google.gdata.data.douban.SubjectFeed;

public class CachedDoubanService
{
    private static Logger logger= Logger.getLogger(CachedDoubanService.class.getName());
    
    private DoubanService dbService;

    private Map<String, DoubanResource> cache4Movies;

    public CachedDoubanService()
    {
        String apiKey = "0fc141a814ca4f3d2f43c4f83e5eedb7";
        dbService = new DoubanService("kuokuo", apiKey);// read-only service

        // the simplest implementation of in-memory cache
        // More advanced cache systems such as JCS, ehache, oscache, will be
        // considered using when necessary
        cache4Movies = Collections.synchronizedMap(new WeakHashMap<String, DoubanResource>());
    }

    public DoubanResource getInfo(String query, DoubanResourceType type) throws Exception
    {
        SubjectFeed feed = null;
        switch (type)
        {
        case MOVIE:
            DoubanResource doubanResource = cache4Movies.get(query);
            if (doubanResource != null)
            {
                logger.info("hit cache entry ["+query+"] with douban id: "+doubanResource.selfURL);
                return doubanResource;
            }
            feed = dbService.findMovie(query, null, 1, 1);
            break;
        }
        List<SubjectEntry> list = feed.getEntries();
        if (list.size() == 0)
        {
            return null;
        }
        SubjectEntry entry = list.get(0);

        DoubanResource rv = new DoubanResource(entry);
        switch (type)
        {
        case MOVIE:
            logger.info("caching entry ["+query+"] with douban id: "+rv.selfURL);
            cache4Movies.put(query, rv);
            break;
        }
        return rv;
    }

}
