package org.example.dto;

public record ParatrooperDto(int x, int y, boolean onGround) implements GameObjectInfo {

    @Override
    public DtoType type() {
        return DtoType.PARATROOPER;
    }
}
