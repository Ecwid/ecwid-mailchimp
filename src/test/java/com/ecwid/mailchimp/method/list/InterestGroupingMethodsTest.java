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
			ListInterestGroupingDelMethod method = new ListInterestGroupingDelMethod();
			method.apikey = apiKey;
			method.grouping_id = g.id;
			assertTrue(client.execute(method));
		}

		ListInterestGroupingAddMethod method = new ListInterestGroupingAddMethod();
		method.apikey = apiKey;
		method.id = listId;
		method.name = "Food preferences";
		method.type = InterestGroupingType.checkboxes;
		method.groups = Arrays.asList("Vegetables", "Meat");
		foodId = client.execute(method);

		method.name = "Drink preferences";
		method.type = InterestGroupingType.dropdown;
		method.groups = Arrays.asList("Water", "Vodka");
		drinkId = client.execute(method);

		method.name = "Mobile device";
		method.type = InterestGroupingType.hidden;
		method.groups = Arrays.asList("Apple", "Samsung", "Motorolla");
		mobileId = client.execute(method);

		method.name = "TV preferences";
		method.type = InterestGroupingType.radio;
		method.groups = Arrays.asList("MTV", "Discovery Science");
		tvId = client.execute(method);
	}

	private static class Grouping extends MailChimpObject {
		@Field
		Integer id;

		@Field
		String name;

		@Field
		String groups;
	}

	private static class MergeVars extends MailChimpObject {
		@Field
		String EMAIL, FNAME, LNAME;

		@Field
		List<Grouping> GROUPINGS;
	}

	@Test
	public void testListGroupings() throws Exception {
		Iterator<InterestGrouping> iterator = listInterestGroupings().iterator();
		InterestGrouping g = iterator.next();
		assertEquals(g.id, foodId);
		assertEquals(g.form_field, InterestGroupingType.checkboxes);
		g = iterator.next();
		assertEquals(g.id, drinkId);
		assertEquals(g.form_field, InterestGroupingType.dropdown);
		g = iterator.next();
		assertEquals(g.id, mobileId);
		assertEquals(g.form_field, InterestGroupingType.hidden);
		g = iterator.next();
		assertEquals(g.id, tvId);
		assertEquals(g.form_field, InterestGroupingType.radio);
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testSubscribe() throws Exception {
		ListSubscribeMethod subscribeMethod = new ListSubscribeMethod();
		subscribeMethod.apikey = apiKey;
		subscribeMethod.id = listId;
		subscribeMethod.email_address = email(33);
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
		grouping.groups = "Vegetables, Meat";
		mv.GROUPINGS.add(grouping);
		grouping = new Grouping();
		grouping.id = tvId;
		grouping.name = null;
		grouping.groups = "Discovery Science";
		mv.GROUPINGS.add(grouping);
		grouping = new Grouping();
		grouping.id = null;
		grouping.name = "Mobile device";
		grouping.groups = "Samsung";
		mv.GROUPINGS.add(grouping);
		subscribeMethod.merge_vars = mv;

		assertTrue(client.execute(subscribeMethod));

		ListMemberInfoMethod infoMethod = new ListMemberInfoMethod();
		infoMethod.apikey = apiKey;
		infoMethod.id = listId;
		infoMethod.email_address = Arrays.asList(subscribeMethod.email_address);
		ListMemberInfoResult infoResult = client.execute(infoMethod);
		assertEquals(infoResult.data.size(), 1);

		mv = infoResult.data.get(0).merges.as(MergeVars.class);
		assertEquals(mv.EMAIL, email(33));
		assertEquals(mv.FNAME, "Vasya");
		assertEquals(mv.LNAME, "Pupkin");
		
		Iterator<Grouping> iterator = mv.GROUPINGS.iterator();
		grouping = iterator.next();
		assertEquals(grouping.id, foodId);
		assertEquals(grouping.groups, "Vegetables, Meat");
		grouping = iterator.next();
		assertEquals(grouping.id, drinkId);
		grouping = iterator.next();
		assertEquals(grouping.id, mobileId);
		assertEquals(grouping.groups, "Samsung");
		grouping = iterator.next();
		assertEquals(grouping.id, tvId);
		assertEquals(grouping.groups, "Discovery Science");
		assertFalse(iterator.hasNext());
	}

	private List<InterestGrouping> listInterestGroupings() throws Exception {
		ListInterestGroupingsMethod method = new ListInterestGroupingsMethod();
		method.apikey = apiKey;
		method.id = listId;
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
}
