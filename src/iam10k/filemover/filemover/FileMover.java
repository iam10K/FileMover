package iam10k.filemover.filemover;

import iam10k.filemover.utilites.Settings;
import iam10k.filemover.templates.Template;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by iam10k on 2/25/2016.
 * Github.com/iam10k
 */
public class FileMover {

    private Settings settings = new Settings();
    private Logger log = Logger.getLogger(FileMover.class.getName());

    private ArrayList<Template> templates = new ArrayList<>();

    public FileMover() {

    }

    /**
     * Initialize all the variables. Logs any errors
     * @return true if successful, false if failed.
     */
    public boolean init() {
        // Check if log is null if yes return false
        if (log == null) {
            return false;
        }
        // Add size limit to log file.
        try {
            FileHandler fileHandler = new FileHandler("fileMover.log", 2000000, 1);
            log.addHandler(fileHandler);
        } catch (IOException ex) {
            log.warning("Size limit failed to initialize.");
        }

        // Initialize settings
        if (!settings.init()) {
            return false;
        }

        // Set level of logging
        log.setLevel(settings.getLoggingLevel());

        // Initialize templates
        initTemplates();

        return true;
    }

    /**
     *
     */
    private void initTemplates() {
        File templatesFolder = new File(settings.getFolder() + File.separator + "templates");
        // If folder does not exist create it.
        if (!templatesFolder.exists()) {
            templatesFolder.mkdir();
            log.log(Level.FINEST, "Created templates folder.");
        }

        if (!templatesFolder.isDirectory()) {

        }

        for (File templateFile : templatesFolder.listFiles()) {
            Template template = new Template(log);
            if (template.init(templateFile)) {
                templates.add(template);
                log.log(Level.FINEST, "Added template.");
            }
        }

        if (templates.isEmpty()) {
            log.log(Level.INFO, "No templates found. First run? If not report the issue.");
        } else {
            Collections.sort(templates);
            log.log(Level.FINEST, "Templates sorted.");
        }
    }

    /**
     *
     */
    public void run() {

    }

    public Settings getSettings() {
        return settings;
    }

    public Logger getLog() {
        return log;
    }

    public ArrayList<Template> getTemplates() {
        return templates;
    }
}
