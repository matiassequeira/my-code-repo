/**
 * 
 */
package org.kuokuo.server.job.douban;

import java.io.IOException;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.util.ServiceException;

/**
 * @version Jan 3, 2010 11:26:59 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public interface TaskItem
{
    /**
     * execute task
     */
    public void execute(DoubanService service) throws IOException, ServiceException;
}
