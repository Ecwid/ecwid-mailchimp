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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * MailChimp API wrapper.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpClient {
	private static final Logger log = Logger.getLogger(MailChimpClient.class.getName());
	
	private final HttpClient http = new DefaultHttpClient();
	private final Gson gson = MailChimpGsonFactory.createGson();
	
	private String execute(String url, String request) throws IOException {
		if(log.isLoggable(Level.FINE)) {
			log.fine("Post to "+url+" : "+request);
		}
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(URLEncoder.encode(request, "UTF-8")));
		String response = http.execute(post, new BasicResponseHandler());
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
	 * @param method API method to be executed
	 * @return method execution result
	 */
	public <R> R execute(MailChimpMethod<R> method) throws IOException, MailChimpException {
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
	 * Release resources.
	 */
	public void close() {
		http.getConnectionManager().shutdown();
	}
}
