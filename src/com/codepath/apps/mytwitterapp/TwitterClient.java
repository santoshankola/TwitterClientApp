package com.codepath.apps.mytwitterapp;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "4pnDrbi4U4dsji1nxW3R6A";       // Change this
    public static final String REST_CONSUMER_SECRET = "lDfiLpiVooFHsKN6bgJ1qK32ssiEPD0kYZ6k5FrYQY"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterapp"; // Change this (here and in manifest)
    private static final String TWEET_COUNT="25";
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeLine(AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/home_timeline.json");
    	RequestParams params = new RequestParams();
        params.put("count", TWEET_COUNT);
    	client.get(url, params, handler);
    }
    
    public void postTweet(String status, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
        getClient().post(apiUrl, params, handler);
    }


}