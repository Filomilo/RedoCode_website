package com.redocode.backend.VmAcces;

public enum VmStatus {
    AWAITING_ACCES,
    CREATING_VM,
    STARTING_MACHINE,
    STOPPING_MACHINE,
    RUNNING_MACHINE,
    MACHINE_STOPPED,
    NOT_REQUESTED,
    DESTROYING_MACHINE,
    RESTARTING,
    PAUSED,
    DESTROYED,
    CREATED
}
