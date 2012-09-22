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
package com.ecwid.mailchimp.method.list;

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
		private final String EMAIL, FNAME, LNAME;

		private MergeVars() {
			this(null, null, null);
		}

		public MergeVars(String email, String fname, String lname) {
			this.EMAIL = email;
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
		listUnsubscribeBatch(0, MAX_EMAILS, true);
	}

	@Test
	public void testExecute_ListBatch() throws Exception {
		ListBatchSubscribeResult batchSubscribeResult = listSubscribeBatch(4, 5, false, false);
		assertEquals((int) batchSubscribeResult.add_count, 5);
		assertEquals((int) batchSubscribeResult.update_count, 0);
		assertEquals((int) batchSubscribeResult.error_count, 0);

		ListMemberInfoResult memberInfoResult = listMemberInfo(3, 5);
		assertEquals((int) memberInfoResult.success, 4);
		assertEquals((int) memberInfoResult.errors, 1);

		ListBatchUnsubscribeResult batchUnsubscribeResult = listUnsubscribeBatch(4, 2, false);
		assertEquals((int) batchUnsubscribeResult.success_count, 2);
		assertEquals((int) batchUnsubscribeResult.error_count, 0);

		ListMembersResult membersResult = listMembers(MemberStatus.subscribed);
		assertEquals((int) membersResult.total, 3);

		membersResult = listMembers(MemberStatus.unsubscribed);
		assertEquals((int) membersResult.total, 2);
	}

	@Test
	public void testExecute_ListSingle() throws Exception {
		ListSubscribeMethod listSubscribe = new ListSubscribeMethod();
		listSubscribe.apikey = apiKey;
		listSubscribe.id = listId;
		listSubscribe.email_address = email(0);
		listSubscribe.double_optin = false;
		listSubscribe.update_existing = false;
		listSubscribe.merge_vars = new MergeVars(null, "Vasya", "Pupkin");
		assertTrue(client.execute(listSubscribe));

		try {
			client.execute(listSubscribe);
			fail();
		} catch(MailChimpException e) {
			// aready registered
			assertEquals(e.code, 214);
		}

		ListMemberInfoResult listMemberInfoResult = listMemberInfo(0, 1);
		assertEquals(listMemberInfoResult.data.get(0).email, email(0));
		assertEquals(listMemberInfoResult.data.get(0).status, MemberStatus.subscribed);
		assertEquals(listMemberInfoResult.data.get(0).merges.get("FNAME"), "Vasya");
		assertEquals(listMemberInfoResult.data.get(0).merges.get("LNAME"), "Pupkin");

		ListUpdateMemberMethod listUpdateMember = new ListUpdateMemberMethod();
		listUpdateMember.apikey = apiKey;
		listUpdateMember.id = listId;
		listUpdateMember.email_address = email(0);
		listUpdateMember.merge_vars = new MergeVars(null, null, "Popkin");
		assertTrue(client.execute(listUpdateMember));

		ListUnsubscribeMethod listUnsubscribe = new ListUnsubscribeMethod();
		listUnsubscribe.apikey = apiKey;
		listUnsubscribe.id = listId;
		listUnsubscribe.email_address = email(0);
		listUnsubscribe.delete_member = false;
		assertTrue(client.execute(listUnsubscribe));

		listMemberInfoResult = listMemberInfo(0, 1);
		assertEquals(listMemberInfoResult.data.get(0).email, email(0));
		assertEquals(listMemberInfoResult.data.get(0).status, MemberStatus.unsubscribed);
		assertEquals(listMemberInfoResult.data.get(0).merges.get("FNAME"), "Vasya");
		assertEquals(listMemberInfoResult.data.get(0).merges.get("LNAME"), "Popkin");

		listUnsubscribe.delete_member = true;
		assertTrue(client.execute(listUnsubscribe));

		try {
			client.execute(listUnsubscribe);
			fail();
		} catch(MailChimpException e) {
			// not exist
			assertEquals(e.code, 232);
		}
	}

	private ListBatchUnsubscribeResult listUnsubscribeBatch(int from, int count, boolean delete_member) throws Exception {
		ListBatchUnsubscribeMethod request = new ListBatchUnsubscribeMethod();
		request.apikey = apiKey;
		request.id = listId;
		request.emails = emails(from, count);
		request.delete_member = delete_member;
		ListBatchUnsubscribeResult result = client.execute(request);
		log.info("Result: " + result);
		assertEquals(count, result.success_count + result.error_count);
		return result;
	}

	private ListBatchSubscribeResult listSubscribeBatch(int from, int count, boolean double_optin, boolean update_existing) throws Exception {
		ListBatchSubscribeMethod request = new ListBatchSubscribeMethod();
		request.apikey = apiKey;
		request.id = listId;
		request.double_optin = double_optin;
		request.update_existing = update_existing;

		List<MergeVars> batch = new ArrayList<MergeVars>();
		for(int i=from; i<from+count; i++) {
			batch.add(new MergeVars(email(i), "Batch"+i, "Subscribed"+i));
		}
		request.batch = batch;

		ListBatchSubscribeResult result = client.execute(request);
		log.info("Result: " + result);
		return result;
	}

	private ListMemberInfoResult listMemberInfo(int from, int count) throws Exception {
		ListMemberInfoMethod request = new ListMemberInfoMethod();
		request.apikey = apiKey;
		request.id = listId;
		request.email_address = emails(from, count);
		ListMemberInfoResult result = client.execute(request);
		log.info("Result: " + result);
		return result;
	}

	private ListMembersResult listMembers(MemberStatus status) throws Exception {
		ListMembersMethod request = new ListMembersMethod();
		request.apikey = apiKey;
		request.id = listId;
		request.status = status;
		ListMembersResult result = client.execute(request);
		log.info("Result: " + result);
		return result;
	}
}
