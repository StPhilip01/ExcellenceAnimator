import static org.junit.Assert.assertEquals;

import cs3500.animator.model.action.Action;
import cs3500.animator.model.action.RotationActionImpl;
import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Position;
import cs3500.animator.model.shapes.RGBColor;
import cs3500.animator.model.shapes.Rectangle;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for a {@link RotationActionImpl} implementation of Action, which includes
 * rotation.
 */
public class RotationActionImplTest {

  // shapes
  IShape rect;
  IShape ellipse;

  // action to keep a shape the same way it was for 10 ticks
  Action defaultAction;

  // actions to change the size of an ellipse
  Action growEllipse;
  Action shrinkEllipse;

  @Before
  public void init() {
    rect = new Rectangle(100, 100, new Position(0, 0), new RGBColor(0, 0, 255), false);
    defaultAction = new RotationActionImpl("R", rect, 1, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight(), rect.getColor(), 0);

    ellipse = new Ellipse(20, 20, new Position(0, 0), new RGBColor(255, 255, 255), false);
    growEllipse = new RotationActionImpl("C", ellipse, 10, ellipse.getCurrentPosition(),
        120, 220, ellipse.getColor(), -20);

    shrinkEllipse = new RotationActionImpl("shrunk", ellipse, 40, ellipse.getCurrentPosition(),
        20, 30.4, ellipse.getColor(), 2000);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullShape() {
    Action a = new RotationActionImpl("R", null, 10, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight(), rect.getColor(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullPosition() {
    Action a = new RotationActionImpl("R", rect, 10, null, rect.getWidth(),
        rect.getHeight(), rect.getColor(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullColor() {
    Action a = new RotationActionImpl("R", rect, 10, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight(), null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullShapeName() {
    Action a = new RotationActionImpl(null, rect, 10, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight(), rect.getColor(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNegativeTick() {
    Action a = new RotationActionImpl("shape", rect, -1, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight(), rect.getColor(), 0);
  }


  @Test
  public void getToDegrees() {
    assertEquals(0, defaultAction.getToDegrees());
    assertEquals(-20, growEllipse.getToDegrees());
    assertEquals(2000, shrinkEllipse.getToDegrees());
  }

  @Test
  public void testToString() {
    assertEquals("time=1,  pos=(0,0), size=(100,100), color=(0,0,255) degrees=0",
        defaultAction.toString());
    assertEquals("time=10,  pos=(0,0), size=(120,220), color=(255,255,255) degrees=-20",
        growEllipse.toString());
    assertEquals("time=40,  pos=(0,0), size=(20,30), color=(255,255,255) degrees=2000",
        shrinkEllipse.toString());
  }
}