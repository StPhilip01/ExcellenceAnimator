import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.action.Action;
import cs3500.animator.model.action.ActionImpl;
import cs3500.animator.model.action.KeyframeAction;
import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Position;
import cs3500.animator.model.shapes.RGBColor;
import cs3500.animator.model.shapes.Rectangle;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit tests for the {@link cs3500.animator.model.action.KeyframeAction} implementation
 * of {@link Action}.
 */
public class KeyframeActionTest {

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
    defaultAction = new KeyframeAction("R", rect, 1, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight(), rect.getColor());

    ellipse = new Ellipse(20, 20, new Position(0, 0), new RGBColor(255, 255, 255), false);
    growEllipse = new KeyframeAction("C", ellipse, 10, ellipse.getCurrentPosition(),
        120, 220, ellipse.getColor());

    shrinkEllipse = new KeyframeAction("shrunk", ellipse, 40, ellipse.getCurrentPosition(),
        20, 30.4, ellipse.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullShape() {
    Action a = new KeyframeAction("R", null, 10, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight(), rect.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullPosition() {
    Action a = new KeyframeAction("R", rect, 10, null, rect.getWidth(),
        rect.getHeight(), rect.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullColor() {
    Action a = new KeyframeAction("R", rect, 10, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullShapeName() {
    Action a = new KeyframeAction(null, rect, 10, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight(), rect.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNegativeTick() {
    Action a = new KeyframeAction("shape", rect, -1, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight(), rect.getColor());
  }

  @Test
  public void testExecuteSameShape() {
    assertEquals(100, rect.getWidth(), .0001);
    assertEquals(100, rect.getHeight(), .0001);
    assertFalse(rect.getVisibility());
    assertEquals(new Position(0, 0), rect.getCurrentPosition());
    assertEquals(new RGBColor(0, 0, 255), rect.getColor());

    defaultAction.execute();

    assertEquals(100, rect.getWidth(), .0001);
    assertEquals(100, rect.getHeight(), .0001);
    assertTrue(rect.getVisibility());
    assertEquals(new Position(0, 0), rect.getCurrentPosition());
    assertEquals(new RGBColor(0, 0, 255), rect.getColor());
  }

  @Test
  public void testExecuteGrowEllipse() {
    assertEquals(20, ellipse.getWidth(), .0001);
    assertEquals(20, ellipse.getHeight(), .0001);
    assertFalse(ellipse.getVisibility());
    assertEquals(new Position(0, 0), ellipse.getCurrentPosition());
    assertEquals(new RGBColor(255, 255, 255), ellipse.getColor());

    growEllipse.execute();

    assertEquals(20, ellipse.getWidth(), .0001);
    assertEquals(20, ellipse.getHeight(), .0001);
    assertTrue(ellipse.getVisibility());
    assertEquals(new Position(0, 0), ellipse.getCurrentPosition());
    assertEquals(new RGBColor(255, 255, 255), ellipse.getColor());
  }

  @Test
  public void testExecuteShrinkEllipse() {
    assertEquals(20, ellipse.getWidth(), .0001);
    assertEquals(20, ellipse.getHeight(), .0001);
    assertFalse(ellipse.getVisibility());
    assertEquals(new Position(0, 0), ellipse.getCurrentPosition());
    assertEquals(new RGBColor(255, 255, 255), ellipse.getColor());

    shrinkEllipse.execute();

    assertEquals(20, ellipse.getWidth(), .0001);
    assertEquals(20, ellipse.getHeight(), .0001);
    assertTrue(ellipse.getVisibility());
    assertEquals(new Position(0, 0), ellipse.getCurrentPosition());
    assertEquals(new RGBColor(255, 255, 255), ellipse.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorGrowToNegativeWidth() {
    Action shrinkTooSmall = new ActionImpl("toosmall!", ellipse, 10, 40,
        ellipse.getCurrentPosition(),
        -1, 200, ellipse.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorGrowToNegativeHeight() {
    Action shrinkTooSmall = new ActionImpl("toosmall!", ellipse, 10, 40,
        ellipse.getCurrentPosition(),
        200, -2, ellipse.getColor());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testConstructorGrowToNegativeHeightWidth() {
    Action shrinkTooSmall = new ActionImpl("toosmall!", ellipse, 10, 40,
        ellipse.getCurrentPosition(),
        -20, -2, ellipse.getColor());
  }


  @Test
  public void testExecuteMoveEllipse() {
    assertEquals(20, ellipse.getWidth(), .0001);
    assertEquals(20, ellipse.getHeight(), .0001);
    assertFalse(ellipse.getVisibility());
    assertEquals(new Position(0, 0), ellipse.getCurrentPosition());
    assertEquals(new RGBColor(255, 255, 255), ellipse.getColor());

    Action moveEllipse = new ActionImpl("moved", ellipse, 10, 40, new Position(100, 10),
        ellipse.getWidth(), ellipse.getHeight(), ellipse.getColor());
    moveEllipse.execute();

    assertEquals(20, ellipse.getWidth(), .0001);
    assertEquals(20, ellipse.getHeight(), .0001);
    assertTrue(ellipse.getVisibility());
    assertEquals(new Position(100, 10), ellipse.getCurrentPosition());
    assertEquals(new RGBColor(255, 255, 255), ellipse.getColor());
  }

  @Test
  public void testExecuteChangeColor() {
    assertEquals(20, ellipse.getWidth(), .0001);
    assertEquals(20, ellipse.getHeight(), .0001);
    assertFalse(ellipse.getVisibility());
    assertEquals(new Position(0, 0), ellipse.getCurrentPosition());
    assertEquals(new RGBColor(255, 255, 255), ellipse.getColor());

    Action changeColor = new ActionImpl("color", ellipse, 10, 40, ellipse.getCurrentPosition(),
        ellipse.getWidth(), ellipse.getHeight(), new RGBColor(24, 200, 100));
    changeColor.execute();

    assertEquals(20, ellipse.getWidth(), .0001);
    assertEquals(20, ellipse.getHeight(), .0001);
    assertTrue(ellipse.getVisibility());
    assertEquals(new Position(0, 0), ellipse.getCurrentPosition());
    assertEquals(new RGBColor(24, 200, 100), ellipse.getColor());
  }

  @Test
  public void testExecuteChangeAll() {
    assertEquals(20, ellipse.getWidth(), .0001);
    assertEquals(20, ellipse.getHeight(), .0001);
    assertFalse(ellipse.getVisibility());
    assertEquals(new Position(0, 0), ellipse.getCurrentPosition());
    assertEquals(new RGBColor(255, 255, 255), ellipse.getColor());

    Action changeColor = new ActionImpl("color", ellipse, 10, 13, new Position(20, -10.4),
        20.4, 100.2, new RGBColor(10, 49, 10));
    changeColor.execute();

    assertEquals(20.4, ellipse.getWidth(), .0001);
    assertEquals(100.2, ellipse.getHeight(), .0001);
    assertTrue(ellipse.getVisibility());
    assertEquals(new Position(20, -10.4), ellipse.getCurrentPosition());
    assertEquals(new RGBColor(10, 49, 10), ellipse.getColor());
  }

  @Test
  public void testGetShapeName() {
    assertEquals("R", defaultAction.getShapeName());
    assertEquals("shrunk", shrinkEllipse.getShapeName());
    assertEquals("C", growEllipse.getShapeName());
  }

  @Test
  public void testGetShape() {
    assertEquals(rect, defaultAction.getShape());
    assertEquals(ellipse, shrinkEllipse.getShape());
    assertEquals(ellipse, growEllipse.getShape());

    assertEquals(rect.copy(), defaultAction.getShape());
    assertEquals(ellipse.copy(), shrinkEllipse.getShape());
    assertEquals(ellipse.copy(), growEllipse.getShape());
  }

  @Test
  public void testGetFirstTick() {
    assertEquals(1, defaultAction.getFirstTick());
    assertEquals(40, shrinkEllipse.getFirstTick());
    assertEquals(10, growEllipse.getFirstTick());
  }


  @Test
  public void testGetFinalTick() {
    assertEquals(1, defaultAction.getFinalTick());
    assertEquals(40, shrinkEllipse.getFinalTick());
    assertEquals(10, growEllipse.getFinalTick());
  }

  @Test
  public void testGetToPosition() {
    assertEquals(new Position(0, 0), defaultAction.getToPosition());
    assertEquals(new Position(0, 0), shrinkEllipse.getToPosition());
    assertEquals(new Position(0, 0), growEllipse.getToPosition());

    Action moveEllipse = new KeyframeAction("moved", ellipse, 10, new Position(100, 10),
        ellipse.getWidth(), ellipse.getHeight(), ellipse.getColor());

    assertEquals(new Position(100, 10), moveEllipse.getToPosition());
  }

  @Test
  public void testGetToWidth() {
    assertEquals(100, defaultAction.getToWidth(), .0001);
    assertEquals(20, shrinkEllipse.getToWidth(), .0001);
    assertEquals(120, growEllipse.getToWidth(), .0001);
  }

  @Test
  public void testGetToHeight() {
    assertEquals(100, defaultAction.getToHeight(), .0001);
    assertEquals(30.4, shrinkEllipse.getToHeight(), .0001);
    assertEquals(220, growEllipse.getToHeight(), .0001);
  }

  @Test
  public void testGetToColor() {
    assertEquals(new RGBColor(0, 0, 255), defaultAction.getToColor());
    assertEquals(new RGBColor(255, 255, 255), shrinkEllipse.getToColor());
    assertEquals(new RGBColor(255, 255, 255), growEllipse.getToColor());

    Action changeColor = new ActionImpl("color", ellipse, 10, 40, ellipse.getCurrentPosition(),
        ellipse.getWidth(), ellipse.getHeight(), new RGBColor(24, 200, 100));
    changeColor.execute();

    assertEquals(new RGBColor(24, 200, 100), changeColor.getToColor());
  }

  @Test
  public void testConflictsWithNoConflict() {
    Action moveEllipse = new ActionImpl("moved", ellipse, 10, 40, new Position(100, 10),
        ellipse.getWidth(), ellipse.getHeight(), ellipse.getColor());

    assertTrue(moveEllipse.conflictsWith(moveEllipse));

    Action moveEllipse2 = new ActionImpl("moved", ellipse, 1, 6, new Position(20, 0),
        ellipse.getWidth(), ellipse.getHeight(), ellipse.getColor());

    assertFalse(moveEllipse.conflictsWith(moveEllipse2));
    assertFalse(moveEllipse2.conflictsWith(moveEllipse));

    Action moveEllipse3 = new ActionImpl("moved", ellipse, 40, 42, new Position(10, 10),
        ellipse.getWidth(), ellipse.getHeight(), ellipse.getColor());

    assertFalse(moveEllipse.conflictsWith(moveEllipse3));
    assertFalse(moveEllipse3.conflictsWith(moveEllipse));

    Action colorEllipse = new ActionImpl("moved", ellipse, 40, 42, ellipse.getCurrentPosition(),
        ellipse.getWidth(), ellipse.getHeight(), new RGBColor(0, 0, 20));

    assertFalse(moveEllipse.conflictsWith(colorEllipse));
    assertFalse(colorEllipse.conflictsWith(moveEllipse));

    Action colorEllipseDifferentName = new ActionImpl("moved2", ellipse, 40, 42,
        ellipse.getCurrentPosition(),
        ellipse.getWidth(), ellipse.getHeight(), new RGBColor(0, 0, 20));
    assertFalse(colorEllipse.conflictsWith(colorEllipseDifferentName));
    assertFalse(colorEllipseDifferentName.conflictsWith(colorEllipse));

  }

  @Test
  public void testConflictsWithMove() {
    Action moveEllipse = new ActionImpl("moved", ellipse, 10, 40, new Position(100, 10),
        ellipse.getWidth(), ellipse.getHeight(), ellipse.getColor());

    Action moveEllipseConflict = new ActionImpl("moved", ellipse, 8, 100, new Position(20, 400),
        ellipse.getWidth(), ellipse.getHeight(), ellipse.getColor());

    assertTrue(moveEllipse.conflictsWith(moveEllipseConflict));
    assertTrue(moveEllipseConflict.conflictsWith(moveEllipse));

    Action moveEllipseConflict2 = new ActionImpl("moved", ellipse, 38, 100, new Position(20, 400),
        ellipse.getWidth(), ellipse.getHeight(), ellipse.getColor());

    assertTrue(moveEllipse.conflictsWith(moveEllipseConflict2));
    assertTrue(moveEllipseConflict2.conflictsWith(moveEllipse));

  }

  @Test
  public void testConflictsWithSize() {
    Action sizeEllipse = new ActionImpl("sized", ellipse, 20, 100, ellipse.getCurrentPosition(),
        10, ellipse.getHeight(), ellipse.getColor());

    Action sizeEllipse2 = new ActionImpl("sized", ellipse, 30, 80, ellipse.getCurrentPosition(),
        10, ellipse.getHeight(), ellipse.getColor());

    assertTrue(sizeEllipse.conflictsWith(sizeEllipse2));
    assertTrue(sizeEllipse2.conflictsWith(sizeEllipse));

    Action sizeEllipseHeight = new ActionImpl("sized", ellipse, 20, 100,
        ellipse.getCurrentPosition(),
        ellipse.getWidth(), 10, ellipse.getColor());

    Action sizeEllipseHeight2 = new ActionImpl("sized", ellipse, 1, 30,
        ellipse.getCurrentPosition(),
        ellipse.getWidth(), 10, ellipse.getColor());

    assertTrue(sizeEllipseHeight.conflictsWith(sizeEllipseHeight2));
    assertTrue(sizeEllipseHeight2.conflictsWith(sizeEllipseHeight));

    Action sizeEllipseBoth = new ActionImpl("sized", ellipse, 5, 15, ellipse.getCurrentPosition(),
        20, 10, ellipse.getColor());

    Action sizeEllipseBoth2 = new ActionImpl("sized", ellipse, 14, 18, ellipse.getCurrentPosition(),
        14, 1000, ellipse.getColor());

    assertTrue(sizeEllipseBoth.conflictsWith(sizeEllipseBoth2));
    assertTrue(sizeEllipseBoth2.conflictsWith(sizeEllipseBoth));
  }

  @Test
  public void testConflictsWithColor() {
    Action colorEllipse = new ActionImpl("color", ellipse, 10, 40, ellipse.getCurrentPosition(),
        ellipse.getWidth(), ellipse.getHeight(), new RGBColor(0, 24, 23));

    Action colorEllipse2 = new ActionImpl("color", ellipse, 10, 40, ellipse.getCurrentPosition(),
        ellipse.getWidth(), ellipse.getHeight(), new RGBColor(0, 200, 23));

    assertTrue(colorEllipse.conflictsWith(colorEllipse2));
    assertTrue(colorEllipse2.conflictsWith(colorEllipse));

    Action colorEllipse3 = new ActionImpl("color", ellipse, 10, 40, ellipse.getCurrentPosition(),
        ellipse.getWidth(), ellipse.getHeight(), new RGBColor(0, 200, 23));

    assertTrue(colorEllipse.conflictsWith(colorEllipse3));
    assertTrue(colorEllipse3.conflictsWith(colorEllipse));
  }


  @Test
  public void testConflictsWithAll() {
    Action motionEllipse = new ActionImpl("transformed", ellipse, 1, 100, new Position(0, -1023),
        69, 420, new RGBColor(69, 69, 69));

    Action motionEllipse2 = new ActionImpl("transformed", ellipse, 42, 69, new Position(6.9420, 10),
        420, 6969, new RGBColor(4.20, 42, 6.9));

    assertTrue(motionEllipse.conflictsWith(motionEllipse2));
    assertTrue(motionEllipse2.conflictsWith(motionEllipse));

    Action motionEllipse3 = new ActionImpl("transformed", ellipse, 99, 101,
        new Position(6.9420, 10),
        420, 6969, new RGBColor(4.20, 42, 6.9));

    assertTrue(motionEllipse.conflictsWith(motionEllipse3));
    assertTrue(motionEllipse3.conflictsWith(motionEllipse));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteInvalidTick() {
    Action a = new ActionImpl("ellipse", ellipse, 10, 20, ellipse.getCurrentPosition(),
        ellipse.getWidth(), ellipse.getHeight(), ellipse.getColor());
    a.tickExecute(9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteInvalidTick2() {
    Action a = new ActionImpl("ellipse", ellipse, 10, 20, ellipse.getCurrentPosition(),
        ellipse.getWidth(), ellipse.getHeight(), ellipse.getColor());
    a.tickExecute(21);
  }

  @Test
  public void testTickExecute() {
    Action a = new KeyframeAction("r", rect, 8, new Position(10, 10), rect.getWidth(),
        rect.getHeight(), rect.getColor());
    a.tickExecute(9);
    assertEquals(new Position(0, 0), rect.getCurrentPosition());

    // add 4 to the rectangle's height in 4 ticks. (1 unit per tick)
    // after 2 ticks, it should go from 100 to 102
    Action a2 = new KeyframeAction("r", rect, 12, rect.getCurrentPosition(), rect.getWidth(),
        rect.getHeight() + 4, rect.getColor());
    a2.tickExecute(10);
    assertEquals(100, rect.getHeight(), .0001);
  }

  @Test
  public void testToString() {
    assertEquals("time=1,  pos=(0,0), size=(100,100), color=(0,0,255)", defaultAction.toString());
    assertEquals("time=10,  pos=(0,0), size=(120,220), color=(255,255,255)",
        growEllipse.toString());
    assertEquals("time=40,  pos=(0,0), size=(20,30), color=(255,255,255)",
        shrinkEllipse.toString());
  }

}