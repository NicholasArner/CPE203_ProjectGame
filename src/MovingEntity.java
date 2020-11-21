import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class MovingEntity extends AnimatedEntity{
    private final int resourceLimit;
    private int resourceCount;

    protected MovingEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public int getResourceLimit(){ return this.resourceLimit;}
    public int getResourceCount(){ return this.resourceCount;}
    public void setResourceCount(int i){ resourceCount = i;}

    protected abstract Point nextPosition(WorldModel world, Point destPos);
    protected boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler){
        if (this.getPosition().adjacent(target.getPosition()))
        {
            //this.resourceCount += 1;
            if (this instanceof Octo_Not_Full | this instanceof Crab){
                if (this instanceof Octo_Not_Full) setResourceCount(getResourceCount() + 1);

                world.removeEntity(target);
                scheduler.unscheduleAllEvents(target);
            }

            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    @Override
    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                super.getActionPeriod());
        scheduler.scheduleEvent(this, createAnimationAction( 0),
                this.getAnimationPeriod());
    }
}
