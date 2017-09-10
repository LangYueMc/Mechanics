package com.jianyuan.mechanics.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.bukkit.plugin.Plugin;

public class Logger {
    private String pluginName;
    private org.apache.logging.log4j.Logger logger;
    private boolean debug;

    public Logger(Plugin plugin, boolean debug) {
        String prefix = plugin.getDescription().getPrefix();
        pluginName = prefix != null ? new StringBuilder().append("[").append(prefix).append("] ").toString()
                : "[" + plugin.getDescription().getName() + "] ";
        logger = LogManager.getLogger(prefix != null ? prefix : plugin.getDescription().getName());
        this.debug = debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * 获取日志输出（默认debug关闭）
     *
     * @param plugin
     * @return
     */
    public static Logger getLogger(Plugin plugin) {
        return getLogger(plugin, false);
    }

    /**
     * 获取日志输出（默认debug关闭）
     *
     * @param plugin
     * @param debug
     *            是否启用debug日志输出
     * @return
     */
    public static Logger getLogger(Plugin plugin, boolean debug) {
        return new Logger(plugin, debug);
    }

    public void log(String msg) {
        logger.log(Level.ALL, pluginName + msg);
    }

    public void log(Level level, String msg) {
        logger.log(level, pluginName + msg);
    }

    public void log(Level level, String msg, Throwable thrown) {
        logger.log(level, pluginName + msg, thrown);
    }

    /**
     * 使用{}作为占位符
     *
     * @param format
     * @param objects
     */
    public void log(Level level, String format, Object... objects) {
        logger.log(level, pluginName + format, objects);
    }

    /**
     * 使用{}作为占位符
     *
     * @param format
     * @param objects
     */
    public void log(Level level, String format, Throwable thrown, Object... objects) {
        logger.log(level, pluginName + format, thrown, objects);
    }

    public void info(String msg) {
        logger.log(Level.INFO, pluginName + msg);
    }

    public void info(String format, Object... objects) {
        logger.log(Level.INFO, pluginName + format, objects);
    }

    public void info(String msg, Throwable thrown) {
        this.log(Level.INFO, msg, thrown);
    }

    public void info(String msg, Throwable thrown, Object... objects) {
        this.log(Level.INFO, msg, thrown, objects);
    }

    public void debug(String msg) {
        if (debug)
            this.log(Level.INFO, String.format("§a[DEBUG] %s§r", msg));
    }

    public void debug(String msg, Object... objects) {
        if (debug)
            this.log(Level.INFO, String.format("§a[DEBUG] %s§r", msg), objects);
    }

    public void debug(String msg, Throwable thrown) {
        if (debug)
            this.log(Level.INFO, String.format("§a[DEBUG] %s§r", msg), thrown);
    }

    public void debug(String msg, Throwable thrown, Object... objects) {
        if (debug)
            this.log(Level.INFO, String.format("§a[DEBUG] %s§r", msg), thrown, objects);
    }

    public void warning(String msg) {
        this.log(Level.WARN, msg);
    }

    public void warning(String format, Object... objects) {
        this.log(Level.WARN, format, objects);
    }

    public void warning(String msg, Throwable thrown) {
        this.log(Level.WARN, msg, thrown);
    }

    public void warning(String msg, Throwable thrown, Object... objects) {
        this.log(Level.WARN, msg, thrown, objects);
    }

    public void error(String msg) {
        this.log(Level.ERROR, msg);
    }

    public void error(String format, Object... objects) {
        this.log(Level.ERROR, format, objects);
    }

    public void error(String msg, Throwable thrown) {
        this.log(Level.ERROR, msg, thrown);
    }

    public void error(String msg, Throwable thrown, Object... objects) {
        this.log(Level.ERROR, msg, thrown, objects);
    }
}
