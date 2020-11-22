import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Octo_Not_Full extends Octo{
    protected Octo_Not_Full(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }
//    public Octo_Not_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
//        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
//    }



//    @Override
//    public Point nextPosition(WorldModel world, Point destPos) {
//        return null;
//    }

//    @Override
//    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
//        if (this.getPosition().adjacent(target.getPosition()))
//        {
//            //this.resourceCount += 1;
//            this.setResourceCount(this.getResourceCount() + 1);
//            world.removeEntity(target);
//            scheduler.unscheduleAllEvents(target);
//
//            return true;
//        }
//        else
//        {
//            Point nextPos = nextPosition(world, target.getPosition());
//
//            if (!this.getPosition().equals(nextPos))
//            {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent())
//                {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> notFullTarget = world.findNearest(super.getPosition(),
//                Fish.class);
//
//        if (!notFullTarget.isPresent() ||
//                !moveTo( world, notFullTarget.get(), scheduler) ||
//                !transform( world, scheduler, imageStore))
//        {
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    super.getActionPeriod());
//        }
    }

    @Override
    protected boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return false;
    }

//    @Override
//    protected boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (this.getResourceCount() >= this.getResourceLimit())
//        {
//            ActiveEntity octo = new Octo_Full(this.getId(), this.getResourceLimit(),
//                    this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
//                    this.getImages());
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(octo);
//            octo.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }
}
