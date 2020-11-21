import processing.core.PImage;

import java.util.List;

public abstract class GoalFinder extends AnimatedEntity{
    private int num_lives = 3;

    protected GoalFinder(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public int getNum_lives() {
        return num_lives;
    }
}
