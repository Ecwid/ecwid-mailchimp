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
package com.ecwid.mailchimp.samples.v1_3;

import com.ecwid.mailchimp.MailChimpClient;
import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.v1_3.list.ListMemberInfoMethod;
import com.ecwid.mailchimp.method.v1_3.list.ListMemberInfoResult;
import com.ecwid.mailchimp.method.v1_3.list.ListSubscribeMethod;
import com.ecwid.mailchimp.method.v1_3.list.MemberInfo;
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
		ListSubscribeMethod listSubscribeMethod = new ListSubscribeMethod();
		listSubscribeMethod.apikey = apikey;
		listSubscribeMethod.id = listId;
		listSubscribeMethod.email_address = email;
		listSubscribeMethod.double_optin = false;
		listSubscribeMethod.update_existing = true;
		listSubscribeMethod.merge_vars = new MergeVars(email, "Vasya", "Pupkin");
		mailChimpClient.execute(listSubscribeMethod);
		
		System.out.println(email+" has been successfully subscribed to the list. Now will check it...");

		// check his status
		ListMemberInfoMethod listMemberInfoMethod = new ListMemberInfoMethod();
		listMemberInfoMethod.apikey = apikey;
		listMemberInfoMethod.id = listId;
		listMemberInfoMethod.email_address = Arrays.asList(email);

		ListMemberInfoResult listMemberInfoResult = mailChimpClient.execute(listMemberInfoMethod);
		MemberInfo info = listMemberInfoResult.data.get(0);
		System.out.println(info.email+"'s status is "+info.status);

		// Close http-connection when the MailChimpClient object is not needed any longer.
		// Generally the close method should be called from a "finally" block.
		mailChimpClient.close();
	}
}
