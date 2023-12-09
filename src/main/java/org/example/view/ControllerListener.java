package org.example.view;

import org.example.dto.IndicesReduced;

public interface ControllerListener {

    void createBullet();

    void updateGun(int mouseX, int mouseY);
    IndicesReduced getIndicesReducedObjects();
    void endGame();
}
