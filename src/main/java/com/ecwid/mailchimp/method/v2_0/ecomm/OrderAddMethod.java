package com.ecwid.mailchimp.method.v2_0.ecomm;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.method.v2_0.lists.DummyResult;


/**
 * See:
 * <a href="http://apidocs.mailchimp.com/api/2.0/ecomm/order-add.php">
 *      http://apidocs.mailchimp.com/api/2.0/ecomm/order-add.php
 * </a>
 */
@MailChimpMethod.Method(name = "ecomm/order-add", version = MailChimpAPIVersion.v2_0)
public class OrderAddMethod extends MailChimpMethod<DummyResult> {

	@Field
	public OrderInfo order;
}
