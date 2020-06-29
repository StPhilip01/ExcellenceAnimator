import static org.junit.Assert.assertEquals;

import cs3500.animator.model.IKeyframeModel;
import cs3500.animator.model.KeyframeModel;
import cs3500.animator.model.action.KeyframeAction;
import cs3500.animator.model.shapes.Position;
import cs3500.animator.model.shapes.RGBColor;
import cs3500.animator.model.shapes.Rectangle;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for a {@link KeyframeModel}. Tests additional functionality not tested by
 * {@link AbstractAnimatorModelTest}.
 */
public class KeyframeModelTest {

  IKeyframeModel defaultModel;
  IKeyframeModel model;

  @Before
  public void init() {
    defaultModel = new KeyframeModel();
    model = new KeyframeModel(100, 200, 500, 200);
    model.createShape("Sanjana",
        new Rectangle(10, 20, new Position(0, 0), new RGBColor(10, 20, 30), false));
    model.addKeyframe("Sanjana", 3, new Position(10, 100), 20, 10, new RGBColor(200, 3, 100), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDimensions() {
    IKeyframeModel m = new KeyframeModel(0, 0, -1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shapeAtFail() {
    model.shapeAt(null, -1);
  }

  @Test
  public void shapeAt() {
    assertEquals(new Rectangle(10, 20, new Position(0, 0), new RGBColor(10, 20, 30), false),
        model.shapeAt("Sanjana", 0));
    assertEquals(new Rectangle(20, 10, new Position(10, 100), new RGBColor(200, 3, 100), true),
        model.shapeAt("Sanjana", 3));
    assertEquals(new Rectangle(20, 10, new Position(10, 100), new RGBColor(200, 3, 100), true),
        model.shapeAt("Sanjana", 1000));
    model.addKeyframe("Sanjana", 200, new Position(2, 203), 20, 10, new RGBColor(100, 200, 3), 0);

    assertEquals(new Rectangle(20, 10, new Position(10, 100), new RGBColor(200, 3, 100), true),
        model.shapeAt("Sanjana", 3));
    assertEquals(new Rectangle(20, 10, new Position(2, 203), new RGBColor(100, 200, 3), true),
        model.shapeAt("Sanjana", 1000));
    assertEquals(new Rectangle(20, 10, new Position(9, 103),
            new RGBColor(196.4467005076142, 10, 96.55329949238579), true),
        model.shapeAt("Sanjana", 10));

  }

  @Test(expected = UnsupportedOperationException.class)
  public void addMotion() {
    model.addMotion("Sanjana", 1, 2, new Position(10, 200), 10, 20, new RGBColor(0, 0, 0));
  }

  @Test()
  public void addKeyframe() {
    assertEquals(1, model.getShapeActions("Sanjana").size());
    model.addKeyframe("Sanjana", 200, new Position(2, 203), 20, 10, new RGBColor(100, 200, 3), 0);
    assertEquals(2, model.getShapeActions("Sanjana").size());
    assertEquals(
        new KeyframeAction("Sanjana", model.findShape("Sanjana"), 200, new Position(2, 203), 20, 10,
            new RGBColor(100, 200, 3)), model.getShapeActions("Sanjana").get(1));

  }

  @Test(expected = IllegalArgumentException.class)
  public void addKeyframeFail() {
    model.addKeyframe("Sanjana", 3, new Position(2, 203), 20, 10, new RGBColor(100, 200, 3), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeKeyframeBadInputs() {
    model.removeKeyframe(null, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeKeyframeNameNotFound() {
    model.removeKeyframe("Stefan", 10);
  }

  @Test
  public void removeKeyframe() {
    assertEquals(1, model.getShapeActions("Sanjana").size());
    model.removeKeyframe("Sanjana", 3);
    assertEquals(0, model.getShapeActions("Sanjana").size());
  }
}