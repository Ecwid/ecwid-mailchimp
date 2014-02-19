package com.ecwid.mailchimp.method.v2_0.campaign;

import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;

/**
 * Base class for every campaign related
 */
public abstract class CampaignRelatedMethod<R> extends MailChimpMethod<R>
{
    @MailChimpObject.Field
    public String cid;

}