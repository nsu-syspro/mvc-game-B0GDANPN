
    private void updateGame(Point mousePosition) {

        for (int i = 1; i < gameObjects.size(); i++) {
            gameObjects.get(i).move();
        }
        checkHit();
        paratrooperToSoldier();
        checkCountSoldiers();
        checkCollisions();
    }


    private void checkCollisions() {
        Rectangle rectangleScreen = new Rectangle(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
        Set<GameObject> objectsToRemove = new HashSet<>();
        for (GameObject object : gameObjects) {
            if (object instanceof Parachutist) {
                if (!object.getBounds().intersects(rectangleScreen)) {
                    objectsToRemove.add(object);
                }
            }
        }
        gameObjects.removeAll(objectsToRemove);
    }




    private void checkHit() {
        Set<GameObject> objectsToRemove = new HashSet<>();
        Set<Bullet> bullets = gameObjects.stream().filter(o -> o instanceof Bullet).map(o -> (Bullet) o).collect(toSet());
        Set<GameObject> otherObjects = gameObjects.stream().filter(o -> !(o instanceof Bullet) && !(o instanceof Gun) && !(o instanceof Soldier)).collect(toSet());
        for (Bullet bullet : bullets) {
            for (GameObject otherObject : otherObjects) {
                if (bullet.getBounds().intersects(otherObject.getBounds())) {
                    increaseScore();
                    objectsToRemove.add(bullet);
                    objectsToRemove.add(otherObject);
                }
            }
        }
        gameObjects.removeAll(objectsToRemove);
    }


    private void checkCountSoldiers() {
        int soldierCount = 0;
        for (GameObject object : gameObjects) {
            if (object instanceof Soldier) {
                soldierCount++;
            }
        }
        if (soldierCount >= 5) {
            endGame();
        }
    }


    private void endGame() {//TODO jj
        gameFrame.dispose();
        try {
            FileWriter writer = new FileWriter("file.txt", true);
            writer.write(score + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
