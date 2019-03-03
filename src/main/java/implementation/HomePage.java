package implementation;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class HomePage {
    PFont homeFont;

    public HomePage(PFont homeFont) {
        this.homeFont = homeFont;
    }

    public void draw(PApplet app) {
        app.textFont(homeFont);
        app.textMode(PConstants.CENTER);
        app.background(0,0,0);
        app.textSize(35);
        app.fill(107,2,2);
        app.text("Pac Man's Nightmare", app.width/8, app.height/2-100);
        app.textSize(25);
        app.text("Press SPACE to play", app.width/5, app.height/2+100);
    }

}
