package org.example.dto;

import java.util.List;

public record IndicesReduced(List<Integer> indicesBullets, List<Integer> indicesHelicopters, List<Integer> indicesParachutists) {
}
