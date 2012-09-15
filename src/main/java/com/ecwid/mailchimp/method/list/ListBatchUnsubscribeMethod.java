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

import com.ecwid.mailchimp.MailChimpMethod;
import java.util.List;

/**
 * See http://apidocs.mailchimp.com/api/1.3/listbatchunsubscribe.func.php
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
@MailChimpMethod.Name("listBatchUnsubscribe")
public class ListBatchUnsubscribeMethod extends HasListIdMethod<ListBatchUnsubscribeResult> {

	@Field
	public List<String> emails;
	
	@Field
	public Boolean delete_member;
	
	@Field
	public Boolean send_goodbye;
	
	@Field
	public Boolean send_notify;

	@Override
	public Class<ListBatchUnsubscribeResult> getResultType() {
		return ListBatchUnsubscribeResult.class;
	}
}
