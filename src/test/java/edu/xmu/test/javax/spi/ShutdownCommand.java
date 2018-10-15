package edu.xmu.test.javax.spi;

/**
 * Created by davywalker on 17/2/28.
 */
public class ShutdownCommand implements Command {
    @Override
    public void execute() {
        System.out.println("shutdown");
    }
}
