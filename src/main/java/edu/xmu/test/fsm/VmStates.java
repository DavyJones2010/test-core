package edu.xmu.test.fsm;

/**
 * Created by kunlun.ykl on 2018/8/23.
 */
public enum VmStates {
    INIT,
    CREATING,
    PENDING,
    STARTING,
    START_FAILED,
    RUNNING,
    LIVE_MIGRATING,
    LIVE_MIGRATE_FAILED,
    STOPPING,
    SHUT_FAILED,
    STOPPED,
    DESTROYING,
    DESTROYED;
}
