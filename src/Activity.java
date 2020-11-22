public class Activity extends Action{
    protected Activity(ActiveEntity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    protected void executeAction(EventScheduler scheduler)
    {
        getEntity().executeActivity(super.getWorld(),
                    getImageStore(), scheduler);
    }

}
