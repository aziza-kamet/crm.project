package com.crm.project.enums;

/**
 * Created by aziza on 03.01.18.
 */
public enum Days {
    Monday(0),
    Tuesday(1),
    Wednesday(2),
    Thursday(3),
    Friday(4),
    Saturday(5);

    private int key;

    Days(int key) {
        this.key = key;
    }

    public int key() {
        return key;
    }
}
