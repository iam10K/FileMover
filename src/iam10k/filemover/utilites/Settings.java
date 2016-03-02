package iam10k.filemover.utilites;

import iam10k.filemover.main.Main;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Created by iam10k on 2/25/2016.
 * Github.com/iam10k
 */
public class Settings {

    private long interval = 120;            // Interval to check folder for new files
    private String folder = "";             // Folder where files are dropped
    private boolean hiddenFiles = false;    // Global setting for checking hidden files
    private int loggingLevel = 1;           // Logging level.
                                            // 0 - off
                                            // 1 - important messages (default)
                                            // 2 - important and minor messages
                                            // 3 - all/debug


    public Settings() {

    }

    /**
     * Initialize all the variables.
     * @return true if successful, false if there is an invalid config or cannot create config.
     */
    public boolean init() {
        // Gets the path of the jar
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File parentFile = (new File(path)).getParentFile().getParentFile();
        folder = parentFile.getPath();

        // Load properties file
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(folder + File.separator + "config.properties");

            // Load a properties file
            prop.load(input);

            // Get the property value and set value
            interval = Integer.getInteger(prop.getProperty("updateInterval", "120"), 120);
            hiddenFiles = Boolean.getBoolean(prop.getProperty("hiddenFiles", "true"));
            loggingLevel = Integer.getInteger(prop.getProperty("loggingLevel", "1"), 1);

        } catch (IOException ex) {
            // No settings file, create first file
            return firstRunInit();
        }

        return true;
    }

    /**
     * Creates the initial first config file.
     */
    public boolean firstRunInit() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream(folder + File.separator + "config.properties");

            // set the properties value
            prop.setProperty("updateInterval", "120");
            prop.setProperty("hiddenFiles", "true");
            prop.setProperty("loggingLevel", "1");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            return false;
        }
        return true;
    }

    /**
     * Interval to check folder for new files
     * @return the interval in seconds
     */
    public long getInterval() {
        return interval;
    }

    /**
     * Folder where files are dropped
     * @return String of the folder location
     */
    public String getFolder() {
        return folder;
    }

    /**
     * Global setting for checking hidden files
     * @return true if hidden files should be checked
     */
    public boolean checkHiddenFiles() {
        return hiddenFiles;
    }

    /**
     * Logging level. 0 - off 1 - normal 2 - all
     * @return the logging level 0,1, or 2
     */
    public Level getLoggingLevel() {
        switch (loggingLevel) {
            case 0:                     // All Off
                return Level.OFF;
            case 1:                     // Level > 800
                return Level.INFO;
            case 2:                     // Level > 400
                return Level.FINER;
            case 3:                     // Level > 0
                return Level.ALL;
            default:
                return Level.INFO;
        }
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public void setHiddenFiles(boolean hiddenFiles) {
        this.hiddenFiles = hiddenFiles;
    }

    public void setLoggingLevel(int loggingLevel) {
        this.loggingLevel = loggingLevel;
    }
}
