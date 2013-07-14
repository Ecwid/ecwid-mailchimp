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
import org.apache.http.params.HttpConnectionParams;

/**
 * Implementation of {@link MailChimpConnectionManager}
 * which uses Apache HttpClient library to access MailChimp API service point.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class HttpClientConnectionManager implements MailChimpConnectionManager {

	private static final int DEFAULT_TIMEOUT = 15000;

	private final HttpClient http = new DefaultHttpClient();

	public HttpClientConnectionManager() {
		this(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
	}

	public HttpClientConnectionManager(int connectTimeout, int readTimeout) {
		setConnectTimeout(connectTimeout);
		setReadTimeout(readTimeout);
	}

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

	public int getConnectTimeout() {
		return HttpConnectionParams.getConnectionTimeout(http.getParams());
	}

	public void setConnectTimeout(int connectTimeout) {
		HttpConnectionParams.setConnectionTimeout(http.getParams(), connectTimeout);
	}

	public int getReadTimeout() {
		return HttpConnectionParams.getSoTimeout(http.getParams());
	}

	public void setReadTimeout(int readTimeout) {
		HttpConnectionParams.setSoTimeout(http.getParams(), readTimeout);
	}
}
