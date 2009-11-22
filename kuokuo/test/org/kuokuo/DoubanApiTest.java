package org.kuokuo;

import java.util.List;

import junit.framework.TestCase;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.douban.SubjectEntry;
import com.google.gdata.data.douban.SubjectFeed;

public class DoubanApiTest extends TestCase {
	public void testSearchMovie() throws Exception {
		String apiKey = "0fc141a814ca4f3d2f43c4f83e5eedb7";
		DoubanService myService = new DoubanService("kuokuo", apiKey);//read-only service
		
		SubjectFeed movieFeed = myService.findMovie("钢之炼金术师 2009",null, 1,1);
		List<SubjectEntry> list = movieFeed.getEntries();
		assertTrue(list.size()>0);
		SubjectEntry movie = list.get(0);
		System.out.println(movie.getTitle().getPlainText());
		System.out.println(movie.getLink("image", null).getHref());
	}
}
