package in.devtools.taskmgrapp.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Central utility to get a channel-specific logger.
 *
 * Usage:
 *   private static final Logger log = ChannelLogger.get(LogChannel.JOBS);
 *   log.info("Job started: {}", jobName);
 */
public final class ChannelLogger {

    private static final Map<LogChannel, Logger> CACHE = new ConcurrentHashMap<>();

    private ChannelLogger() {}

    /**
     * Returns the SLF4J Logger bound to the given channel.
     * Loggers are cached so repeated calls are cheap.
     */
    public static Logger get(LogChannel channel) {
        return CACHE.computeIfAbsent(channel,
                ch -> LoggerFactory.getLogger(ch.getLoggerName()));
    }
}