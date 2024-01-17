package org.example.dto;

sealed public interface GameObjectInfo permits BulletDto, GunDto, HelicopterDto, ParatrooperDto {
    int x();

    int y();

    DtoType type();
}
