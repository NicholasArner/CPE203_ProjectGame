import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class MovingEntity extends AnimatedEntity{

    protected MovingEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected abstract Point nextPosition(WorldModel world, Point destPos);
    protected boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler){
        if (this.getPosition().adjacent(target.getPosition())) return true;
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());
            if (nextPos == null){
                return true;
            }

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
