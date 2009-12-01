/**
 * 
 */
package org.kuokuo.db;

import java.util.List;

import junit.framework.TestCase;

/**
 * @author Felix
 * 
 */
public class KoukouFileServiceTestCase extends TestCase {
	private KoukouFileService service = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		service = new KoukouFileService();
		
		super.setUp();
//		KoukouFile koukouFile = new KoukouFile();
//		koukouFile
//				.setName("Cold.Mountain.冷山.双语字幕.HR-HDTV.AC3.960X528.x264-人人影视制作");
//		koukouFile
//				.setFullName("Cold.Mountain.冷山.双语字幕.HR-HDTV.AC3.960X528.x264-人人影视制作.avi");
//		koukouFile
//				.setPath("D:/happy/movie/电影/Cold.Mountain.冷山.双语字幕.HR-HDTV.AC3.960X528.x264-人人影视制作.avi");
//		service.save(koukouFile);
//		koukouFile = new KoukouFile();
//		koukouFile
//				.setName("The.Motorcycle.Diaries.摩托日记.中文字幕.HR-HDTV.AC3.960X516.X264-人人影视制作");
//		koukouFile
//				.setFullName("The.Motorcycle.Diaries.摩托日记.中文字幕.HR-HDTV.AC3.960X516.X264-人人影视制作.avi");
//		koukouFile
//				.setPath("D:/happy/movie/电影/The.Motorcycle.Diaries.摩托日记.中文字幕.HR-HDTV.AC3.960X516.X264-人人影视制作.avi");
//		service.save(koukouFile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link org.kuokuo.db.KoukouFileService#fullTextSearch(java.lang.String)}.
	 * @throws Exception 
	 */
	public void testFullTextSearch() throws Exception {
		
		List<KoukouFile> ret = service.fullTextSearch("字幕");
		assertEquals(11, ret.size());
	}
	/**
	 * Test method for
	 * {@link org.kuokuo.db.KoukouFileService#fullTextPageSearch(java.lang.String,java.lang.String,int,int)}.
	 * @throws Exception 
	 */
	public void testFullTextPageSearch() throws Exception {
		
		PageRecord ret = service.fullTextPageSearch("csi", "path", 1, 30);
		for (Object file : ret.getResult()){
			System.out.println(file);
		}
		assertEquals(17, ret.getResult().size());
		HibernateUtil.closeSessionFactory();
	}
}
