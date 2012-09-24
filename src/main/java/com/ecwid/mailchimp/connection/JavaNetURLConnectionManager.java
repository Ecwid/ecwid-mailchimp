package com.ecwid.mailchimp.connection;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Alternate implementation of {@link MailChimpConnectionManager}
 * which uses standard java.net.* libraries to access MailChimp API service point.
 * 
 * @author James Broberg <jbroberg@gmail.com>
 */
public class JavaNetURLConnectionManager implements MailChimpConnectionManager  {
	private HttpURLConnection conn = null;

	@Override
	public String post(String url, String payload) throws IOException {

        URL mcUrl = new URL(url);
        conn = (HttpURLConnection) mcUrl.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");

		byte bytes[] = payload.getBytes("UTF-8");
		conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
		conn.setRequestProperty("Content-Length", Integer.toString(bytes.length));
		conn.getOutputStream().write(bytes);
		
		Reader reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
		StringBuilder sb = new StringBuilder();
		char buf[] = new char[4096];
		int cnt;
		while ((cnt = reader.read(buf)) >= 0) {
			sb.append(buf, 0, cnt);
		}
		return sb.toString();
	}
	
	@Override
	public void close() throws IOException {
		if (conn != null) {
			conn.disconnect();
		}		
	}
}
