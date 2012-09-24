package com.ecwid.mailchimp.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Alternate implementation of {@link MailChimpConnectionManager}
 * which uses standard java.net.* libraries to access MailChimp API service point.
 * 
 * @author James Broberg <jbroberg@gmail.com>
 */
public class PlainURLConnectionManager implements MailChimpConnectionManager  {
	private HttpURLConnection conn = null;

	@Override
	public String post(String url, String payload) throws IOException {

        URL mcUrl = new URL(url);
        conn = (HttpURLConnection) mcUrl.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		
		conn.addRequestProperty("Content-Type", "application/" + "POST");
		conn.setRequestProperty("Content-Length", Integer.toString(payload.length()));
		conn.getOutputStream().write(payload.getBytes("UTF8"));

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
 
		String output = "";
		StringBuilder sbResponse = new StringBuilder();
		while ((output = br.readLine()) != null) {
			sbResponse.append(output + "\n");
		}
 
		return sbResponse.toString();
	}
	
	@Override
	public void close() throws IOException {
		if (conn != null) {
			conn.disconnect();
		}		
	}
}
