package com.andrew_flower.demo;

/**
 * Created on 15/2/20.
 *
 * @author rewolf
 */
public class HypotheticalApplicationRequestHandler {
    private static HypotheticalApplicationRequestHandler instance = null;

    public synchronized static HypotheticalApplicationRequestHandler getInstance() {
        if (instance == null) {
            instance = new HypotheticalApplicationRequestHandler();
        }
        return instance;
    }

    public synchronized static void setInstance(HypotheticalApplicationRequestHandler instance) {
        HypotheticalApplicationRequestHandler.instance = instance;
    }

    public String getCurrentRequestId() {
        return "abcd-efgh-1234-5678";
    }
}
