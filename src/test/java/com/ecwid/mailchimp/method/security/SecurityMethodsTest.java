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
package com.ecwid.mailchimp.method.security;

import com.ecwid.mailchimp.method.AbstractMethodTestCase;
import java.util.HashMap;
import java.util.Map;
import static org.testng.Assert.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class SecurityMethodsTest extends AbstractMethodTestCase {
	private final String username, password, apiKey;

	@Parameters({"mailchimp.test.username", "mailchimp.test.password", "mailchimp.test.apikey"})
	public SecurityMethodsTest(String username, String password, String apiKey) {
		this.username = username;
		this.password = password;
		this.apiKey = apiKey;
	}

	@Test
	public void test() throws Exception {
		ApikeyAddMethod apikeyAddMethod = new ApikeyAddMethod();
		apikeyAddMethod.apikey = apiKey;
		apikeyAddMethod.username = username;
		apikeyAddMethod.password = password;

		String newKey = client.execute(apikeyAddMethod);
		assertNotNull(newKey);

		ApikeysMethod apikeysMethod = new ApikeysMethod();
		apikeysMethod.apikey = apiKey;
		apikeysMethod.username = username;
		apikeysMethod.password = password;
		apikeysMethod.expired = true;

		Map<String, ApikeyInfo> apikeys = getApikeys();
		assertNull(apikeys.get(apiKey).expired_at);
		assertNull(apikeys.get(newKey).expired_at);

		ApikeyExpireMethod apikeyExpireMethod = new ApikeyExpireMethod();
		apikeyExpireMethod.apikey = newKey;
		apikeyExpireMethod.username = username;
		apikeyExpireMethod.password = password;
		assertTrue(client.execute(apikeyExpireMethod));

		apikeys = getApikeys();
		assertNull(apikeys.get(apiKey).expired_at);
		assertNotNull(apikeys.get(newKey).expired_at);
	}

	private Map<String, ApikeyInfo> getApikeys() throws Exception {
		ApikeysMethod apikeysMethod = new ApikeysMethod();
		apikeysMethod.apikey = apiKey;
		apikeysMethod.username = username;
		apikeysMethod.password = password;
		apikeysMethod.expired = true;

		Map<String, ApikeyInfo> apikeys = new HashMap<String, ApikeyInfo>();
		for(ApikeyInfo info: client.execute(apikeysMethod)) {
			apikeys.put(info.apikey, info);
		}

		return apikeys;
	}
}
