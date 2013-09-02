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
package com.ecwid.mailchimp.method.v2_0.lists;

import com.ecwid.mailchimp.MailChimpException;
import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.AbstractMethodTestCase;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class SubscribeMethodsTest extends AbstractMethodTestCase {
	private static final Logger log = Logger.getLogger(SubscribeMethodsTest.class.getName());

	private final String apiKey;
	private final String listId;
	
	private static class MergeVars extends MailChimpObject {
		@MailChimpObject.Field
		private final String FNAME, LNAME;

		private MergeVars() {
			this(null, null);
		}

		public MergeVars(String fname, String lname) {
			this.FNAME = fname;
			this.LNAME = lname;
		}
	}

	@Parameters({"mailchimp.test.apikey", "mailchimp.test.listid"})
	public SubscribeMethodsTest(String apiKey, String listId) {
		this.apiKey = apiKey;
		this.listId = listId;
	}

	@BeforeMethod
	private void cleanup() throws Exception {
		unsubscribeBatch(0, MAX_EMAILS, true);
	}

	@Test
	public void testExecute_ListBatch() throws Exception {
		BatchSubscribeResult batchSubscribeResult = listSubscribeBatch(4, 5, false, false);
		assertEquals((int) batchSubscribeResult.add_count, 5);
		assertEquals((int) batchSubscribeResult.update_count, 0);
		assertEquals((int) batchSubscribeResult.error_count, 0);

		MemberInfoResult memberInfoResult = memberInfo(3, 5);
		assertEquals((int) memberInfoResult.success_count, 4);
		assertEquals((int) memberInfoResult.error_count, 1);

		BatchUnsubscribeResult batchUnsubscribeResult = unsubscribeBatch(4, 2, false);
		assertEquals((int) batchUnsubscribeResult.success_count, 2);
		assertEquals((int) batchUnsubscribeResult.error_count, 0);

		MembersResult membersResult = listMembers("subscribed");
		assertEquals((int) membersResult.total, 3);

		membersResult = listMembers("unsubscribed");
		assertEquals((int) membersResult.total, 2);
	}

	@Test
	public void testExecute_ListSingle() throws Exception {
		SubscribeMethod subscribe = new SubscribeMethod();
		subscribe.apikey = apiKey;
		subscribe.id = listId;
		subscribe.email = email_v2_0(0);
		subscribe.double_optin = false;
		subscribe.update_existing = false;
		subscribe.merge_vars = new MergeVars("Vasya", "Pupkin");
		assertEquals(client.execute(subscribe).email, email(0));

		try {
			client.execute(subscribe);
			fail();
		} catch(MailChimpException e) {
			// aready registered
			assertEquals(e.code, 214);
		}

		MemberInfoResult memberInfoResult = memberInfo(0, 1);
		assertEquals(memberInfoResult.data.get(0).email, email(0));
		assertEquals(memberInfoResult.data.get(0).status, "subscribed");
		assertEquals(memberInfoResult.data.get(0).merges.get("FNAME"), "Vasya");
		assertEquals(memberInfoResult.data.get(0).merges.get("LNAME"), "Pupkin");

		UpdateMemberMethod updateMember = new UpdateMemberMethod();
		updateMember.apikey = apiKey;
		updateMember.id = listId;
		updateMember.email = email_v2_0(0);
		updateMember.merge_vars = new MergeVars(null, "Popkin");
		assertEquals(client.execute(updateMember).email, email(0));

		UnsubscribeMethod unsubscribe = new UnsubscribeMethod();
		unsubscribe.apikey = apiKey;
		unsubscribe.id = listId;
		unsubscribe.email = email_v2_0(0);
		unsubscribe.delete_member = false;
		assertTrue(client.execute(unsubscribe).complete);

		memberInfoResult = memberInfo(0, 1);
		assertEquals(memberInfoResult.data.get(0).email, email(0));
		assertEquals(memberInfoResult.data.get(0).status, "unsubscribed");
		assertEquals(memberInfoResult.data.get(0).merges.get("FNAME"), "Vasya");
		assertEquals(memberInfoResult.data.get(0).merges.get("LNAME"), "Popkin");

		unsubscribe.delete_member = true;
		assertTrue(client.execute(unsubscribe).complete);

		try {
			client.execute(unsubscribe);
			fail();
		} catch(MailChimpException e) {
			// not exist
			assertEquals(e.code, 232);
		}
	}

	private BatchUnsubscribeResult unsubscribeBatch(int from, int count, boolean delete_member) throws Exception {
		BatchUnsubscribeMethod request = new BatchUnsubscribeMethod();
		request.apikey = apiKey;
		request.id = listId;
		request.batch = emails_v2_0(from, count);
		request.delete_member = delete_member;
		BatchUnsubscribeResult result = client.execute(request);
		log.info("Result: " + result);
		assertEquals(count, result.success_count + result.error_count);
		return result;
	}

	private BatchSubscribeResult listSubscribeBatch(int from, int count, boolean double_optin, boolean update_existing) throws Exception {
		BatchSubscribeMethod request = new BatchSubscribeMethod();
		request.apikey = apiKey;
		request.id = listId;
		request.double_optin = double_optin;
		request.update_existing = update_existing;

		request.batch = new ArrayList<BatchSubscribeInfo>();
		for(int i=from; i<from+count; i++) {
			BatchSubscribeInfo info = new BatchSubscribeInfo();
			info.email = email_v2_0(i);
			info.merge_vars = new MergeVars("Batch"+i, "Subscribed"+i);
			request.batch.add(info);
		}

		BatchSubscribeResult result = client.execute(request);
		log.info("Result: " + result);
		return result;
	}

	private MemberInfoResult memberInfo(int from, int count) throws Exception {
		MemberInfoMethod request = new MemberInfoMethod();
		request.apikey = apiKey;
		request.id = listId;
		request.emails = emails_v2_0(from, count);
		MemberInfoResult result = client.execute(request);
		log.info("Result: " + result);
		return result;
	}

	private MembersResult listMembers(String status) throws Exception {
		MembersMethod request = new MembersMethod();
		request.apikey = apiKey;
		request.id = listId;
		request.status = status;
		MembersResult result = client.execute(request);
		log.info("Result: " + result);
		return result;
	}
}
