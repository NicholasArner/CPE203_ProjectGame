import processing.core.PImage;

import java.util.List;

public abstract class ActiveEntity extends Entity{

    private final int actionPeriod;
    private final int animationPeriod;

    public ActiveEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public int getActionPeriod() {
        return actionPeriod;
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    protected abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    protected abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
//    protected Action createAnimationAction(int repeatCount)
//    {
//        return new Animation(this, null, null, repeatCount);
//    }
    protected Action createActivityAction(WorldModel world, ImageStore imageStore) { return new Activity(this, world, imageStore, 0); }
    protected void nextImage()
    {
        this.setImageIndex((this.getImageIndex() + 1) % this.getImages().size());
    }
}
