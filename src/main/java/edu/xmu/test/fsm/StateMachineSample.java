package edu.xmu.test.fsm;

import java.util.Collection;
import java.util.EnumSet;

import com.alibaba.fastjson.JSON;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

/**
 * Created by kunlun.ykl on 2018/8/23.
 */
public class StateMachineSample {
    static StateMachine<States, Events> buildMachine() throws Exception {
        Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
            .withStates()
            .initial(States.STATE1)
            .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
            .withExternal()
            .source(States.STATE1).target(States.STATE2)
            .event(Events.EVENT1)
            .and()
            .withExternal()
            .source(States.STATE1).target(States.STATE2)
            .event(Events.EVENT3)
            .and()
            .withExternal()
            .source(States.STATE2).target(States.STATE1)
            .event(Events.EVENT2);

        return builder.build();
    }

    static StateMachine<States, Events> stateMachine;

    static {
        try {
            stateMachine = buildMachine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        stateMachine.addStateListener(new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                if (from == null) {
                    return;
                }
                System.out.println(String
                    .format("Thread-%s stateChanged %s->%s", Thread.currentThread().getName(), from.getId(),
                        to.getId()));
                stateMachine.sendEvent(Events.EVENT1);
            }
        });
        stateMachine.start();

        new Thread("event1") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep((long)(Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stateMachine.sendEvent(Events.EVENT1);
                }
            }
        }.start();

        new Thread("event2") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep((long)(Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stateMachine.sendEvent(Events.EVENT2);
                }
            }
        }.start();

        new Thread("event3") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep((long)(Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stateMachine.sendEvent(Events.EVENT3);
                }
            }
        }.start();
        stateMachine.sendEvent(Events.EVENT1);
        //stateMachine.sendEvent(Events.EVENT1);
        //stateMachine.sendEvent(Events.EVENT1);
        //stateMachine.sendEvent(Events.EVENT1);
        Collection<Transition<States, Events>> transitions = stateMachine.getTransitions();
        System.out.println(JSON.toJSONString(transitions));

        stateMachine.sendEvent(Events.EVENT2);
        //stateMachine.sendEvent(Events.EVENT2);
        transitions = stateMachine.getTransitions();
        System.out.println(JSON.toJSONString(transitions));

        stateMachine.sendEvent(Events.EVENT3);
        transitions = stateMachine.getTransitions();
        System.out.println(JSON.toJSONString(transitions));
    }
}
