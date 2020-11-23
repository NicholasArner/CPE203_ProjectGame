import processing.core.PApplet;

public class EndScreen extends PApplet {


    @Override
    public void draw()
    {
        clear();
        fill(100, 0, 0);
        rect(275, 350, 30, 30);
        textSize(20);
        fill(100, 0, 0);
        text("BROWSER DEFEATED... VICTORY!", 285, 355);
        //noLoop();
        //DRAW THE START SCREEN
    }

}
