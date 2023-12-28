package org.example.dto;

sealed public interface GameObjectInfo permits Dto, GunDto, HelicopterDto, ParatrooperDto {
    int x();

    int y();

    DtoType type();
}
