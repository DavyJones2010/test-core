package edu.xmu.test.javax.spi;

/**
 * Created by davywalker on 17/2/28.
 */
public class StartCommand implements Command {
    @Override
    public void execute() {
        System.out.println("start");
    }
}
