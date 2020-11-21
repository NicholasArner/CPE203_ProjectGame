import processing.core.PImage;

import java.util.List;

public abstract class AnimatedEntity extends ActiveEntity{
    protected AnimatedEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected Action createAnimationAction(int repeatCount)
    {
        return new Animation(this, null, null, repeatCount);
    }
}
