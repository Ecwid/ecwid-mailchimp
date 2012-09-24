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
package com.ecwid.mailchimp;

import com.ecwid.mailchimp.connection.HttpClientConnectionManager;
import com.ecwid.mailchimp.connection.JavaNetURLConnectionManager;
import com.ecwid.mailchimp.connection.MailChimpConnectionManager;
import com.ecwid.mailchimp.internal.gson.MailChimpGsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MailChimp API wrapper.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpClient implements Closeable {
	private static final Logger log = Logger.getLogger(MailChimpClient.class.getName());
	
	private final MailChimpConnectionManager connection;

	/**
	 * Constructs a {@code MailChimpClient} object accessing MailChimp API service point
	 * through the default connection manager (currently {@link HttpClientConnectionManager}).
	 */
	public MailChimpClient() {
		this(new HttpClientConnectionManager());
	}

	/**
	 * Constructs a {@code MailChimpClient} object accessing MailChimp API service point
	 * through the specified connection manager.
	 * <p>
	 * Use this constructor if the default connection manager
	 * (currently {@link HttpClientConnectionManager}) is not suitable. 
	 * For instance, in GAE environment you should use {@link JavaNetURLConnectionManager} instead.
	 * 
	 * @param connection connection manager to be used to access the service point
	 */
	public MailChimpClient(MailChimpConnectionManager connection) {
		this.connection = connection;
	}
	
	private String execute(String url, String request) throws IOException {
		if(log.isLoggable(Level.FINE)) {
			log.fine("Post to "+url+" : "+request);
		}
		String response = connection.post(url, URLEncoder.encode(request, "UTF-8"));
		if(log.isLoggable(Level.FINE)) {
			log.fine("Response: "+response);
		}
		return response;
	}
	
	private JsonElement execute(String url, JsonElement request) throws IOException {
		return new JsonParser().parse(execute(url, request.toString()));
	}
	
	/**
	 * Execute MailChimp API method.
	 * 
	 * @param method MailChimp API method to be executed
	 * @return execution result
	 */
	public <R> R execute(MailChimpMethod<R> method) throws IOException, MailChimpException {
		final Gson gson = MailChimpGsonFactory.createGson();

		JsonElement result = execute(buildUrl(method), gson.toJsonTree(method));
		if(result.isJsonObject()) {
			JsonElement error = result.getAsJsonObject().get("error");		
			if(error != null) {
				JsonElement code = result.getAsJsonObject().get("code");
				throw new MailChimpException(code.getAsInt(), error.getAsString());
			}
		}
		
		return gson.fromJson(result, method.getResultType());
	}
	
	private String buildUrl(MailChimpMethod<?> method) throws UnsupportedEncodingException {
		String apikey = method.apikey;
		if(apikey == null) throw new IllegalArgumentException("apikey is not set");
		
		String prefix;
		int dash = apikey.lastIndexOf('-');
		if(dash > 0) {
			prefix = apikey.substring(dash + 1);
		} else {
			throw new IllegalArgumentException("Wrong apikey: "+apikey);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("https://");
		sb.append(prefix);
		sb.append(".api.mailchimp.com/1.3/?method=");
		sb.append(URLEncoder.encode(method.getMethodName(), "UTF-8"));
		return sb.toString();
	}
	
	/**
	 * Release resources associated with the connection to MailChimp API service point.
	 */
	@Override
	public void close() {
		try {
			connection.close();
		} catch(IOException e) {
			log.log(Level.WARNING, "Could not close connection", e);
		}
	}
}
