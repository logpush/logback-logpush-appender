package io.logpush;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogpushAppenderTest {
    private static final Logger LOG = LoggerFactory.getLogger(LogpushAppenderTest.class);

    @Test
    public void testLogpush() {
        String abc = null;

        for (int i = 0; i < 3; i++) {
            try {
                if (abc.length() == 0) {
                    LOG.info("No String");
                }
            } catch (Exception e) {
                LOG.error("Caught Exception: ", e);
                LOG.info("You can't see me.");
            }
        }
    }
}