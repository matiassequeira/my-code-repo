package org.kuokuo;

import java.util.List;

import junit.framework.TestCase;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.douban.SubjectEntry;
import com.google.gdata.data.douban.SubjectFeed;

public class DoubanApiTest extends TestCase {
	public void testSearchMovie() throws Exception {
		String apiKey = "0fc141a814ca4f3d2f43c4f83e5eedb7";
		String secret = "530a4522721b9682";

		DoubanService myService = new DoubanService("kuokuo", apiKey, secret);
		System.out.println("please paste the url in your webbrowser, complete the authorization then come back:");
		System.out.println(myService.getAuthorizationUrl(null));
		byte buffer[] = new byte[1];
		System.in.read(buffer);
		
		myService.getAccessToken();
		//myService.setAccessToken("", "");
		
		SubjectFeed movieFeed = myService.findMovie(null,"钢之炼金术师 2009", 1,2);
		List<SubjectEntry> list = movieFeed.getEntries();
		assertTrue(list.size()>0);
		SubjectEntry movie = list.get(0);
		System.out.println(movie.getTitle());
	}
}
