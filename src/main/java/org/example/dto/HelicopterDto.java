package org.example.dto;

import org.example.model.Direction;

public record HelicopterDto(int x, int y, Direction direction) implements GameObjectInfo {//save info ab
    @Override
    public DtoType type() {
        return DtoType.HELICOPTER;
    }
    public int getDirection() {
        if (direction == Direction.LEFT) {
            return -1;
        } else {
            return 1;
        }
    }
}
