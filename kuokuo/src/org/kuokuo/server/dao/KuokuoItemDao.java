/**
 * 
 */
package org.kuokuo.server.dao;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.kuokuo.client.data.KuokuoItem;
import org.kuokuo.resource.ResourceDef;

import com.chenlb.mmseg4j.analysis.MaxWordAnalyzer;

/**
 * @version Dec 6, 2009 9:03:03 PM
 * @author Dingmeng (xuedm79@gmail.com)
 * 
 */
public class KuokuoItemDao extends AbstractDao
{
    /**
     * delete all objects
     * 
     * @return
     */
    public int deleteAll()
    {
        Session session = getSession();

        try
        {
            session.beginTransaction();
            Query query = session.createQuery("delete from KuokuoItem");
            int ret = query.executeUpdate();
            session.getTransaction().commit();
            return ret;
        }
        finally
        {
            releaseSession(session);
        }
    }
    
    public boolean insertItem(File file, ResourceDef def)
    {
        Session session = getSession();
        try
        {
            session.beginTransaction();

            String name = file.getName();

            KuokuoItem item = new KuokuoItem();
            item.setName(name);
            item.setType(def.getType());
            item.setRootPath(def.getRootPath());
            item.setPath(file.getAbsolutePath());
            item.setFolderPath(file.getParent());
            item.setLastModified(new Date(file.lastModified()));
            item.setFolder(file.isDirectory());
            if (!item.isFolder())
            {
                item.setLength(file.length());
                item.setFolder(false);
                int pos = name.lastIndexOf(".");
                if (pos > 0)
                {
                    item.setFormat(name.substring(pos + 1));
                }
            }
            session.saveOrUpdate(item);
            session.getTransaction().commit();
            return true;
        }
        finally
        {
            releaseSession(session);
        }
    }
    
    /**
     * get count of all items
     * @return
     */
    public int getAllItemsCount()
    {
        return getAllItemsCount(null);
    }
    
    public int getAllItemsCount(String type)
    {
        Session session = getSession();
        try
        {
            Criteria crit = session.createCriteria(KuokuoItem.class);
            if(type != null)
            {
                crit.add(Restrictions.eq("type", type));
            }
            return crit.list().size();
        }
        finally
        {
            releaseSession(session);
        }
    }
    
    public List<KuokuoItem> getKuokuoItemOrderByModified(int from, int pageSize)
    {
        return getKuokuoItemOrderByModified(null, from, pageSize);
    }

    @SuppressWarnings("unchecked")
    public List<KuokuoItem> getKuokuoItemOrderByModified(String type, int from, int pageSize)
    {
        Session session = getSession();
        try
        {
            Criteria crit = session.createCriteria(KuokuoItem.class);
            crit = crit.addOrder(Order.desc("lastModified"));
            if(type != null)
            {
                crit.add(Restrictions.eq("type", type));
            }
            crit.setFirstResult(from);
            crit.setMaxResults(pageSize);
            return crit.list();
        }
        finally
        {
            releaseSession(session);
        }
    }

    @SuppressWarnings("unchecked")
    public void createFullTextSession()
    {
        Session session = getSession();
        try
        {
            FullTextSession fullTextSession = Search.getFullTextSession(session);
            fullTextSession.beginTransaction();
            Criteria crit = session.createCriteria(KuokuoItem.class);
            List<KuokuoItem> list = crit.list();
            for (KuokuoItem item : list)
            {
                fullTextSession.index(item);
            }
            fullTextSession.getTransaction().commit();
        }
        finally
        {
            releaseSession(session);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<KuokuoItem> fullTextSearch(String word)
    {
        Session session = getSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        
        try
        {
            fullTextSession.beginTransaction();
            Analyzer analyzer = new MaxWordAnalyzer();
            QueryParser parser = new QueryParser(Version.LUCENE_24, "name", analyzer);
            org.apache.lucene.search.Query query = parser.parse(word);
            QueryScorer scorer = new QueryScorer(parser.parse(word));
            SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b><font color=red>", "</font></b>");
            Highlighter highlighter = new Highlighter(formatter, scorer);
            FullTextQuery hibQuery = fullTextSession.createFullTextQuery(query, KuokuoItem.class);
            List<KuokuoItem> list = hibQuery.list();
            List<KuokuoItem> ret = new Vector<KuokuoItem>(list.size());
            for (KuokuoItem item : list)
            {
                item.setHighlightName(highlighter.getBestFragment(analyzer, "name", item.getName()));
                ret.add(item);
            }
            
            return ret;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            fullTextSession.getTransaction().commit();
            releaseSession(session);
        }
    }
    
    public KuokuoItem saveOrUpdate(KuokuoItem item)
    {
        Session session = getSession();
        try
        {
            session.beginTransaction();
            session.saveOrUpdate(item);
            session.getTransaction().commit();
            return item;
        }
        finally
        {
            releaseSession(session);
        }
    }
}
