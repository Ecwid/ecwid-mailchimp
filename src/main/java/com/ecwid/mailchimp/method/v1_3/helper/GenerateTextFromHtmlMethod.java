package com.ecwid.mailchimp.method.v1_3.helper;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

/**
 * See:
 * <a href="http://apidocs.mailchimp.com/api/1.3/generatetext.func.php">
 *      http://apidocs.mailchimp.com/api/1.3/generatetext.func.php
 * </a>
 * this class implementens just the <code>html</code> type
 *
 * @author Massimo Lusetti <mlusetti@gmail.com>
 *
 */
@MailChimpMethod.Method(name = "generateText", version = MailChimpAPIVersion.v1_3)
public class GenerateTextFromHtmlMethod extends MailChimpMethod<String>
{
    @Field
    public String type;
    @Field
    public String content;
}
