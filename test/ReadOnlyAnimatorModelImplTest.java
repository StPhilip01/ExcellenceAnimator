import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.ReadOnlyAnimatorModel;
import cs3500.animator.model.ReadOnlyAnimatorModelImpl;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Position;
import cs3500.animator.model.shapes.RGBColor;
import cs3500.animator.model.shapes.Rectangle;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for {@link ReadOnlyAnimatorModelImpl} implementation of {@link
 * ReadOnlyAnimatorModel}.
 */
public class ReadOnlyAnimatorModelImplTest {

  // model initialized with shape R (rectangle)
  private ReadOnlyAnimatorModel model;

  @Before
  public void init() {
    AnimatorModel model1 = new AnimatorModelImpl();
    model1.createShape("R", new Rectangle(1, 1, new Position(0, 0), new RGBColor(0, 0, 0), true));
    this.model = new ReadOnlyAnimatorModelImpl(model1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNull() {
    ReadOnlyAnimatorModel model3 = new ReadOnlyAnimatorModelImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shapeAtNull() {
    IShape shape = model.shapeAt(null, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shapeAtNotFound() {
    assertEquals(0, model.shapeAt("sanjana", 0));
  }

  @Test
  public void getShapeNames() {
    ReadOnlyAnimatorModel newModel = new ReadOnlyAnimatorModelImpl(new AnimatorModelImpl());
    assertEquals(0, newModel.getShapeNames().size());
    assertEquals(1, model.getShapeNames().size());
  }

  @Test
  public void getWidth() {
    assertEquals(500, model.getWidth());
  }

  @Test
  public void getHeight() {
    assertEquals(500, model.getHeight());
  }

  @Test
  public void getLeftX() {
    assertEquals(0, model.getLeftX());
  }

  @Test
  public void getTopY() {
    assertEquals(0, model.getTopY());
  }

  @Test
  public void getFinalTick() {
    assertEquals(0, model.getFinalTick());
  }

  @Test
  public void getShapeActions() {
    assertEquals(0, model.getShapeActions("R").size());
  }

  @Test
  public void getLayers() {
    assertEquals(Arrays.asList("default"), model.getLayers());
  }

  @Test
  public void getShapeLayer() {
    assertEquals("default", model.getShapeLayer("R"));
  }

}