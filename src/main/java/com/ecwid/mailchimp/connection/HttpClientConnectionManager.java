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
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

/**
 * Implementation of {@link MailChimpConnectionManager}
 * which uses Apache HttpClient library to access MailChimp API service point.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class HttpClientConnectionManager implements MailChimpConnectionManager {

	private static final int DEFAULT_TIMEOUT = 15000;

	private final HttpClient http = new DecompressingHttpClient(new DefaultHttpClient());

	/**
	 * Constructor.
	 * Equivalent to calling {@link #HttpClientConnectionManager(int, int)} with both parameters set to 15000 (15 seconds).
	 */
	public HttpClientConnectionManager() {
		this(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
	}

	/**
	 * Constructor.
	 *
	 * @param connectTimeout the timeout (in milliseconds) when trying to connect to the remote server
	 * @param readTimeout the timeout (in milliseconds) when when waiting for the response from the remote server
	 */
	public HttpClientConnectionManager(int connectTimeout, int readTimeout) {
		setConnectTimeout(connectTimeout);
		setReadTimeout(readTimeout);
	}

	@Override
	public String post(String url, String payload) throws IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(payload, "UTF-8"));
		HttpResponse response = http.execute(post);
		if (response.getEntity() != null) {
			return EntityUtils.toString(response.getEntity(), "UTF-8").trim();
		} else {
			throw new IOException(response.getStatusLine().toString());
		}
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
