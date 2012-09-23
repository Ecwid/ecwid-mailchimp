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

import com.ecwid.mailchimp.method.AbstractMethodTestCase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MergeVarsTest extends AbstractMethodTestCase {

	private static final Set<String> predefined = new HashSet<String>(Arrays.asList("EMAIL", "FNAME", "LNAME"));

	private final String apiKey;
	private final String listId;

	@Parameters({"mailchimp.test.apikey", "mailchimp.test.listid"})
	public MergeVarsTest(String apiKey, String listId) {
		this.apiKey = apiKey;
		this.listId = listId;
	}

	@BeforeMethod
	private void cleanup() throws Exception {
		for(MergeVarInfo mvi: listMergeVars()) {
			if(!predefined.contains(mvi.tag)) {
				ListMergeVarDelMethod delMethod = new ListMergeVarDelMethod();
				delMethod.apikey = apiKey;
				delMethod.id = listId;
				delMethod.tag = mvi.tag;
				assertTrue(client.execute(delMethod));
			}
		}
	}

	@Test
	public void test() throws Exception {
		ListMergeVarAddMethod addMethod = new ListMergeVarAddMethod();
		addMethod.apikey = apiKey;
		addMethod.id = listId;
		addMethod.name = "Your pet";
		addMethod.tag = "PET";
		addMethod.options = new MergeVarInfo();
		addMethod.options.field_type = MergeVarType.radio;
		addMethod.options.choices = Arrays.asList("Dog", "Cat");
		addMethod.options.default_ = "Cat";
		addMethod.options.defaultcountry = "RU";
		client.execute(addMethod);

		Iterator<MergeVarInfo> iterator = listMergeVars().iterator();
		MergeVarInfo mvi = iterator.next();
		assertEquals(mvi.tag, "EMAIL");
		assertEquals(mvi.field_type, MergeVarType.email);
		mvi = iterator.next();
		assertEquals(mvi.tag, "FNAME");
		assertEquals(mvi.field_type, MergeVarType.text);
		mvi = iterator.next();
		assertEquals(mvi.tag, "LNAME");
		assertEquals(mvi.field_type, MergeVarType.text);
		mvi = iterator.next();
		assertEquals(mvi.tag, "PET");
		assertEquals(mvi.name, "Your pet");
		assertEquals(mvi.field_type, MergeVarType.radio);
		assertEquals(mvi.choices, Arrays.asList("Dog", "Cat"));
		assertEquals(mvi.default_, "Cat");
		assertFalse(iterator.hasNext());

		ListMergeVarUpdateMethod updateMethod = new ListMergeVarUpdateMethod();
		updateMethod.apikey = apiKey;
		updateMethod.id = listId;
		updateMethod.tag = "PET";
		updateMethod.options = new MergeVarInfo();
		updateMethod.options.tag = "PARENTSPET";
		updateMethod.options.name = "Your parents' pet";
		updateMethod.options.default_ = "Dog";
		assertTrue(client.execute(updateMethod));
		
		iterator = listMergeVars().iterator();
		iterator.next();
		iterator.next();
		iterator.next();
		mvi = iterator.next();
		assertEquals(mvi.tag, "PARENTSPET");
		assertEquals(mvi.name, "Your parents' pet");
		assertEquals(mvi.field_type, MergeVarType.radio);
		assertEquals(mvi.choices, Arrays.asList("Dog", "Cat"));
		assertEquals(mvi.default_, "Dog");
		assertFalse(iterator.hasNext());
	}

	private List<MergeVarInfo> listMergeVars() throws Exception {
		ListMergeVarsMethod method = new ListMergeVarsMethod();
		method.apikey = apiKey;
		method.id = listId;
		return client.execute(method);
	}
}
