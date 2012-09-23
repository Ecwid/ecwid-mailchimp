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
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.MailChimpClient;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class AbstractMethodTestCase {
	protected static final int MAX_EMAILS = 50;

	protected final MailChimpClient client = new MailChimpClient();

	@AfterClass
	private void afterClass() {
		client.close();
	}

	protected List<String> emails(int from, int count) {
		List<String> result = new ArrayList<String>();
		for(int i=from; i<from+count; i++) {
			result.add(email(i));
		}
		return result;
	}

	protected String email(int i) {
		assertTrue(i >= 0 && i < MAX_EMAILS, ""+i);
		return "test+"+i+"@gmail.com";
	}
}
