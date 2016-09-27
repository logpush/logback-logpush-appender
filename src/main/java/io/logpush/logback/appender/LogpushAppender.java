package io.logpush.logback.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

public class LogpushAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private String token;
    private LayoutWrappingEncoder<ILoggingEvent> encoder;

    @Override
    public void start() {
        if (!checkProperty()) {
            addError("No set token [" + name + "].");

            return;
        }

        if (encoder == null) {
            addError("No encoder set for the appender named [" + name + "].");

            return;
        }

        super.start();
    }

    private boolean checkProperty() {
        return token != null && token.length() != 0;
    }

    @Override
    protected void append(ILoggingEvent event) {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LayoutWrappingEncoder<ILoggingEvent> getEncoder() {
        return encoder;
    }

    public void setEncoder(LayoutWrappingEncoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }
}
