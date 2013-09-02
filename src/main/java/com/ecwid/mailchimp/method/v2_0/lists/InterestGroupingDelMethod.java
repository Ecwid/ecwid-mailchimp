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

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

/**
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
@MailChimpMethod.Method(name = "lists/interest-grouping-del", version = MailChimpAPIVersion.v2_0)
public class InterestGroupingDelMethod extends MailChimpMethod<DummyResult> {

	@Field
	public Integer grouping_id;

	@Override
	public Class<DummyResult> getResultType() {
		return DummyResult.class;
	}
}
