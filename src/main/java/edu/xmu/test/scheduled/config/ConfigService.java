package edu.xmu.test.scheduled.config;

public interface ConfigService {
    public String getStringProperty(String name, String defaultValue);
    public String getStringProperty(String name);
    public long getLongProperty(String name, long defaultValue);
    public long getLongProperty(String name);
    public boolean getBooleanProperty(String name, boolean defaultValue);
    public boolean getBooleanProperty(String name);
    public void registerGlobalConfigChangeListener(String name, ConfigChangeListener listener);
}
