package me.alpho320.fabulous.farm.util.logger;

import org.jetbrains.annotations.NotNull;

public interface FarmPluginLogger {

    @NotNull LoggingLevel getServerLoggingLevel();
    void setServerLoggingLevel(@NotNull LoggingLevel serverLoggingLevel);

    void log(@NotNull String str, @NotNull LoggingLevel level);
    void debug(@NotNull String str);
    void info(@NotNull String str);
    void warning(@NotNull String str);
    void severe(@NotNull String str);

    enum LoggingLevel {
        ERROR(0),
        WARNING(1),
        INFO(2),
        DEBUG(3);

        private final int numericVerbosity;

        LoggingLevel(int number) {
            this.numericVerbosity = number;
        }

        public int getNumericVerbosity() {
            return numericVerbosity;
        }

        public static @NotNull LoggingLevel fromNumber(int number) {
            for (LoggingLevel level : LoggingLevel.values()) {
                if (level.getNumericVerbosity() == number) {
                    return level;
                }
            }
            return LoggingLevel.INFO;
        }
    }

}