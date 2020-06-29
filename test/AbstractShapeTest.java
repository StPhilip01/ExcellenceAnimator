import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IPosition;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Position;
import cs3500.animator.model.shapes.RGBColor;
import cs3500.animator.model.shapes.Rectangle;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for AbstractShape class. Provides methods to simultaneously test concrete
 * implementations.
 */

public abstract class AbstractShapeTest {

  protected IShape shape(double width, double height, Position pos, IColor color) {
    return shape(width, height, pos, color, 0);
  }

  protected abstract IShape shape(double width, double height, Position pos, IColor color,
      int rotation);

  /**
   * Concrete implementation for testing the {@link Rectangle} class.
   */
  public static final class RectangleTest extends AbstractShapeTest {

    @Override
    protected IShape shape(double width, double height, Position pos, IColor color, int rotation) {
      return new Rectangle(width, height, pos, color, false, rotation);
    }
  }

  /**
   * Concrete implementation for testing the {@link Ellipse} class.
   */
  public static final class EllipseTest extends AbstractShapeTest {

    @Override
    protected IShape shape(double width, double height, Position pos, IColor color, int rotation) {
      return new Ellipse(width, height, pos, color, false, rotation);
    }
  }

  IColor black;
  IColor red;
  IColor green;
  IColor blue;

  IShape defaultShape;

