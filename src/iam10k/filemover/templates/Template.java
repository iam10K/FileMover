package iam10k.filemover.templates;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by iam10k on 2/25/2016.
 * Github.com/iam10k
 */
public class Template implements Comparable {

    private Logger log;
    private int priority;
    private String moveToLocation;


    public Template(Logger logger) {
        this.log = logger;
    }

    public boolean init(File templateFile) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(templateFile);

            // Load a properties file
            prop.load(input);

            // Get the property value and set value
            priority = Integer.getInteger(prop.getProperty("priority", "1"), 1);
            moveToLocation = prop.getProperty("moveToLocation", templateFile.getParent());

        } catch (FileNotFoundException ex) {
            // Template file not found
            log.log(Level.WARNING, "Template file not found.");
            return false;
        } catch (IOException ex) {
            // Error loading input file
            log.log(Level.WARNING, "Template file threw IOException when loading properties.");
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        int otherPriority = ((Template) o).getPriority();

        return this.getPriority() - otherPriority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getMoveToLocation() {
        return moveToLocation;
    }

    public void setMoveToLocation(String moveToLocation) {
        this.moveToLocation = moveToLocation;
    }
}
