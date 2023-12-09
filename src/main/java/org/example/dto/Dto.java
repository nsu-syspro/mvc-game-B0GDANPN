package org.example.dto;

import org.example.model.Direction;

// - paratrooper -> soldier
// - helicopter direction
public record Dto(int x, int y, DtoType dtoType,int additional) {
    public Dto(int x, int y, DtoType dtoType) {
        this( x, y, dtoType,-1); // set the default value for y
    }
}

sealed interface GameObject permits HelicopterDto, GunDto, Dto1 {
    int x();
    int y();
    DtoType type();
}

record Dto1(int x, int y, DtoType type) implements GameObject {}

record HelicopterDto(int x, int y, Direction direction) implements GameObject {

    @Override
    public DtoType type() {
        return DtoType.HELICOPTER;
    }
}

record GunDto(int x, int y, int angle) implements GameObject {

    @Override
    public DtoType type() {
        return DtoType.GUN;
    }
}
