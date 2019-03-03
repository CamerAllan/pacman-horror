package implementation;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static processing.core.PConstants.CENTER;

public class Light {

    PVector position;
    PImage image;

    public Light(PImage image, PVector playerPosition) {
        this.position = playerPosition;
        this.image = image;
    }

    public void update(PVector playerPosition) {
        this.position = playerPosition;
    }

    public void draw(PApplet app, int scale) {
        app.imageMode(CENTER);
        app.image(this.image, this.position.x, this.position.y);
    }
}
