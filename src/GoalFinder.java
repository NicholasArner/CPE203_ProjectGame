import processing.core.PImage;

import java.util.List;

public abstract class GoalFinder extends MovingEntity{
    private int num_lives = 3;
    private final PathingStrategy strategy = new AStarPathingStrategy();

    protected GoalFinder(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public int getNum_lives() {
        return num_lives;
    }

    public Point nextPosition(WorldModel world, Point destPos){ return null;}
}
