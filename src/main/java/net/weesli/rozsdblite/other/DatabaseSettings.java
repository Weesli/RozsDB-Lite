package net.weesli.rozsdblite.other;

import java.lang.reflect.Field;

public class DatabaseSettings {
    private final boolean autoCreate = false;
    private final boolean autoCreateTable = false;
    public DatabaseSettings() {}

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

    public boolean isAutoCreate() {
        return autoCreate;
    }

    public boolean isAutoCreateTable() {
        return autoCreateTable;
    }
}
