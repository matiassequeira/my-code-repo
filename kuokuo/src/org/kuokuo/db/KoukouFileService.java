/**
 * 
 */
package org.kuokuo.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.kuokuo.Constants;
import org.wltea.analyzer.lucene.IKAnalyzer;


/**
 * @author Felix
 * 
 */
public class KoukouFileService implements Constants {
	private String[] searchFields = new String[] { "name", "fullName", "path",
			"tag" };
	private String formatterPreTag = "<font color='red'>";
	private String formatterPostTag = "</font>";

	public static String checkNullString(String str) {
		return str == null ? "" : str;
	}

	public void scanFolder(String rootPath) {
	}

	public void save(KoukouFile koukouFile) {
		Session session = HibernateUtil.getSession();
		try {
			session.beginTransaction();
			Timestamp now = new Timestamp(new Date().getTime());
			koukouFile.setRecordCreateTime(now);
			koukouFile.setRecordUpdateTime(now);
			session.save(koukouFile);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	
	public List<KoukouFile> selectAll() {
		Session session = HibernateUtil.getSession();
		try {
			List res = session.createQuery("from KoukouFile as koukouFile")
					.list();
			List<KoukouFile> list = new ArrayList<KoukouFile>(res);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return null;
	}

	public void deleteAll() {
		Session session = HibernateUtil.getSession();
		List<KoukouFile> koukouFiles = selectAll();
		try {
			session.beginTransaction();
			for (KoukouFile koukouFile : koukouFiles) {
				session.delete(koukouFile);
				session.flush();
				session.evict(koukouFile);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	
	public List<KoukouFile> fullTextSearch(String word) throws Exception {
		Session session = HibernateUtil.getSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.beginTransaction();
		List res = session.createQuery("from KoukouFile as koukouFile ").list();
		for (Object kFile : res) {
			fullTextSession.index((KoukouFile) kFile);
		}
		Analyzer analyzer = new IKAnalyzer();
		fullTextSession.getTransaction().commit();

		QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_29,
				searchFields, analyzer);
		org.apache.lucene.search.Query query = parser.parse(word);

		// highlighter
		QueryScorer scorer = new QueryScorer(query);
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
				formatterPreTag, formatterPostTag);
		Highlighter highlighter = new Highlighter(formatter, scorer);

		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
				query, KoukouFile.class);
		List result = hibQuery.list();
		List<KoukouFile> koukouFiles = new ArrayList<KoukouFile>();
		for (Object obj : result) {
			KoukouFile f = (KoukouFile) obj;
			f.setName(highlighter.getBestFragment(analyzer, "name",
					checkNullString(f.getName())));
			f.setDisplayName(highlighter.getBestFragment(analyzer, "fullName",
					checkNullString(f.getFullName())));
			f.setDisplayPath(highlighter.getBestFragment(analyzer, "path",
					checkNullString(f.getPath())));
			f.setTag(highlighter.getBestFragment(analyzer, "tag",
					checkNullString(f.getTag())));
			koukouFiles.add(f);
		}

		HibernateUtil.closeSession(session);
		return koukouFiles;
	}

	public PageRecord fullTextPageSearch(String word,
			String orderBy, int pageNo, int pageSize) throws Exception {
		Session session = HibernateUtil.getSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.beginTransaction();
		String hql = "from KoukouFile as koukouFile order by koukouFile." + orderBy+" asc ";
		List res = session.createQuery(
				hql).list();
		for (Object kFile : res) {
			fullTextSession.index((KoukouFile) kFile);
		}
		Analyzer analyzer = new IKAnalyzer();
		fullTextSession.getTransaction().commit();

		QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_29,
				searchFields, analyzer);
		org.apache.lucene.search.Query query = parser.parse(word);
		//
		// // 排序
		Sort sort = new Sort(new SortField[] { new SortField(orderBy,SortField.STRING) });
		
		// highlighter
		QueryScorer scorer = new QueryScorer(query);
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
				formatterPreTag, formatterPostTag);
		Highlighter highlighter = new Highlighter(formatter, scorer);

		FullTextQuery hibQuery = fullTextSession.createFullTextQuery(query,
				KoukouFile.class);
		//hibQuery.setSort(sort);
		//hibQuery.setFilter(arg0)
		int totalCount = hibQuery.list().size();
		int startIndex = (pageNo - 1) * pageSize;
		List result = hibQuery.setFirstResult(startIndex).setMaxResults(
				pageSize).list();
		List<KoukouFile> koukouFiles = new ArrayList<KoukouFile>();
		for (Object obj : result) {
			KoukouFile f = (KoukouFile) obj;
			f.setName(highlighter.getBestFragment(analyzer, "name",
					checkNullString(f.getName())));
			f.setDisplayName(highlighter.getBestFragment(analyzer, "fullName",
					checkNullString(f.getFullName())));
			f.setDisplayPath(highlighter.getBestFragment(analyzer, "path",
					checkNullString(f.getPath())));
			f.setTag(highlighter.getBestFragment(analyzer, "tag",
					checkNullString(f.getTag())));
			koukouFiles.add(f);
		}
		HibernateUtil.closeSession(session);
		PageRecord pageRecord = new PageRecord(
				koukouFiles, totalCount, pageNo, pageSize);

		return pageRecord;
	}
	
	public PageRecord namePageSearch(String word,
			String orderBy, int pageNo, int pageSize) throws Exception {
		Session session = HibernateUtil.getSession();
		HibernateUtil.closeSession(session);
		List<KoukouFile> koukouFiles = selectAll();
		int totalCount = koukouFiles.size();
		PageRecord pageRecord = new PageRecord(
				koukouFiles, totalCount, pageNo, pageSize);

		return pageRecord;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		KoukouFileService service = new KoukouFileService();

		HibernateUtil.closeSessionFactory();
	}

}
