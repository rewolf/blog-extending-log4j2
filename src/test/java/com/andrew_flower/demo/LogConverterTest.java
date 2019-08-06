package com.andrew_flower.demo;


import com.andrew_flower.demo.logging.RequestIdConverter;
import com.andrew_flower.demo.util.StringAppender;
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
    private final static String STUB_REQUEST_ID = "Haro-I-Id";

    @Before
    public void setup() {
        HypotheticalApplicationRequestHandler stub = new HypotheticalApplicationRequestHandler() {
            @Override
            public String getCurrentRequestId() {
                return STUB_REQUEST_ID;
            }
        };
        HypotheticalApplicationRequestHandler.setInstance(stub);
    }

    @Test
    public void formatAppendsRequestId() {
        RequestIdConverter converter = RequestIdConverter.newInstance(new String[]{});
        StringBuilder message = new StringBuilder().append("BEFORE_");

        converter.format(null, message);

        assertEquals("BEFORE_" + STUB_REQUEST_ID, message.toString());
    }

    @Test
    public void logReplacesKeyWithRequestId() {
        // Get the RootLogger which, if you don't have log4j2-test.xml defined, will only log ERRORs
        Logger logger = LogManager.getRootLogger();
        // Create a String Appender to capture log output
        StringAppender appender = StringAppender.createStringAppender("[%requestId] %m");
        appender.addToLogger(logger.getName(), Level.INFO);
        appender.start();

        // Log to the string appender
        logger.error("Test");

        assertEquals("[" + STUB_REQUEST_ID + "] Test", appender.getOutput());
        appender.removeFromLogger(LogManager.getRootLogger().getName());
    }
}
