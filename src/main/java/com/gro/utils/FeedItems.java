package com.gro.utils;

import com.mongodb.BasicDBObject;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: niqbal
 * Date: 7/13/11
 * Time: 8:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedItems {
    List<BasicDBObject> items;
    String nextPageUrl;

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<BasicDBObject> getItems() {
        return items;
    }

    public void setItems(List<BasicDBObject> items) {
        this.items = items;
    }
}