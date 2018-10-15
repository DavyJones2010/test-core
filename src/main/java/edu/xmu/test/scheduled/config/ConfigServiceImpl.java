package edu.xmu.test.scheduled.config;

/**
 * Created by davywalker on 16/10/26.
 */
public class ConfigServiceImpl implements ConfigService {
    @Override
    public String getStringProperty(String name, String defaultValue) {
        return null;
    }

    @Override
    public String getStringProperty(String name) {
        return null;
    }

    @Override
    public long getLongProperty(String name, long defaultValue) {
        return 0;
    }

    @Override
    public long getLongProperty(String name) {
        return 0;
    }

    @Override
    public boolean getBooleanProperty(String name, boolean defaultValue) {
        return false;
    }

    @Override
    public boolean getBooleanProperty(String name) {
        return false;
    }

    @Override
    public void registerGlobalConfigChangeListener(String name, ConfigChangeListener listener) {

    }
}
