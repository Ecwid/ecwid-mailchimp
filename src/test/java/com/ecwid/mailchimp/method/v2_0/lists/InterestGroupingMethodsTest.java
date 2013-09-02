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
package com.ecwid.mailchimp.method.v2_0.lists;

import com.ecwid.mailchimp.MailChimpException;
import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.AbstractMethodTestCase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class InterestGroupingMethodsTest extends AbstractMethodTestCase {
	private static final Logger log = Logger.getLogger(InterestGroupingMethodsTest.class.getName());

	private final String apiKey;
	private final String listId;
	private Integer foodId, drinkId, mobileId, tvId;

	@Parameters({"mailchimp.test.apikey", "mailchimp.test.listid"})
	public InterestGroupingMethodsTest(String apiKey, String listId) {
		this.apiKey = apiKey;
		this.listId = listId;
	}

	@BeforeClass
	private void cleanupAndAdd() throws Exception {
		// cleanup
		for(InterestGrouping g: listInterestGroupings()) {
			InterestGroupingDelMethod method = new InterestGroupingDelMethod();
			method.apikey = apiKey;
			method.grouping_id = g.id;
			assertTrue(client.execute(method).complete);
		}

		InterestGroupingAddMethod method = new InterestGroupingAddMethod();
		method.apikey = apiKey;
		method.id = listId;
		method.name = "Food preferences";
		method.type = "checkboxes";
		method.groups = Arrays.asList("Vegetables", "Meat");
		foodId = client.execute(method).id;

		method.name = "Drink preferences";
		method.type = "dropdown";
		method.groups = Arrays.asList("Water", "Vodka");
		drinkId = client.execute(method).id;

		method.name = "Mobile device";
		method.type = "hidden";
		method.groups = Arrays.asList("Apple", "Samsung", "Motorolla");
		mobileId = client.execute(method).id;

		method.name = "TV preferences";
		method.type = "radio";
		method.groups = Arrays.asList("MTV", "Discovery Science");
		tvId = client.execute(method).id;
	}

	private static class Grouping extends MailChimpObject {
		@MailChimpObject.Field
		Integer id;

		@MailChimpObject.Field
		String name;

		@MailChimpObject.Field
		List<String> groups;
	}

	private static class MergeVars extends MailChimpObject {
		@Field
		String EMAIL, FNAME, LNAME;
		
		@MailChimpObject.Field
		List<Grouping> GROUPINGS;
	}

	@Test
	public void testListGroupings() throws Exception {
		Iterator<InterestGrouping> iterator = listInterestGroupings().iterator();
		InterestGrouping g = iterator.next();
		assertEquals(g.id, foodId);
		assertEquals(g.form_field, "checkboxes");
		g = iterator.next();
		assertEquals(g.id, drinkId);
		assertEquals(g.form_field, "dropdown");
		g = iterator.next();
		assertEquals(g.id, mobileId);
		assertEquals(g.form_field, "hidden");
		g = iterator.next();
		assertEquals(g.id, tvId);
		assertEquals(g.form_field, "radio");
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testSubscribe() throws Exception {
		SubscribeMethod subscribeMethod = new SubscribeMethod();
		subscribeMethod.apikey = apiKey;
		subscribeMethod.id = listId;
		subscribeMethod.email = email_v2_0(33);
		subscribeMethod.double_optin = false;
		subscribeMethod.send_welcome = false;
		subscribeMethod.update_existing = true;
		subscribeMethod.replace_interests = true;

		MergeVars mv = new MergeVars();
		mv.FNAME = "Vasya";
		mv.LNAME = "Pupkin";
		mv.GROUPINGS = new ArrayList<Grouping>();
		Grouping grouping = new Grouping();
		grouping.id = foodId;
		grouping.name = null;
		grouping.groups = Arrays.asList("Vegetables", "Meat");
		mv.GROUPINGS.add(grouping);
		grouping = new Grouping();
		grouping.id = tvId;
		grouping.name = null;
		grouping.groups = Arrays.asList("Discovery Science");
		mv.GROUPINGS.add(grouping);
		grouping = new Grouping();
		grouping.id = null;
		grouping.name = "Mobile device";
		grouping.groups = Arrays.asList("Samsung");
		mv.GROUPINGS.add(grouping);
		subscribeMethod.merge_vars = mv;

		assertEquals(client.execute(subscribeMethod).email, email(33));

		MemberInfoMethod infoMethod = new MemberInfoMethod();
		infoMethod.apikey = apiKey;
		infoMethod.id = listId;
		infoMethod.emails = Arrays.asList(subscribeMethod.email);
		MemberInfoResult infoResult = client.execute(infoMethod);
		assertEquals((int) infoResult.success_count, 1);
		assertEquals((int) infoResult.error_count, 0);
		assertEquals(infoResult.data.size(), 1);

		MemberInfoMerges merges = infoResult.data.get(0).merges;
		assertEquals(merges.get("EMAIL"), email(33));
		assertEquals(merges.get("FNAME"), "Vasya");
		assertEquals(merges.get("LNAME"), "Pupkin");
		
		Iterator<MemberInfoGrouping> iterator = merges.GROUPINGS.iterator();
		MemberInfoGrouping mig = iterator.next();
		assertEquals(mig.id, foodId);
		assertEquals(interestedGroups(mig.groups), Arrays.asList("Vegetables", "Meat"));
		mig = iterator.next();
		assertEquals(mig.id, drinkId);
		mig = iterator.next();
		assertEquals(mig.id, mobileId);
		assertEquals(interestedGroups(mig.groups), Arrays.asList("Samsung"));
		mig = iterator.next();
		assertEquals(mig.id, tvId);
		assertEquals(interestedGroups(mig.groups), Arrays.asList("Discovery Science"));
		assertFalse(iterator.hasNext());
	}
	
	private List<InterestGrouping> listInterestGroupings() throws Exception {
		InterestGroupingsMethod method = new InterestGroupingsMethod();
		method.apikey = apiKey;
		method.id = listId;
		method.counts = true;
		List<InterestGrouping> result = new ArrayList<InterestGrouping>();
		try {
			result.addAll(client.execute(method));
		} catch(MailChimpException e) {
			if("This list does not have interest groups enabled".equals(e.message)) {
				// ignore it
			} else {
				throw e;
			}
		}
		return result;
	}
	
	private List<String> interestedGroups(List<MemberInfoGroup> groups) {
		List<String> result = new ArrayList<String>();
		for (MemberInfoGroup group: groups) {
			if (group.interested) {
				result.add(group.name);
			}
		}
		return result;
	}
}
