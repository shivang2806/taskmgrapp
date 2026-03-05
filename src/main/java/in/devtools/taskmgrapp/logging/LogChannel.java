package in.devtools.taskmgrapp.logging;

/**
 * Defines all available log channels.
 * Each enum maps to a named logger in logback-spring.xml
 * which routes to its own dedicated rolling log file.
 */
public enum LogChannel {

    CUSTOMER_ASSIGNMENT("channel.customer-assignment"),
    CUSTOMER_CREATED("channel.customer-created"),
    CUSTOMER_EXPORT("channel.customer-export"),
    JOBS("channel.jobs"),
    SCHEDULER("channel.scheduler"),
    WEBHOOK("channel.webhook"),
    API("channel.api"),
    SECURITY("channel.security");

    private final String loggerName;

    LogChannel(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLoggerName() {
        return loggerName;
    }
}