package com.andrew_flower.demo.logging;

import com.andrew_flower.demo.HypotheticalApplicationRequestHandler;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.*;

/**
 * Converter for Log strings that replaces the format token %requestId with current request Id from RequestHandler
 *
 * @author rewolf
 */
@Plugin(name = "RequestIdConverter", category = "Converter")
@ConverterKeys({"requestId"})
public class RequestIdConverter extends LogEventPatternConverter {

    private RequestIdConverter(final String name, final String style) {
        super(name, style);
    }

    public static RequestIdConverter newInstance(final String[] options) {
        return new RequestIdConverter("requestId", "requestId");
    }

    @Override
    public void format(final LogEvent event, final StringBuilder toAppendTo) {
        toAppendTo.append(getRequestId());
    }

    private String getRequestId() {
        String requestId = HypotheticalApplicationRequestHandler.getInstance().getCurrentRequestId();

        if (requestId == null) {
            requestId = "-";
        }

        return requestId;
    }
}