package io.logpush.logback.appender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LogpushAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/json;charset=utf-8");
    private static final boolean DEFAULT_ONLY_ERROR = true;
    private static final Gson GSON = new Gson();

    private String token;
    private boolean onlyError = DEFAULT_ONLY_ERROR;
    private LayoutWrappingEncoder<ILoggingEvent> encoder;
    private OkHttpClient client;

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

        try {
            encoder.init(System.out);

            client = new OkHttpClient();
        } catch (Exception e) {
            addError("Exception", e);
        }

        super.start();
    }

    private boolean checkProperty() {
        return token != null && token.length() != 0;
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (event.getLevel() != Level.ERROR && onlyError) {
            return;
        }

        sendToLogpush(event);
    }

    private void sendToLogpush(ILoggingEvent event) {
        String msg = encoder.getLayout().doLayout(event);

        LogpushBody logpushBody = new LogpushBody();

        logpushBody.message = msg;
        logpushBody.level = event.getLevel().toString();

        RequestBody body = RequestBody.create(CONTENT_TYPE, GSON.toJson(logpushBody, LogpushBody.class));

        Request req = new Request.Builder().url("http://logpush.io/api/v1/logs?app_token=" + token).post(body).build();

        try {
            client.newCall(req).execute();
        } catch (Exception e) {
            addError("Exception", e);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isOnlyError() {
        return onlyError;
    }

    public void setOnlyError(boolean onlyError) {
        this.onlyError = onlyError;
    }

    public LayoutWrappingEncoder<ILoggingEvent> getEncoder() {
        return encoder;
    }

    public void setEncoder(LayoutWrappingEncoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }

    private class LogpushBody {
        private String level;
        private String message;
    }
}
