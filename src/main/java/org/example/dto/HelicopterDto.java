package org.example.dto;

import org.example.model.Direction;

public record HelicopterDto(int x, int y, Direction direction) implements GameObjectInfo {//save info ab
    @Override
    public DtoType type() {
        return DtoType.HELICOPTER;
    }

}
