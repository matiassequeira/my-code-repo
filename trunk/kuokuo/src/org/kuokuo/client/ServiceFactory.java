/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client;

import org.kuokuo.client.service.SearchService;
import org.kuokuo.client.service.SearchServiceAsync;

import com.google.gwt.core.client.GWT;

/**
 * service factory
 * 
 * @version Nov 24, 2009 10:42:04 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class ServiceFactory
{
    public final static SearchServiceAsync SERVICE_SEARCH = GWT.create(SearchService.class);

}
