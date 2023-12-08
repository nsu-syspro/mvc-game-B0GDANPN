package org.example.dto;

public record Dto(int x, int y, DtoType dtoType,int additional) {
    public Dto(int x, int y, DtoType dtoType) {
        this( x, y, dtoType,-1); // set the default value for y
    }
}