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

import com.ecwid.mailchimp.MailChimpObject;
import java.util.List;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MergeVarInfo extends MailChimpObject {

	/**
	 * Not used in {@link ListMergeVarAddMethod} requests.
	 */
	@Field
	public String tag;

	/**
	 * Not used in {@link ListMergeVarAddMethod} requests.
	 */
	@Field
	public String name;

	@Field
	public Boolean req;

	@Field
	public MergeVarType field_type;

	@Field(name="public")
	public Boolean public_;

	@Field
	public Boolean show;

	@Field
	public Integer order;

	@Field(name="default")
	public String default_;

	@Field
	public Integer size;

	@Field
	public List<String> choices;

	/**
	 * Not set in responses.
	 */
	@Field
	public String dateformat;

	/**
	 * Not set in responses.
	 */
	@Field
	public String phoneformat;

	/**
	 * Not set in responses.
	 */
	@Field
	public String defaultcountry;
}
