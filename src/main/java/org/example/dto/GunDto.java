package org.example.dto;

public record GunDto(int x, int y, double angle) implements GameObjectInfo {

    @Override
    public DtoType type() {
        return DtoType.GUN;
    }
}
