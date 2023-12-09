package org.example.view;

import org.example.dto.IndicesReduced;

import java.util.List;

public interface ControllerListener {

    public void createBullet();

    public void updateGun(int mouseX, int mouseY);
    public IndicesReduced getIndicesReducedObjects();
}
