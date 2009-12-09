package org.kuokuo.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.kuokuo.client.data.DoubanResource;
import org.kuokuo.client.data.DoubanResourceType;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.douban.SubjectEntry;
import com.google.gdata.data.douban.SubjectFeed;

public class CachedDoubanService
{
    private static Logger logger= Logger.getLogger(CachedDoubanService.class.getName());
    
    private DoubanService dbService;

    private Map<String, DoubanResource> cache;

    public CachedDoubanService()
    {
        String apiKey = "0fc141a814ca4f3d2f43c4f83e5eedb7";
        dbService = new DoubanService("kuokuo", apiKey);// read-only service

        // the simplest implementation of in-memory cache
        // More advanced cache systems such as JCS, ehache, oscache, will be
        // considered using when necessary
        cache = Collections.synchronizedMap(new HashMap<String, DoubanResource>());
    }

    private static long lastQuery = 0;
    
    /**
     * 
     * @param query usually the file name
     * @param type
     * @param cacheKey the key for the map-based cache, usually the full path
     * @return
     * @throws Exception
     */
    public DoubanResource getInfo(String query, DoubanResourceType type, String cacheKey) throws Exception
    {
        DoubanResource doubanResource = cache.get(cacheKey);
        if (doubanResource != null)
        {
            logger.info("hit cache entry ["+cacheKey+"] with douban id: "+doubanResource.selfURL);
            return doubanResource;
        }
        
        //block query within 3 seconds.
        if(System.currentTimeMillis() - lastQuery < 3000)
        {
            return null;
        }

        lastQuery = System.currentTimeMillis();
        

        SubjectFeed feed = null;
        switch (type)
        {
        case MOVIE:
            feed = dbService.findMovie(query, null, 1, 1);
            break;
        case MUSIC:
            feed = dbService.findMusic(query, null, 1, 1);
            break;
        case BOOK:
            feed = dbService.findBook(query, null, 1, 1);
            break;             
        }
        //feed never null
        List<SubjectEntry> list = feed.getEntries();
        if (list.size() == 0)
        {
            logger.info("no entry found in douban for ["+cacheKey+"]");
            return null;
        }
        SubjectEntry entry = list.get(0);

        DoubanResource rv = DoubanResourceFactory.create(entry);
        logger.info("caching entry ["+cacheKey+"] with douban id: "+rv.selfURL);
        cache.put(cacheKey, rv);
        return rv;
    }

}
