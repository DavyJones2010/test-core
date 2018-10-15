package edu.xmu.test.designpattern.api;

public interface CommonRequest<T> {
    Class<T> getResponseClass();
}
