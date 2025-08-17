package net.weesli.rozsdblite.other;

import java.lang.reflect.Field;

/**
 * Represents the settings/configuration for a RozsDB database.
 * <p>
 * This class supports creating an instance of {@code DatabaseSettings}
 * from a semicolon-separated key-value string via {@link #withString(String)}.
 * </p>
 * <p>
 * The input string should be in the format:
 * <pre>
 *     "key1=value1;key2=value2;key3=value3"
 * </pre>
 * Keys are matched (case-insensitive) to the declared fields of this class,
 * and values are parsed and assigned accordingly.
 * </p>
 * <p>
 * Supports primitive types boolean, int, long, float, double, and String fields.
 * </p>
 *
 * <p><b>Usage example:</b></p>
 * <pre>
 *     DatabaseSettings settings = DatabaseSettings.withString("autoSave=true;autoSaveInterval=3600");
 * </pre>
 *
 * @author Weesli
 * @version 1.1.0
 */
public class DatabaseSettings {

    private boolean autoSave = false;
    // in seconds, if autoSave is enabled
    private int autoSaveInterval = 10 * 60 * 60;

    public DatabaseSettings() {}

    public static DatabaseSettings empty() {
        return new DatabaseSettings();
    }

    public static DatabaseSettings withString(String string) {
        DatabaseSettings settings = new DatabaseSettings();
        String[] strings = string.split(";");
        for (String s : strings) {
            String[] parts = s.split("=");
            String key = parts[0];
            String value = parts[1];
            try {
                for (Field field : DatabaseSettings.class.getDeclaredFields()) {
                    if (field.getName().toLowerCase().equals(key.toLowerCase())) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        appendField(settings,field,type,value);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return settings;
    }

    private static void appendField(Object obj, Field field, Class<?> type, String value){
        try {
            if (type.equals(boolean.class)) {
                field.setBoolean(obj, Boolean.parseBoolean(value));
            }else if (type.equals(String.class)) {
                field.set(null, value);
            }else if (type.equals(int.class)) {
                field.setInt(obj, Integer.parseInt(value));
            }else if (type.equals(long.class)) {
                field.setLong(obj, Long.parseLong(value));
            }else if (type.equals(float.class)) {
                field.setFloat(obj, Float.parseFloat(value));
            } else if (type.equals(double.class)) {
                field.setDouble(obj, Double.parseDouble(value));
            } else {
                throw new NoSuchMethodException();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getAutoSaveInterval() {
        return autoSaveInterval;
    }

    public boolean isAutoSave() {
        return autoSave;
    }
}
