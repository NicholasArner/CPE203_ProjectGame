import processing.core.PImage;

import java.util.List;

public class Browser extends MovingEntity{
    protected Browser(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    protected Point nextPosition(WorldModel world, Point destPos) {
        return null;
    }

    @Override
    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

    }
}
