import processing.core.PImage;

import java.util.List;

public abstract class MovingEntity extends AnimatedEntity{

    protected MovingEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected abstract Point nextPosition(WorldModel world, Point destPos);
    protected abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

    @Override
    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                super.getActionPeriod());
        scheduler.scheduleEvent(this, createAnimationAction( 0),
                this.getAnimationPeriod());
    }

    protected static boolean neighbors(Point p1, Point p2)
    {
        return p1.getX()+1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()-1 == p2.getY();
    }
}
