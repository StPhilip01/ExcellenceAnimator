import static org.junit.Assert.assertEquals;

import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Position;
import cs3500.animator.model.shapes.RGBColor;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for AbstractAnimatorModel class. Provides methods to simultaneously test
 * concrete implementations.
 */
public abstract class AbstractAnimatorModelTest {

  protected abstract AnimatorModel model();

  /**
   * Concrete implementation for testing the {@link AnimatorModelImpl} class.
   */
  public static final class AnimatorModelImplTest extends AbstractAnimatorModelTest {

    @Override
    protected AnimatorModel model() {
      return new AnimatorModelImpl();
    }
  }

  // model initialized with a Rectangle: "R", and an Ellipse: "Ellipse"
  AnimatorModel model1;

  @Before
  public void init() {
    model1 = model();
    model1.createShape("R",
        new Rectangle(100, 100, new Position(0, 0), new RGBColor(0, 0, 0), false));
    model1.createShape("Ellipse",
        new Ellipse(20, 40.2, new Position(20, -2), new RGBColor(20, 120, 255), false));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateShapeAlreadyCreated() {
    model1.createShape("R", new Ellipse(1, 1, new Position(0, 0), new RGBColor(0, 0, 0), false));
  }

  @Test
  public void testCreateShape() {
    assertEquals(null, model1.findShape("Rectangle 2"));

    IShape shape = new Rectangle(10, 12, new Position(0, 0), new RGBColor(0, 0, 0), false);
    model1.createShape("Rectangle 2", shape);
    assertEquals(shape, model1.findShape("Rectangle 2"));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testFindShapeNullName() {
    IShape a = model1.findShape(null);
  }

  @Test
  public void testFindShape() {
    assertEquals(null, model1.findShape("Rectangle 2"));
    assertEquals(null, model1.findShape(""));
    assertEquals(new Rectangle(100, 100, new Position(0, 0), new RGBColor(0, 0, 0), false),
        model1.findShape("R"));
    assertEquals(new Ellipse(20, 40.2, new Position(20, -2), new RGBColor(20, 120, 255), false),
        model1.findShape("Ellipse"));
  }

  @Test
  public void testReset() {
    assertEquals(2, model1.getShapes().size());
    assertEquals(0, model1.getShapeActions("R").size());

    model1.addMotion("R", 1, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
    assertEquals(1, model1.getShapeActions("R").size());
    model1.addMotion("R", 10, 30, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
    assertEquals(2, model1.getShapeActions("R").size());

    model1.reset();
    assertEquals(0, model1.getShapeActions("R").size());

    assertEquals(0, model1.getShapes().size());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionNullName() {
    model1.addMotion(null, 1, 20, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionNameNotFound() {
    model1.addMotion("invalid shape", 1, 20, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidTick() {
    model1.addMotion("R", 1, -1, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionNulls() {
    model1.addMotion("R", 1, 20, null, 100, 200, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionNegativeDimensions() {
    model1.addMotion("R", 1, 20, new Position(0, 0), -100, -1, new RGBColor(0, 0, 0));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddOverlappingSameMotion() {
    model1.addMotion("R", 1, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
    model1.addMotion("R", 1, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddOverlappingMotionInBetween() {
    model1.addMotion("R", 1, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
    model1.addMotion("R", 2, 7, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionConflict() {
    model1.addMotion("R", 1, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
    model1.addMotion("R", 1, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionConflict2() {
    model1.addMotion("R", 1, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
    model1.addMotion("R", 8, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
  }

  @Test
  public void testAddMotion() {
    assertEquals(0, model1.getShapeActions("R").size());
    model1.addMotion("R", 1, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
    assertEquals(1, model1.getShapeActions("R").size());

    model1.addMotion("R", 10, 30, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
    assertEquals(2, model1.getShapeActions("R").size());

    // able to add motions that are overlapping in time but NOT in the same motion
    IShape ellipse = model1.findShape("Ellipse");
    assertEquals(0, model1.getShapeActions("Ellipse").size());

    model1.addMotion("Ellipse", 1, 10, ellipse.getCurrentPosition(), ellipse.getWidth(),
        ellipse.getHeight(), ellipse.getColor());
    assertEquals(1, model1.getShapeActions("Ellipse").size());

    model1.addMotion("Ellipse", 8, 15, new Position(0, 0), ellipse.getWidth(), ellipse.getHeight(),
        ellipse.getColor());
    assertEquals(2, model1.getShapeActions("Ellipse").size());

    model1.addMotion("Ellipse", 13, 16, ellipse.getCurrentPosition(), ellipse.getWidth(),
        ellipse.getHeight(),
        new RGBColor(100, 100, 100));
    assertEquals(3, model1.getShapeActions("Ellipse").size());
  }

  @Test
  public void testGetShapes() {
    assertEquals(0, model().getShapes().size());
    assertEquals(2, model1.getShapes().size());
    model1.createShape("Shape new",
        new Rectangle(1, 1, new Position(0, 0), new RGBColor(255, 255, 255), false));
    assertEquals(3, model1.getShapes().size());
    model1.reset();
    assertEquals(0, model1.getShapes().size());
  }

  @Test
  public void testGetShapeNames() {
    assertEquals(0, model().getShapeNames().size());
    assertEquals(2, model1.getShapeNames().size());
    model1.createShape("Shape new",
        new Rectangle(1, 1, new Position(0, 0), new RGBColor(255, 255, 255), false));
    assertEquals(3, model1.getShapeNames().size());
    model1.reset();
    assertEquals(0, model1.getShapeNames().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeAtNullName() {
    IShape shape = model1.shapeAt(null, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeAtInvalidTick() {
    IShape shape = model1.shapeAt("R", -1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testShapeAtInvalidBoth() {
    IShape shape = model1.shapeAt(null, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeAtNameNotFound() {
    IShape shape = model1.shapeAt("sanjana", 10);
  }

  @Test
  public void testShapeAt() {
    IShape rect = model1.findShape("R");
    assertEquals(rect, model1.shapeAt("R", 1));
    assertEquals(rect, model1.shapeAt("R", 100));

    model1.addMotion("R", 10, 20, rect.getCurrentPosition(), rect.getWidth() + 10, rect.getHeight(),
        rect.getColor());

    assertEquals(rect, model1.shapeAt("R", 10));
    IShape rectModified = new Rectangle(rect.getWidth() + 5, rect.getHeight(),
        rect.getCurrentPosition(), rect.getColor(), true);
    IShape shapeAt15 = model1.shapeAt("R", 15);

    IShape shapeAt20 = model1.shapeAt("R", 20);
    IShape finalRect = new Rectangle(rect.getWidth() + 10, rect.getHeight(),
        rect.getCurrentPosition(), rect.getColor(), true);

    assertEquals(finalRect, shapeAt20);
    assertEquals(rectModified, shapeAt15);
  }

  @Test
  public void getWidth() {
    assertEquals(500, model1.getWidth());
  }

  @Test
  public void getHeight() {
    assertEquals(500, model1.getHeight());
  }

  @Test
  public void getLeftX() {
    assertEquals(0, model1.getLeftX());
  }

  @Test
  public void getTopY() {
    assertEquals(0, model1.getTopY());
  }

  @Test
  public void getFinalTick() {
    assertEquals(0, model1.getFinalTick());
    model1.addMotion("R", 1, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
    assertEquals(10, model1.getFinalTick());
  }

  @Test
  public void getShapeActions() {
    assertEquals(0, model1.getShapeActions("").size());

    assertEquals(0, model1.getShapeActions("R").size());
    model1.addMotion("R", 1, 10, new Position(10, 10), 100, 200, new RGBColor(0, 0, 0));
    assertEquals(1, model1.getShapeActions("R").size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeShapeNullName() {
    model1.removeShape(null);
  }

  @Test
  public void removeShape() {
    assertEquals(2, model1.getShapes().size());
    model1.removeShape("R");
    assertEquals(1, model1.getShapes().size());
    model1.removeShape("Ellipse");
    assertEquals(0, model1.getShapes().size());
  }

  @Test
  public void testCreateShapeWithLayer() {
    assertEquals(null, model1.findShape("Rectangle 2"));

    IShape shape = new Rectangle(10, 12, new Position(0, 0), new RGBColor(0, 0, 0), false);
    model1.createLayer("Layer");
    model1.createShape("Rectangle 2", shape, "Layer");
    assertEquals(shape, model1.findShape("Rectangle 2"));
    assertEquals("Layer", model1.getShapeLayer("Rectangle 2"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateLayerDefault() {
    model1.createLayer("default");
  }

  @Test
  public void testCreateLayer() {
    assertEquals(1, model1.getLayers().size());
    model1.createLayer("Sanjana");
    assertEquals(2, model1.getLayers().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerDefault() {
    model1.removeLayer("fake");
  }

  @Test
  public void testRemoveLayer() {
    assertEquals(1, model1.getLayers().size());
    model1.removeLayer("default");
    assertEquals(0, model1.getLayers().size());
  }

  @Test
  public void testSwapLayers() {
    model1.createLayer("layer1");
    model1.createLayer("layer2");
    assertEquals(Arrays.asList("default", "layer1", "layer2"), model1.getLayers());
    model1.swapLayers("layer1", "layer2");
    assertEquals(Arrays.asList("default", "layer2", "layer1"), model1.getLayers());
    model1.swapLayers("layer1", "layer2");
    assertEquals(Arrays.asList("default", "layer1", "layer2"), model1.getLayers());
  }

  @Test
  public void testGetShapeLayer() {
    IShape shape = new Rectangle(10, 12, new Position(0, 0), new RGBColor(0, 0, 0), false);
    model1.createLayer("Layer");
    model1.createShape("Rectangle 2", shape, "Layer");
    model1.createShape("stefan", shape);

    assertEquals("Layer", model1.getShapeLayer("Rectangle 2"));
    assertEquals("default", model1.getShapeLayer("stefan"));
  }

  @Test
  public void getLayers() {
    assertEquals(Arrays.asList("default"), model1.getLayers());
    model1.createLayer("stefan");
    assertEquals(Arrays.asList("default", "stefan"), model1.getLayers());
    model1.createLayer("sanjana");
    assertEquals(Arrays.asList("default", "stefan", "sanjana"), model1.getLayers());
  }
}