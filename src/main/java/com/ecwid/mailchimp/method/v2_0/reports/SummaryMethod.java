/*
 * Copyright 2014 Ecwid, Inc.
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
package com.ecwid.mailchimp.method.v2_0.reports;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.method.v2_0.campaign.CampaignRelatedMethod;

/**
 * Retrieve relevant aggregate campaign statistics (opens, bounces, clicks, etc.)
 * 
 * @author Benjamin Warncke
 */
@MailChimpMethod.Method(name = "reports/summary", version = MailChimpAPIVersion.v2_0)
public class SummaryMethod extends CampaignRelatedMethod<SummaryMethodResult> {

}
