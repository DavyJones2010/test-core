package edu.xmu.test.scheduled.config;

public interface ConfigChangeListener {
    public void propertyChanged(String name, String oldValue, String newValue);
}