  @Before
  public void init() {
    black = new RGBColor(0, 0, 0);
    red = new RGBColor(255, 0, 0);
    green = new RGBColor(0, 255, 0);
    blue = new RGBColor(0, 0, 255);

    defaultShape = shape(50.5, 50.5, new Position(10, 10), blue);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullPosition() {
    IShape r = shape(10, 10, null, black);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullColor() {
    IShape r = shape(10, 10, new Position(0, 0), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroWidth() {
    IShape r = shape(0, 10, new Position(0, 0), black);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeWidth() {
    IShape r = shape(-10, 10, new Position(0, 0), black);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testZeroHeight() {
    IShape r = shape(10, 0, new Position(0, 0), black);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHeight() {
    IShape r = shape(10, -10, new Position(0, 0), black);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHeightWidth() {
    IShape r = shape(-10, -10, new Position(0, 0), black);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHeightWidthNullArgs() {
    IShape r = shape(-10, -10, null, null);
  }

  @Test
  public void testCopy() {
    IShape r = shape(200, 101.1, new Position(10, 20), red);
    IShape copied = r.copy();

    assertTrue(r.equals(copied));
    assertFalse(r == copied);
  }

  @Test
  public void testMoveToZero() {
    Position toZero = new Position(0, 0);

    assertEquals(new Position(10, 10), defaultShape.getCurrentPosition());
    defaultShape.move(toZero);
    assertEquals(toZero, defaultShape.getCurrentPosition());
  }

  @Test
  public void testMoveToQuadrantOne() {
    Position toQuadrantOne = new Position(100, 100);
    assertEquals(new Position(10, 10), defaultShape.getCurrentPosition());
    defaultShape.move(toQuadrantOne);
    assertEquals(toQuadrantOne, defaultShape.getCurrentPosition());
  }

  @Test
  public void testMoveToQuadrantTwo() {
    Position toQuadrantTwo = new Position(-100, 100);
    assertEquals(new Position(10, 10), defaultShape.getCurrentPosition());
    defaultShape.move(toQuadrantTwo);
    assertEquals(toQuadrantTwo, defaultShape.getCurrentPosition());
  }

  @Test
  public void testMoveToQuadrantThree() {
    Position toQuadrantThree = new Position(-100, -100);
    assertEquals(new Position(10, 10), defaultShape.getCurrentPosition());
    defaultShape.move(toQuadrantThree);
    assertEquals(toQuadrantThree, defaultShape.getCurrentPosition());
  }

  @Test
  public void testMoveToQuadrantFour() {
    Position toQuadrantFour = new Position(100, -100);
    assertEquals(new Position(10, 10), defaultShape.getCurrentPosition());
    defaultShape.move(toQuadrantFour);
    assertEquals(toQuadrantFour, defaultShape.getCurrentPosition());
  }

  @Test
  public void testMoveSamePoint() {
    IPosition startingPosition = defaultShape.getCurrentPosition().copy();
    assertEquals(startingPosition, defaultShape.getCurrentPosition());
    defaultShape.move(startingPosition);
    assertEquals(startingPosition, defaultShape.getCurrentPosition());
  }


  @Test(expected = IllegalArgumentException.class)
  public void growInvalidWidth() {
    defaultShape.grow(-1 * defaultShape.getWidth(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void growInvalidHeight() {
    defaultShape.grow(0, -1 * defaultShape.getHeight());
  }

  @Test
  public void testGrowWidthPositive() {
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
    defaultShape.grow(20.5, 0);
    assertEquals(71, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
  }

  @Test
  public void testGrowWidthNegative() {
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
    defaultShape.grow(-20.5, 0);
    assertEquals(30, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
  }

  @Test
  public void testGrowHeightPositive() {
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
    defaultShape.grow(0, 20.5);
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(71, defaultShape.getHeight(), .0001);
  }

  @Test
  public void testGrowHeightNegative() {
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
    defaultShape.grow(0, -20.5);
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(30, defaultShape.getHeight(), .0001);
  }

  @Test
  public void testGrowBothPositive() {
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
    defaultShape.grow(20, 10);
    assertEquals(70.5, defaultShape.getWidth(), .0001);
    assertEquals(60.5, defaultShape.getHeight(), .0001);
  }

  @Test
  public void testGrowBothNegative() {
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
    defaultShape.grow(-10, -20);
    assertEquals(40.5, defaultShape.getWidth(), .0001);
    assertEquals(30.5, defaultShape.getHeight(), .0001);
  }

  @Test
  public void testGrowWidthPositiveHeightNegative() {
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
    defaultShape.grow(10, -20);
    assertEquals(60.5, defaultShape.getWidth(), .0001);
    assertEquals(30.5, defaultShape.getHeight(), .0001);
  }

  @Test
  public void testGrowZeroZero() {
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
    defaultShape.grow(0, 0);
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
  }

  @Test
  public void testRotateTo() {
    assertEquals(0, defaultShape.getRotation());
    defaultShape.rotateTo(200);
    assertEquals(200, defaultShape.getRotation());
    defaultShape.rotateTo(-20);
    assertEquals(-20, defaultShape.getRotation());
  }

  @Test
  public void testGrowHeightPositiveWidthNegative() {
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    assertEquals(50.5, defaultShape.getHeight(), .0001);
    defaultShape.grow(-10, 20);
    assertEquals(40.5, defaultShape.getWidth(), .0001);
    assertEquals(70.5, defaultShape.getHeight(), .0001);
  }

  @Test
  public void testChangeColor() {
    assertEquals(blue, defaultShape.getColor());
    defaultShape.changeColor(black);
    assertEquals(black, defaultShape.getColor());
    defaultShape.changeColor(red);
    assertEquals(red, defaultShape.getColor());
    defaultShape.changeColor(blue);
    assertEquals(blue, defaultShape.getColor());
    defaultShape.changeColor(green);
    assertEquals(green, defaultShape.getColor());
    defaultShape.changeColor(green);
    assertEquals(green, defaultShape.getColor());
  }

  @Test
  public void testGetWidth() {
    assertEquals(50.5, defaultShape.getWidth(), .0001);
    IShape shape1 = shape(1000, 1, new Position(0, 0), black);
    assertEquals(1000, shape1.getWidth(), .0001);

    defaultShape.grow(100, 0);
    assertEquals(150.5, defaultShape.getWidth(), .0001);

    shape1.grow(-100, 0);
    assertEquals(900, shape1.getWidth(), .0001);
  }

  @Test
  public void testGetHeight() {
    assertEquals(50.5, defaultShape.getHeight(), .0001);
    IShape shape1 = shape(1, 1000, new Position(0, 0), black);
    assertEquals(1000, shape1.getHeight(), .0001);

    defaultShape.grow(0, 100);
    assertEquals(150.5, defaultShape.getHeight(), .0001);

    shape1.grow(0, -100);
    assertEquals(900, shape1.getHeight(), .0001);
  }

  @Test
  public void testVisibility() {
    assertFalse(defaultShape.getVisibility());

    IShape shape1 = shape(1, 1000, new Position(0, 0), black);
    assertFalse(shape1.getVisibility());

    defaultShape.setVisibility(false);
    assertFalse(defaultShape.getVisibility());
    shape1.setVisibility(false);
    assertFalse(shape1.getVisibility());

    defaultShape.setVisibility(true);
    assertTrue(defaultShape.getVisibility());
    shape1.setVisibility(true);
    assertTrue(shape1.getVisibility());
  }

  @Test
  public void testGetCurrentPosition() {
    assertEquals(new Position(10, 10), defaultShape.getCurrentPosition());

    IShape shape1 = shape(100, 100, new Position(0, 0), green);
    assertEquals(new Position(0, 0), shape1.getCurrentPosition());

    defaultShape.move(new Position(0, 0));
    assertEquals(new Position(0, 0), defaultShape.getCurrentPosition());

    shape1.move(new Position(1000, -10));
    assertEquals(new Position(1000, -10), shape1.getCurrentPosition());
  }

  @Test
  public void testGetColor() {
    assertEquals(blue, defaultShape.getColor());
    IShape shape1 = shape(10, 20.5, new Position(0, 0), green);
    assertEquals(green, shape1.getColor());

    defaultShape.changeColor(black);
    assertEquals(black, defaultShape.getColor());
    shape1.changeColor(red);
    assertEquals(red, shape1.getColor());
    shape1.changeColor(red);
    assertEquals(red, shape1.getColor());
  }


  @Test
  public void testEquals() {
    IShape shape1 = shape(10.5, 10, new Position(10, 0), blue);
    IShape shape1copied = shape1.copy();

    IShape shape2 = shape(200, 20, new Position(0, 0), red);
    IShape defaultShape2 = shape(50.5, 50.5, new Position(10, 10), blue);

    assertTrue(shape1.equals(shape1copied));
    assertTrue(shape1copied.equals(shape1));
    assertTrue(defaultShape2.equals(defaultShape));
    assertTrue(defaultShape.equals(defaultShape2));

    assertTrue(defaultShape.equals(defaultShape));
    assertFalse(defaultShape.equals(shape1));
    assertFalse(shape1.equals(defaultShape));
    assertFalse(defaultShape == null);
  }

  @Test
  public void testHashCode() {
    IShape shape1 = shape(10.5, 10, new Position(10, 0), blue);
    IShape shape1copied = shape1.copy();

    IShape shape2 = shape(200, 20, new Position(0, 0), red);
    IShape defaultShape2 = shape(50.5, 50.5, new Position(10, 10), blue);

    assertTrue(shape1.hashCode() == shape1.hashCode());
    assertTrue(shape1.hashCode() == shape1copied.hashCode());
    assertFalse(shape1.hashCode() == defaultShape.hashCode());
    assertFalse(shape1.hashCode() == defaultShape2.hashCode());
    assertTrue(defaultShape2.hashCode() == defaultShape.hashCode());
  }
}
