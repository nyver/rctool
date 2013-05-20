package com.nyver.rctool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Application properties class
 *
 * @author Yuri Novitsky
 */
public class AppSettings {

    private static final String SETTINGS_FILENAME = "rctool.properties";

    public static final String SETTING_WINDOW_START_WIDTH = "windowStartupWidth";
    public static final String SETTING_WINDOW_START_HEIGHT = "windowStartupHeight";

    private Properties settings = new Properties();

    public Properties load() throws IOException {
        FileInputStream in = new FileInputStream(SETTINGS_FILENAME);
        settings.load(in);
        return settings;
    }

    public String get(String name)
    {
        if (settings.containsKey(name)) {
            return settings.getProperty(name);
        }
        return null;
    }

    public void set(String name, String value)
    {
        settings.setProperty(name, value);
    }

    public void save() throws IOException {
        FileOutputStream out = new FileOutputStream(SETTINGS_FILENAME);
        settings.store(out, SETTINGS_FILENAME);
    }
}

