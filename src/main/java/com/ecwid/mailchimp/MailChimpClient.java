/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp;

import com.ecwid.mailchimp.method.MailChimpMethod;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
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
	
	private final String apiKey;
	private final String server;
	
	/**
	 * Constructor.
	 * 
	 * @param apiKey MailChimp API key 
	 */
	public MailChimpClient(String apiKey) {
		this.apiKey = apiKey;
		
		int dash = apiKey.lastIndexOf('-');
		if(dash > 0) {
			String prefix = apiKey.substring(dash + 1);
			this.server = prefix + ".api.mailchimp.com";
		} else {
			throw new IllegalArgumentException("Wrong API key: "+apiKey);
		}
	}
	
	private String execute(String method, String request) throws IOException {
		String url = "https://"+server + "/1.3/?method="+method;
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
	
	/**
	 * Execute MailChimp API method.
	 * 
	 * @param method API method to be executed
	 * @return method execution result
	 */
	public <R> R execute(MailChimpMethod<R> method) throws IOException, MailChimpException {
		JsonObject payload = gson.toJsonTree(method).getAsJsonObject();
		payload.addProperty("apikey", apiKey);
		JsonElement result = new JsonParser().parse(execute(method.getMethodName(), payload.toString()));
		if(result.isJsonObject()) {
			JsonElement error = result.getAsJsonObject().get("error");		
			if(error != null) {
				JsonElement code = result.getAsJsonObject().get("code");
				throw new MailChimpException(code.getAsInt(), error.getAsString());
			}
		}
		return gson.fromJson(result, method.getResultType());
	}
	
	/**
	 * Release resources.
	 */
	public void close() {
		http.getConnectionManager().shutdown();
	}
}
