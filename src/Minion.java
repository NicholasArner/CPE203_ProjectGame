import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class Minion extends MovingEntity{

    protected Minion(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public abstract Point nextPosition(WorldModel world, Point destPos);

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> nearestMario = world.findNearest(getPosition(), Mario.class);
        Point target = new Point(17, 15);
        if (nearestMario.isPresent()){
            target = nearestMario.get().getPosition();
        }
        Entity endGoal = new EndGoal("endGoal", target, imageStore.getImageList("endGoal"));
        if (!moveTo(world, endGoal, scheduler)){
            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    getActionPeriod());
        }
        else{
            // minion should already be there
            nearestMario.ifPresent(scheduler::unscheduleAllEvents);
            world.moveEntity(this, target);
        }
    }

    public static boolean neighbors(Point p1, Point p2)
    {
        return p1.getX()+1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()-1 == p2.getY();
    }

}
