package org.example.dto;

// - paratrooper -> soldier
// - helicopter direction
sealed public interface GameObjectInfo permits Dto, GunDto, HelicopterDto, ParatrooperDto {
    int x();
    int y();
    DtoType type();
}
