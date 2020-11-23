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
//        Optional<Entity> nearestGoalFinder = world.findNearest(getPosition(), GoalFinder.class);
//        Point target = new Point(17, 15);
//        if (nearestGoalFinder.isPresent()){
//            target = nearestGoalFinder.get().getPosition();
//        }
//        Entity endGoal = new EndGoal("endGoal", target, imageStore.getImageList("endGoal"));
//        if (!moveTo(world, endGoal, scheduler)){
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    getActionPeriod());
//        }
//        else{
//            // minion should already be there
//            GoalFinder goalFinder;
//            if (nearestGoalFinder.isPresent()){
//                goalFinder = (GoalFinder) nearestGoalFinder.get();
//                goalFinder.hit();
//            }
//            world.moveEntity(this, target);
//        }
    }
    @Override
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
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                occupant.ifPresent(scheduler::unscheduleAllEvents);
                world.PhaseEntity(this, nextPos);
            }
            return false;
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
