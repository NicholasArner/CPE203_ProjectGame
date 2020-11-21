import processing.core.PImage;

import java.util.List;

public class Obstacle extends Entity{
//    public Obstacle(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
//        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
//    }

    protected Obstacle(String id, Point position, List<PImage> images){
        super(id, position, images);
    }

}
