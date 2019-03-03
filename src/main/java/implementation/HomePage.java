package implementation;

import processing.core.PApplet;
import processing.core.PFont;

public class HomePage {
    PFont homeFont;

    public HomePage(PFont homeFont) {
        this.homeFont = homeFont;
    }

    public void draw(PApplet app) {
        app.textFont(homeFont);
        app.background(0,0,0);
        app.textSize(50);
        app.fill(107,2,2);
        app.text("Pac Man's Nightmare", app.width/2, app.height/2);
        app.textSize(30);
        app.text("Press SPACE to play", app.width/2, app.height/2+100);
    }

}
