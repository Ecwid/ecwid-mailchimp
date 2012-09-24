/*
 * Copyright 2012 Ecwid, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ecwid.mailchimp.connection;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implementation of {@link MailChimpConnectionManager}
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
