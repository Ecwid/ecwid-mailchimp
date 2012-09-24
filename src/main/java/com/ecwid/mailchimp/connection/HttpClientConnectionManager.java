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
import java.net.URLEncoder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Implementation of {@link MailChimpConnectionManager}
 * which uses Apache HttpClient library to access MailChimp API service point.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class HttpClientConnectionManager implements MailChimpConnectionManager {
	private final HttpClient http = new DefaultHttpClient();

	@Override
	public String post(String url, String payload) throws IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(payload));
		return http.execute(post, new BasicResponseHandler());
	}

	@Override
	public void close() {
		http.getConnectionManager().shutdown();
	}
}
