import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class Minion extends MovingEntity{

    protected Minion(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public abstract Point nextPosition(WorldModel world, Point destPos);

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
                world.phaseEntity(this, nextPos);
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
