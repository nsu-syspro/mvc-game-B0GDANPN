package org.example.model;

public enum Direction {
    LEFT {
        @Override
        int delta(int speed) {
            return -speed;
        }
    },
    RIGHT {
        @Override
        int delta(int speed) {
            return speed;
        }
    };

    abstract int delta(int speed);
}
