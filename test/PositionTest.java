import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.shapes.IPosition;
import cs3500.animator.model.shapes.Position;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for the {@link Position} implementation of {@link IPosition}.
 */
public class PositionTest {

  IPosition zero;

  @Before
  public void init() {
    zero = new Position(0, 0);
  }

  @Test
  public void testGetX() {
    assertEquals(0, zero.getX(), .0001);
    assertEquals(-10, new Position(-10.0, 0).getX(), .0001);
    assertEquals(10, new Position(10.0, 0).getX(), .0001);
    assertEquals(.5, new Position(.5, 0).getX(), .0001);
  }

  @Test
  public void testGetY() {
    assertEquals(0, zero.getY(), .0001);
    assertEquals(-10, new Position(0, -10).getY(), .0001);
    assertEquals(10, new Position(0, 10).getY(), .0001);
    assertEquals(.5, new Position(0, .5).getY(), .0001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangePositionNull() {
    zero.changePosition(null);
  }

  @Test
  public void testChangePositionSamePosition() {
    assertEquals(0, zero.getX(), .0001);
    assertEquals(0, zero.getY(), .0001);
    zero.changePosition(new Position(0, 0));
    assertEquals(0, zero.getX(), .0001);
    assertEquals(0, zero.getY(), .0001);
  }


  @Test
  public void testChangePositionChangeX() {
    assertEquals(0, zero.getX(), .0001);
    assertEquals(0, zero.getY(), .0001);
    zero.changePosition(new Position(100, 0));
    assertEquals(100, zero.getX(), .0001);
    assertEquals(0, zero.getY(), .0001);
  }


  @Test
  public void testChangePositionChangeY() {
    assertEquals(0, zero.getX(), .0001);
    assertEquals(0, zero.getY(), .0001);
    zero.changePosition(new Position(0, 100));
    assertEquals(0, zero.getX(), .0001);
    assertEquals(100, zero.getY(), .0001);
  }

  @Test
  public void testChangePositionChangeXY() {
    assertEquals(0, zero.getX(), .0001);
    assertEquals(0, zero.getY(), .0001);
    zero.changePosition(new Position(200, 100));
    assertEquals(200, zero.getX(), .0001);
    assertEquals(100, zero.getY(), .0001);
  }

  @Test
  public void testCopy() {
    IPosition copied = zero.copy();
    assertEquals(0, copied.getX(), .0001);
    assertEquals(0, copied.getY(), .0001);
    assertTrue(copied.equals(zero));
    assertFalse(copied == zero);
  }

  @Test
  public void testEquals() {
    IPosition quadrantThree = new Position(-100, -100);
    IPosition negativeOne = new Position(-1, -1);
    IPosition zero2 = new Position(0, 0);

    assertTrue(zero.equals(zero2));
    assertTrue(zero2.equals(zero));
    assertFalse(zero.equals(quadrantThree));
    assertFalse(quadrantThree.equals(zero));
    assertFalse(negativeOne.equals(quadrantThree));
    assertFalse(quadrantThree.equals(negativeOne));
    assertTrue(zero.equals(zero));
    assertTrue(negativeOne.equals(negativeOne));
    assertFalse(zero == null);
  }

  @Test
  public void testHashCode() {
    IPosition quadrantThree = new Position(-100, -100);
    IPosition negativeOne = new Position(-1, -1);
    IPosition zero2 = new Position(0, 0);

    assertEquals(zero.hashCode(), zero.hashCode());
    assertEquals(zero.hashCode(), zero2.hashCode());
    assertFalse(zero.hashCode() == negativeOne.hashCode());
    assertFalse(quadrantThree.hashCode() == negativeOne.hashCode());

  }


}