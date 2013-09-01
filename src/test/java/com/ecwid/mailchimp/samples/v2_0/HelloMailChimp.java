/*
 * Copyright 2013 Ecwid, Inc.
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
package com.ecwid.mailchimp.samples.v2_0;

import com.ecwid.mailchimp.MailChimpClient;
import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.v2_0.lists.Email;
import com.ecwid.mailchimp.method.v2_0.lists.MemberInfoData;
import com.ecwid.mailchimp.method.v2_0.lists.MemberInfoMethod;
import com.ecwid.mailchimp.method.v2_0.lists.MemberInfoResult;
import com.ecwid.mailchimp.method.v2_0.lists.SubscribeMethod;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Sample of using ecwid-mailchimp library.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class HelloMailChimp {

	public static class MergeVars extends MailChimpObject {

		@Field
		public String EMAIL, FNAME, LNAME;

		public MergeVars() {
		}

		public MergeVars(String email, String fname, String lname) {
			this.EMAIL = email;
			this.FNAME = fname;
			this.LNAME = lname;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Enter api key: ");
		String apikey = in.readLine().trim();
		
		System.out.print("Enter list id: ");
		String listId = in.readLine().trim();
		
		System.out.print("Enter email: ");
		String email = in.readLine().trim();
		
		// reuse the same MailChimpClient object whenever possible
		MailChimpClient mailChimpClient = new MailChimpClient();

		// Subscribe a person
		SubscribeMethod subscribeMethod = new SubscribeMethod();
		subscribeMethod.apikey = apikey;
		subscribeMethod.id = listId;
		subscribeMethod.email = new Email();
		subscribeMethod.email.email = email;
		subscribeMethod.double_optin = false;
		subscribeMethod.update_existing = true;
		subscribeMethod.merge_vars = new MergeVars(email, "Vasya", "Pupkin");
		mailChimpClient.execute(subscribeMethod);
		
		System.out.println(email+" has been successfully subscribed to the list. Now will check it...");

		// check his status
		MemberInfoMethod memberInfoMethod = new MemberInfoMethod();
		memberInfoMethod.apikey = apikey;
		memberInfoMethod.id = listId;
		memberInfoMethod.emails = Arrays.asList(subscribeMethod.email);

		MemberInfoResult memberInfoResult = mailChimpClient.execute(memberInfoMethod);
		MemberInfoData data = memberInfoResult.data.get(0);
		System.out.println(data.email+"'s status is "+data.status);

		// Close http-connection when the MailChimpClient object is not needed any longer.
		// Generally the close method should be called from a "finally" block.
		mailChimpClient.close();
	}
}
