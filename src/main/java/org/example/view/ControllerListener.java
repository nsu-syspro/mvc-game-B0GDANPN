package org.example.view;

import org.example.model.Bullet;
import org.example.model.Helicopter;
import org.example.model.Parachutist;

import java.util.List;
import java.util.Objects;

public interface ControllerListener {

    public void createBullet();

    public void updateGun(int mouseX, int mouseY);
    public List<Objects> getIntersectedBullets();
    public List<Objects> getIntersectedParachutists();
    public List<Objects> getIntersectedHelicopters();
}
