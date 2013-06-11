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
    public static final String SETTING_VERTICAL_PANE_DIVIDER_LOCATION = "VerticalSplitPaneDividerLocation";
    public static final String SETTING_HORIZONTAL_PANE_DIVIDER_LOCATION = "HorizontalSplitPaneDividerLocation";

    public static final String SETTING_CVS_TYPE = "cvsType";
    public static final String SETTING_CVS_HOST = "cvsHost";
    public static final String SETTING_CVS_USER = "cvsUser";
    public static final String SETTING_CVS_PASSWORD = "cvsPassword";

    public static final String SETTING_TRACKER_TYPE = "trackerType";
    public static final String SETTING_TRACKER_HOST = "trackerHost";
    public static final String SETTING_TRACKER_USER = "trackerUser";
    public static final String SETTING_TRACKER_PASSWORD = "trackerPassword";

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

    public String get(String name, String defaultValue)
    {
        if (settings.containsKey(name)) {
            return settings.getProperty(name);
        }
        return defaultValue;
    }

    public int getInt(String name)
    {
        if (settings.containsKey(name)) {
            return Integer.parseInt(settings.getProperty(name));
        }
        return 0;
    }

    public int getInt(String name, int defaultValue)
    {
        if (settings.containsKey(name)) {
            return Integer.parseInt(settings.getProperty(name));
        }
        return defaultValue;
    }

    public void set(String name, String value)
    {
        settings.setProperty(name, value);
    }

    public void set(String name, double value)
    {
        set(name, String.valueOf((int) value));
    }

    public void set(String name, int value)
    {
        set(name, String.valueOf(value));
    }

    public void save() throws IOException {
        FileOutputStream out = new FileOutputStream(SETTINGS_FILENAME);
        settings.store(out, SETTINGS_FILENAME);
    }
}

