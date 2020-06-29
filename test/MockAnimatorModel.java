import cs3500.animator.model.IKeyframeModel;
import cs3500.animator.model.action.Action;
import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IPosition;
import cs3500.animator.model.shapes.IShape;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mock animator model used to confirm that inputs are being passed correctly by the
 * controller.
 */
final class MockAnimatorModel implements IKeyframeModel {

  private final Appendable out;

  /**
   * Constructs a mock animator model.
   *
   * @param appender log to append output to
   */
  MockAnimatorModel(Appendable appender) {
    Objects.requireNonNull(appender);
    this.out = appender;
  }

  @Override
  public void createShape(String name, IShape shape) {
    write("Create Shape " + name + " " + shape.toString());
  }

  @Override
  public void createShape(String name, IShape shape, String layerName) {
    write("Create Shape " + name + " " + shape.toString() + " " + layerName);
  }

  @Override
  public IShape findShape(String name) {
    write("Find Shape " + name);
    return null;
  }

  @Override
  public IShape shapeAt(String name, int tick) {
    write("Shape at " + name + " " + tick);
    return null;
  }

  @Override
  public List<IShape> getShapes() {
    return null;
  }

  @Override
  public List<String> getShapeNames() {
    return null;
  }

  @Override
  public void addMotion(String name, int firstTick, int finalTick, IPosition toPosition,
      double toWidth, double toHeight, IColor toColor) {
    write(String
        .format("Add Motion: %s %d %d %d %d %.2f %.2f %.2f %.2f %.2f", name, firstTick, finalTick,
            toPosition.getX(), toPosition.getY(), toWidth, toHeight, toColor.getRed(),
            toColor.getGreen(), toColor.getBlue()));
  }

  @Override
  public void reset() {
    return;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public int getLeftX() {
    return 0;
  }

  @Override
  public int getTopY() {
    return 0;
  }

  @Override
  public int getFinalTick() {
    return 0;
  }

  @Override
  public List<Action> getShapeActions(String shapeName) {
    write("Get Shape Actions: " + shapeName);
    return null;
  }

  @Override
  public void removeShape(String shapeName) {
    write("Remove Shape: " + shapeName);
  }

  @Override
  public void createLayer(String layerName) {
    write("Create Layer " + layerName);
  }

  @Override
  public void swapLayers(String layer1, String layer2) {
    write("Swap Layers" + layer1 + " " + layer2);
  }

  @Override
  public void removeLayer(String layerName) {
    write("Remove Layer " + layerName);
  }

  @Override
  public String getShapeLayer(String shapeName) {
    write("Get Layer for" + shapeName);
    return "default";
  }

  @Override
  public List<String> getLayers() {
    write("Get Layers");
    return new ArrayList<>();
  }

  private void write(String message) {
    try {
      out.append(message + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addKeyframe(String name, int tick, IPosition toPosition, double toWidth,
      double toHeight, IColor toColor, int rotation) {
    write(String.format("Add Keyframe: %s %d %.2f %.2f %.2f %.2f %.2f %.2f %.2f %d", name, tick,
        toPosition.getX(), toPosition.getY(), toWidth, toHeight, toColor.getRed(),
        toColor.getGreen(), toColor.getBlue(), rotation));
  }

  @Override
  public void removeKeyframe(String name, int tick) {
    write("Remove Keyframe: " + name + " " + tick);
  }
}
