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

import java.io.Closeable;
import java.io.IOException;

/**
 * Abstract connection manager to access MailChimp API service point.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public interface MailChimpConnectionManager extends Closeable {
	
	/**
	 * Make a POST request to MailChimp API service point.
	 * 
	 * @param url URL to post data to
	 * @param payload data to post
	 * 
	 * @return service endpoint response body if the response was successful (a 2xx status code)
	 * 
	 * @throws IOException if an I/O error occurred during the communication,
	 * or if the service point response was unsuccessful (>= 300 status code)
	 */
	public String post(String url, String payload) throws IOException;
}
