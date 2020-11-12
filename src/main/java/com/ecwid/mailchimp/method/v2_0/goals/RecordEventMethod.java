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
package com.ecwid.mailchimp.method.v2_0.goals;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.method.v2_0.lists.Email;

@MailChimpMethod.Method(name="goal/record-event", version=MailChimpAPIVersion.v2_0)
public class RecordEventMethod extends MailChimpMethod<RecordEventResult> {
    
	@Field
	public String list_id;
	
	@Field
	public Email email;
	
	@Field
	public String campaign_id;
	
	@Field
	public String event;
	
}
