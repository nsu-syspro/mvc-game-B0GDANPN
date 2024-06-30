package org.example.dto;

public record BulletDto(int x, int y, double angle,DtoType type) implements GameObjectInfo {
}

