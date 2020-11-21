public class Animation extends Action{

    protected Animation(ActiveEntity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    protected void executeAction(EventScheduler scheduler)
    {
        super.getEntity().nextImage();

        if (super.getRepeatCount() != 1)
        {
            if (super.getEntity() instanceof AnimatedEntity){
                AnimatedEntity ane = (AnimatedEntity) super.getEntity();
                scheduler.scheduleEvent(super.getEntity(),
                        ane.createAnimationAction(
                                Math.max(super.getRepeatCount() - 1, 0)),
                        super.getEntity().getAnimationPeriod());
            }
        }
    }
}
