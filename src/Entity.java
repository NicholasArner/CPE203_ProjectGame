import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */


abstract class Entity
{
   public static final Random rand = new Random();

   private final String id;
   private Point position;
   private final List<PImage> images;
   private int imageIndex;

   protected Entity(String id, Point position,
                 List<PImage> images)
   {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
   }
   public Point getPosition(){ return position;}
   public void setPosition(Point p){ position = p;}
   public List<PImage> getImages(){ return images;}
   public String getId(){ return this.id;}
   public int getImageIndex() { return imageIndex; }
   public void setImageIndex(int imageIndex) { this.imageIndex = imageIndex; }

   public PImage getCurrentImage(){ return (this).images.get((this).imageIndex); }

}