package com.ecwid.mailchimp.method.v2_0.reports;

import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.v2_0.lists.MemberInfoData;

import java.util.List;

/**
 * Result of the reports/opened operation
 */
public class OpenedMethodResult extends MailChimpObject {

	@Field
	public Integer total;

	@Field
	public List<Data> data;

	public static class Data extends MailChimpObject {

		@Field
		public Integer opens;

		@Field
		public MemberInfoData member;
	}
}
