package org.example.view;

import org.example.model.Gun;

public interface ControllerListener {

    public void createBullet();

    public void updateGunPosition(double mouseX, double mouseY);
}
