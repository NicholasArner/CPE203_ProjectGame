import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Crab extends MovingEntity{
    private static final String QUAKE_KEY = "quake";

//    public Crab(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
//        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
//    }

    protected Crab(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
        super(id, position, images, 0, 0, actionPeriod, animationPeriod);
    }

    @Override
    protected Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz,
                this.getPosition().getY());

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get() instanceof Fish)))
        {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get() instanceof Fish)))
            {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

//    @Override
//    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
//        return false;
//    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> crabTarget = world.findNearest(
                getPosition(), SGrass.class);
        long nextPeriod = getActionPeriod();

        if (crabTarget.isPresent())
        {
            Point tgtPos = crabTarget.get().getPosition();

            if (moveTo( world, crabTarget.get(), scheduler))
            {
                ActiveEntity quake = new Quake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                nextPeriod);
    }


}
