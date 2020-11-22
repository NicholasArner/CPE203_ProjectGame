import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Octo_Full extends Octo{
//    public Octo_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
//        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
//    }

    protected Octo_Full(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(super.getPosition(),
                Atlantis.class);

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
            Entity e = fullTarget.get();
            if ( e instanceof ActiveEntity){
                ActiveEntity ae = (ActiveEntity) e;
                ae.scheduleActions(scheduler, world, imageStore);
            }


            //transform to unfull
            transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    super.getActionPeriod());
        }
    }

    @Override
    protected boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return false;
    }

//    @Override
//    public Point nextPosition(WorldModel world, Point destPos) {
//        return null;
//    }

//    @Override
//    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
//        return false;
//    }

//    @Override
//    protected boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
//
//        ActiveEntity octo = new Octo_Not_Full(this.getId(), this.getResourceLimit(),
//                this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
//                this.getImages());
//
//        world.removeEntity(this);
//        scheduler.unscheduleAllEvents(this);
//
//        world.addEntity(octo);
//        octo.scheduleActions(scheduler, world, imageStore);
//
//        return false;
//
//    }

}
