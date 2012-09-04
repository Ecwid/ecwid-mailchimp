/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp;

import com.ecwid.mailchimp.annotation.MailChimpField;
import com.ecwid.mailchimp.method.ListBatchSubscribe;
import com.ecwid.mailchimp.method.ListBatchSubscribeResult;
import com.ecwid.mailchimp.method.ListBatchUnsubscribe;
import com.ecwid.mailchimp.method.ListBatchUnsubscribeResult;
import com.ecwid.mailchimp.method.ListMemberInfo;
import com.ecwid.mailchimp.method.ListMemberInfoResult;
import com.ecwid.mailchimp.method.ListMembers;
import com.ecwid.mailchimp.method.ListMembersResult;
import com.ecwid.mailchimp.method.ListSubscribe;
import com.ecwid.mailchimp.method.ListUnsubscribe;
import com.ecwid.mailchimp.method.ListUpdateMember;
import com.ecwid.mailchimp.method.MergeVars;
import com.ecwid.mailchimp.method.MemberStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpClientTest {
	private static final Logger log = Logger.getLogger(MailChimpClientTest.class.getName());

	/**
	 * Max number of emails used in tests.
	 */
	private static final int MAX = 50;
	
	private static String API_KEY;
	private static String LIST_ID;
	
	/**
	 * Username of a gmail account to be used for testing.
	 */
	private static String GMAIL_USERNAME;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		Properties p = new Properties();
		p.load(MailChimpClientTest.class.getResourceAsStream("mailchimp.test.properties"));
		API_KEY = p.getProperty("mailchimp.test.apikey");
		LIST_ID = p.getProperty("mailchimp.test.listid");
		GMAIL_USERNAME = p.getProperty("mailchimp.test.gmail_username");
	}
	
	private MailChimpClient client;
	
	@Before
	public void setUp() throws Exception {
		client = new MailChimpClient();
		listUnsubscribeBatch(0, MAX, true); // clear everything
	}
	
	@After
	public void tearDown() {
		client.close();
	}
	
	class MyMergeVars extends MergeVars {
		@MailChimpField
		public String FNAME;

		@MailChimpField
		public String LNAME;
	}
	
	@Test
	public void testExecute_ListBatch() throws Exception {
		ListBatchSubscribeResult batchSubscribeResult = listSubscribeBatch(4, 5, false, false);
		assertEquals(5, (int) batchSubscribeResult.add_count);
		assertEquals(0, (int) batchSubscribeResult.update_count);
		assertEquals(0, (int) batchSubscribeResult.error_count);
		
		ListMemberInfoResult memberInfoResult = listMemberInfo(3, 5);
		assertEquals(4, (int) memberInfoResult.success);
		assertEquals(1, (int) memberInfoResult.errors);
		
		ListBatchUnsubscribeResult batchUnsubscribeResult = listUnsubscribeBatch(4, 2, false);
		assertEquals(2, (int) batchUnsubscribeResult.success_count);
		assertEquals(0, (int) batchUnsubscribeResult.error_count);
		
		ListMembersResult membersResult = listMembers(MemberStatus.subscribed);
		assertEquals(3, (int) membersResult.total);
		
		membersResult = listMembers(MemberStatus.unsubscribed);
		assertEquals(2, (int) membersResult.total);
	}
	
	@Test
	public void testExecute_ListSingle() throws Exception {
		ListSubscribe listSubscribe = new ListSubscribe();
		listSubscribe.apikey = API_KEY;
		listSubscribe.id = LIST_ID;
		listSubscribe.email_address = email(0);
		listSubscribe.double_optin = false;
		listSubscribe.update_existing = false;
		
		MyMergeVars mv = new MyMergeVars();
		listSubscribe.merge_vars = mv;
		mv.FNAME = "Vasya";
		mv.LNAME = "Pupkin";
		listSubscribe.merge_vars = mv;
		
		assertTrue(client.execute(listSubscribe));
		
		try {
			client.execute(listSubscribe);
			fail();
		} catch(MailChimpException e) {
			// aready registered
			assertEquals(214, e.code);
		}
		
		ListMemberInfoResult listMemberInfoResult = listMemberInfo(0, 1);
		assertEquals(email(0), listMemberInfoResult.data.get(0).email);
		assertEquals(MemberStatus.subscribed, listMemberInfoResult.data.get(0).status);
		assertEquals("Vasya", listMemberInfoResult.data.get(0).merges.get("FNAME"));
		assertEquals("Pupkin", listMemberInfoResult.data.get(0).merges.get("LNAME"));
		
		ListUpdateMember listUpdateMember = new ListUpdateMember();
		listUpdateMember.apikey = API_KEY;
		listUpdateMember.id = LIST_ID;
		listUpdateMember.email_address = email(0);
		mv = new MyMergeVars();
		mv.LNAME = "Popkin";
		listUpdateMember.merge_vars = mv;
		assertTrue(client.execute(listUpdateMember));
		
		ListUnsubscribe listUnsubscribe = new ListUnsubscribe();
		listUnsubscribe.apikey = API_KEY;
		listUnsubscribe.id = LIST_ID;
		listUnsubscribe.email_address = email(0);
		listUnsubscribe.delete_member = false;
		assertTrue(client.execute(listUnsubscribe));
		
		listMemberInfoResult = listMemberInfo(0, 1);
		assertEquals(email(0), listMemberInfoResult.data.get(0).email);
		assertEquals(MemberStatus.unsubscribed, listMemberInfoResult.data.get(0).status);
		assertEquals("Vasya", listMemberInfoResult.data.get(0).merges.get("FNAME"));
		assertEquals("Popkin", listMemberInfoResult.data.get(0).merges.get("LNAME"));
	
		listUnsubscribe.delete_member = true;
		assertTrue(client.execute(listUnsubscribe));
		
		try {
			client.execute(listUnsubscribe);
			fail();
		} catch(MailChimpException e) {
			// not exist
			assertEquals(232, e.code);
		}
	}
	
	private ListBatchUnsubscribeResult listUnsubscribeBatch(int from, int count, boolean delete_member) throws Exception {
		ListBatchUnsubscribe request = new ListBatchUnsubscribe();
		request.apikey = API_KEY;
		request.id = LIST_ID;
		request.emails = emails(from, count);
		request.delete_member = delete_member;
		ListBatchUnsubscribeResult result = client.execute(request);
		log.info("Result: " + result);
		assertEquals(count, result.success_count + result.error_count);
		return result;
	}
	
	private ListBatchSubscribeResult listSubscribeBatch(int from, int count, boolean double_optin, boolean update_existing) throws Exception {
		ListBatchSubscribe request = new ListBatchSubscribe();
		request.apikey = API_KEY;
		request.id = LIST_ID;
		request.batch = new ArrayList<MergeVars>();
		request.double_optin = double_optin;
		request.update_existing = update_existing;
		
		for(int i=from; i<from+count; i++) {
			MyMergeVars mv = new MyMergeVars();
			mv.EMAIL = email(i);
			mv.FNAME = "Batch"+i;
			mv.LNAME = "Subscribed"+i;
			request.batch.add(mv);
		}
		
		ListBatchSubscribeResult result = client.execute(request);
		log.info("Result: " + result);
		return result;
	}
	
	private ListMemberInfoResult listMemberInfo(int from, int count) throws Exception {
		ListMemberInfo request = new ListMemberInfo();
		request.apikey = API_KEY;
		request.id = LIST_ID;
		request.email_address = emails(from, count);
		ListMemberInfoResult result = client.execute(request);
		log.info("Result: " + result);
		return result;
	}
	
	private ListMembersResult listMembers(MemberStatus status) throws Exception {
		ListMembers request = new ListMembers();
		request.apikey = API_KEY;
		request.id = LIST_ID;
		request.status = status;
		ListMembersResult result = client.execute(request);
		log.info("Result: " + result);
		return result;
	}
	
	private List<String> emails(int from, int count) {
		List<String> result = new ArrayList<String>();
		for(int i=from; i<from+count; i++) {
			result.add(email(i));
		}
		return result;
	}
	
	private String email(int i) {
		assertTrue(""+i, i >= 0 && i < MAX);
		return GMAIL_USERNAME + "+"+i+"@gmail.com";
	}
}
