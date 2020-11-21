public class Activity extends Action{
    protected Activity(ActiveEntity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    protected void executeAction(EventScheduler scheduler)
    {
//        switch (super.getEntity().getKind())
//        {
//            case OCTO_FULL:
//
//            case ATLANTIS:
//
//            case SGRASS:
//
//            case QUAKE:
//
//            case CRAB:
//
//            case FISH:
//
//            case OCTO_NOT_FULL:
//                super.getEntity().executeActivity(super.getWorld(),
//                        super.getImageStore(), scheduler);
//                break;
//
//            default:
//                throw new UnsupportedOperationException(
//                        String.format("executeActivityAction not supported for %s",
//                                super.getEntity().getKind()));
//        }

        getEntity().executeActivity(super.getWorld(),
                    getImageStore(), scheduler);


    }

}
