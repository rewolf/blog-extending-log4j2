package com.andrew_flower.example;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author rewolf
 */
public class LogConverterTest {
    private final static String STUB_REQ_ID = "Haro-I-Id";

    @Before
    public void setup() {
        MythicalApplicationRequestHandler stub = new MythicalApplicationRequestHandler() {
            @Override
            public String getCurrentRequestId() {
                return STUB_REQ_ID;
            }
        };
        MythicalApplicationRequestHandler.setInstance(stub);
    }

    @Test
    public void formatAppendsRequestId() throws Exception {
        ReqIdConverter converter = ReqIdConverter.newInstance(new String[]{});
        StringBuilder message = new StringBuilder().append("BEFORE_");

        converter.format(null, message);

        assertEquals("BEFORE_" + STUB_REQ_ID, message.toString());
    }

    @Test
    public void logReplacesKeyWithReqId() throws Exception {
        // Get the RootLogger which, if you don't have log4j2-test.xml defined, will only log ERRORs
        Logger logger = LogManager.getRootLogger();
        // Create a String Appender to capture log output
        StringAppender appender = StringAppender.createStringAppender("[%reqId] %m");
        appender.addToLogger(logger.getName(), Level.INFO);
        appender.start();

        // Log to the string appender
        logger.error("Test");

        assertEquals("[" + STUB_REQ_ID + "] Test", appender.getOutput());
        appender.removeFromLogger(LogManager.getRootLogger().getName());
    }
}
