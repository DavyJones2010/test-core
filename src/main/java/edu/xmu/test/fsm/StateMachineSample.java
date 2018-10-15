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
    private static StateMachine<States, Events> buildMachine() throws Exception {
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
            .source(States.STATE2).target(States.STATE1)
            .event(Events.EVENT2);

        return builder.build();
    }

    public static void main(String[] args) throws Exception {
        StateMachine<States, Events> stateMachine = buildMachine();
        stateMachine.addStateListener(new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println(String.format("stateChanged %s->%s", from.getId(), to.getId()));
            }
        });
        stateMachine.start();
        stateMachine.sendEvent(Events.EVENT1);
        stateMachine.sendEvent(Events.EVENT1);
        stateMachine.sendEvent(Events.EVENT1);
        stateMachine.sendEvent(Events.EVENT1);
        stateMachine.sendEvent(Events.EVENT2);
        stateMachine.sendEvent(Events.EVENT2);
        Collection<Transition<States, Events>> transitions = stateMachine.getTransitions();
        System.out.println(JSON.toJSONString(transitions));
    }
}
