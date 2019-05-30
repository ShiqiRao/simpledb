package com.dzion.simpledb;

import java.io.File;
import java.io.IOException;

/**
 * Database is a class that initializes several static
 * variables used by the database system (the catalog, the buffer pool,
 * and the log files, in particular.)
 * <p>
 * Provides a set of methods that can be used to access these variables
 * from anywhere.
 * <p>
 * Database是一个初始化数据库系统使用的几个静态变量的类（特别是目录，缓冲池和日志文件）。
 * <p>
 * 提供一组可用于从任何位置访问这些变量的方法。
 */

public class Database {
    private static Database instance = new Database();
    private final Catalog catalog;
    private BufferPool bufferpool;

    private final static String LOGFILENAME = "log";
    private LogFile logfile;

    private Database() {
        catalog = new Catalog();
        bufferpool = new BufferPool(BufferPool.DEFAULT_PAGES);
        try {
            logfile = new LogFile(new File(LOGFILENAME));
        } catch (IOException e) {
            logfile = null;
            e.printStackTrace();
            System.exit(1);
        }
        // startControllerThread();
    }

    /**
     * Return the log file of the static Database instance
     */
    public static LogFile getLogFile() {
        return instance.logfile;
    }

    /**
     * Return the buffer pool of the static Database instance
     */
    public static BufferPool getBufferPool() {
        return instance.bufferpool;
    }

    /**
     * Return the catalog of the static Database instance
     */
    public static Catalog getCatalog() {
        return instance.catalog;
    }

    /**
     * Method used for testing -- create a new instance of the
     * buffer pool and return it
     */
    public static BufferPool resetBufferPool(int pages) {
        instance.bufferpool = new BufferPool(pages);
        return instance.bufferpool;
    }

    //reset the database, used for unit tests only.
    public static void reset() {
        instance = new Database();
    }

}
