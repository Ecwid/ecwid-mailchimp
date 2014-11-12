package com.ecwid.mailchimp.method.v2_0.reports;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;

@MailChimpMethod.Method(name = "reports/opened", version = MailChimpAPIVersion.v2_0)
public class OpenedMethod extends MailChimpMethod<OpenedMethodResult> {

	@Field
	public String cid;

	@Field
	public Opts opts;

	/**
	 * Options to apply to this query - all are optional:
	 */
	public static class Opts extends MailChimpObject {

		@Field
		public Integer start;

		@Field
		public Integer limit;

		@Field
		public String sort_field;

		@Field
		public String sort_dir;
	}
}
