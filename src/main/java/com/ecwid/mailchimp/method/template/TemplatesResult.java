/*
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
package com.ecwid.mailchimp.method.template;

import java.util.List;

import com.ecwid.mailchimp.MailChimpObject;

/**
 * 
 * @author Nick Matelli <nick14@gmail.com>
 */
public class TemplatesResult extends MailChimpObject {
	@Field
	public List<TemplateInformation> user;
	
	@Field
	public List<TemplateInformation> gallery;
	
	@Field
	public List<TemplateInformation> base;
	
}
