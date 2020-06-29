package cs3500.animator.model;

import cs3500.animator.model.action.Action;
import cs3500.animator.model.action.RotationActionImpl;
import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IPosition;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Position;
import cs3500.animator.model.shapes.RGBColor;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.util.AnimationBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of an {@link IKeyframeModel} that stores shapes and keyframes for the shapes, and
 * also canvas bounds information. This model has functionality for adding and removing keyframes
 * for shapes, and does not support adding "motions" between two ticks.
 */
public class KeyframeModel extends AbstractAnimatorModel implements IKeyframeModel {

  /**
   * Constructor that calls the constructor of {@link AbstractAnimatorModel} and uses its default
   * fields.
   */
  public KeyframeModel() {
    super();
  }

  /**
   * Constructor that calls the constructor of {@link AbstractAnimatorModel} with the given canvas
   * bound parameters.
   *
   * @throws IllegalArgumentException if the dimensions are invalid
   */
  public KeyframeModel(int x, int y, int widthBound, int heightBound) {
    super(x, y, widthBound, heightBound);
  }

  @Override
  public IShape shapeAt(String name, int tick) {
    if (name == null || shapes.get(name) == null || tick < 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }

    IShape startingShape = shapes.get(name).copy();

    List<Action> actions = super.getShapeActions(name);
    if (actions.size() == 0) {
      startingShape.setVisibility(false);
      return startingShape;
    } else {
      int minTick = super.getShapeActions(name).get(0).getFirstTick();
      for (Action a : super.getShapeActions(name)) {
        minTick = Math.min(minTick, a.getFirstTick());
      }
      if (tick < minTick) {
        startingShape.setVisibility(false);
        return startingShape;
      }
    }

    startingShape.setVisibility(true);

    int closestActionDistance = Math.abs(actions.get(0).getFirstTick() - tick);
    Action closestAction = actions.get(0);

    for (Action a : actions) {
      if (a.getFirstTick() == tick) {
        // found an action with the exact tick
        startingShape.move(a.getToPosition());
        startingShape.changeColor(a.getToColor());
        startingShape.grow(a.getToWidth() - startingShape.getWidth(),
            a.getToHeight() - startingShape.getHeight());
        startingShape.rotateTo(a.getToDegrees());
        return startingShape;
      }

      int currentActionDistance = Math.abs(a.getFirstTick() - tick);
      closestActionDistance = Math.min(closestActionDistance, currentActionDistance);
      if (closestActionDistance == currentActionDistance) {
        closestAction = a;
      }
    }

    // if the closest action is the last motion and it happens before the given tick
    // OR if the closest action is the first motion and it happens after the given tick
    if ((closestAction.getFirstTick() < tick
        && actions.indexOf(closestAction) == actions.size() - 1)
        || (closestAction.getFirstTick() > tick && actions.indexOf(closestAction) == 0)) {
      startingShape.move(closestAction.getToPosition());
      startingShape.changeColor(closestAction.getToColor());
      startingShape.grow(closestAction.getToWidth() - startingShape.getWidth(),
          closestAction.getToHeight() - startingShape.getHeight());
      startingShape.rotateTo(closestAction.getToDegrees());
      return startingShape;
    }

    if (closestAction.getFirstTick() < tick) {
      Action actionAfter = actions.get(actions.indexOf(closestAction) + 1);
      // tween between closest and actionAfter states
      return tweenShape(startingShape, tick, closestAction, actionAfter);
    } else if (closestAction.getFirstTick() > tick) {
      Action actionBefore = actions.get(actions.indexOf(closestAction) - 1);
      // tween between closest and actionBefore states
      return tweenShape(startingShape, tick, actionBefore, closestAction);
    } else {
      startingShape.setVisibility(false);
      return startingShape;
    }
  }

  // Linear interpolation for shape properties
  private IShape tweenShape(IShape startingShape, int tick, Action actionBefore,
      Action actionAfter) {

    double timeRatioA =
        1.0 * (actionAfter.getFirstTick() - tick) / (1.0 * (actionAfter.getFirstTick()
            - actionBefore.getFirstTick()));
    double timeRatioB =
        1.0 * (tick - actionBefore.getFirstTick()) / (1.0 * (actionAfter.getFirstTick()
            - actionBefore.getFirstTick()));

    double newWidth =
        actionBefore.getToWidth() * timeRatioA + actionAfter.getToWidth() * timeRatioB;
    double newHeight =
        actionBefore.getToHeight() * timeRatioA + actionAfter.getToHeight() * timeRatioB;

    double newX = actionBefore.getToPosition().getX() * timeRatioA
        + actionAfter.getToPosition().getX() * timeRatioB;
    double newY = actionBefore.getToPosition().getY() * timeRatioA
        + actionAfter.getToPosition().getY() * timeRatioB;

    double newRotation =
        actionBefore.getToDegrees() * timeRatioA + actionAfter.getToDegrees() * timeRatioB;

    double[] closestRGB = actionBefore.getToColor().getRGB();
    double[] afterRGB = actionAfter.getToColor().getRGB();

    double newRed = closestRGB[0] * timeRatioA + afterRGB[0] * timeRatioB;
    double newGreen = closestRGB[1] * timeRatioA + afterRGB[1] * timeRatioB;
    double newBlue = closestRGB[2] * timeRatioA + afterRGB[2] * timeRatioB;

    startingShape.move(new Position((int) newX, (int) newY));
    startingShape.grow((int) (newWidth - startingShape.getWidth()),
        (int) (newHeight - startingShape.getHeight()));
    startingShape.changeColor(new RGBColor(newRed, newGreen, newBlue));
    startingShape.rotateTo((int) newRotation);
    return startingShape;
  }

  @Override
  public void addMotion(String name, int firstTick, int finalTick, IPosition toPosition,
      double toWidth, double toHeight, IColor toColor) {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public void addKeyframe(String name, int tick, IPosition toPosition,
      double toWidth, double toHeight, IColor toColor, int rotation)
      throws IllegalArgumentException {

    if (name == null) {
      throw new IllegalArgumentException("Name is null");
    }

    IShape shape = shapes.get(name);
    if (shape == null) {
      throw new IllegalArgumentException("Invalid shape");
    }

    Action action = new RotationActionImpl(name, shape, tick, toPosition, toWidth, toHeight,
        toColor, rotation);

    super.validateAction(action);

    actionList.add(action);
    actionList.sort(Comparator.comparingInt(Action::getFirstTick));
  }

  @Override
  public void removeKeyframe(String name, int tick) throws IllegalArgumentException {
    if (name == null || tick < 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    IShape shape = shapes.get(name);
    if (shape == null) {
      throw new IllegalArgumentException("Shape not found");
    }
    actionList.removeIf(a -> a.getFirstTick() == tick);
  }

  /**
   * Builder class to create a {@link IKeyframeModel} by adding configurations, shapes, and motions
   * to an animator model.
   */

  public static final class Builder implements AnimationBuilder<IKeyframeModel> {

    private int leftX;
    private int topY;
    private int widthBound;
    private int heightBound;

    private final Map<String, IShape> shapes;
    private final List<Action> actionListBeginningConditions;
    private final List<Action> actionList;

    private final Map<String, List<String>> layers;

    /**
     * Builder class that initializes the parameters to default values defined in the {@link
     * AnimatorModel} interface, and empty lists of shapes and actions.
     */
    public Builder() {
      this.leftX = AnimatorModel.DEFAULT_LEFT_X;
      this.topY = AnimatorModel.DEFAULT_TOP_Y;
      this.widthBound = AnimatorModel.DEFAULT_WIDTH_BOUND;
      this.heightBound = AnimatorModel.DEFAULT_HEIGHT_BOUND;
      this.shapes = new LinkedHashMap<>();
      this.actionList = new ArrayList<>();
      this.actionListBeginningConditions = new ArrayList<>();

      this.layers = new LinkedHashMap<>();
    }

    @Override
    public IKeyframeModel build() {
      IKeyframeModel model = new KeyframeModel(leftX, topY, widthBound, heightBound);

      for (String layerName : layers.keySet()) {
        if (!layerName.equals("default")) {
          model.createLayer(layerName);
        }
      }

      for (Map.Entry<String, List<String>> entry : layers.entrySet()) {
        String layerName = entry.getKey();

        for (String name : entry.getValue()) {
          for (Action a : actionListBeginningConditions) {
            if (a.getShapeName().equals(name)) {
              shapes.get(name).move(a.getToPosition());
              shapes.get(name).changeColor(a.getToColor());
              shapes.get(name).grow(a.getToWidth(), a.getToHeight());
              break;
            }
          }
          model.createShape(name, shapes.get(name), layerName);
        }
      }

      for (Action a : actionList) {
        model.addKeyframe(a.getShapeName(), a.getFirstTick(), a.getToPosition(),
            a.getToWidth(), a.getToHeight(), a.getToColor(), a.getToDegrees());
      }

      return model;
    }

    @Override
    public AnimationBuilder<IKeyframeModel> setBounds(int x, int y, int width, int height) {
      this.leftX = x;
      this.topY = y;
      this.widthBound = width;
      this.heightBound = height;
      return this;
    }

    @Override
    public AnimationBuilder<IKeyframeModel> declareShape(String name, String type, String layer) {
      Objects.requireNonNull(name);
      Objects.requireNonNull(type);

      if (shapes.get(name) == null) {
        if (!layers.keySet().contains(layer)) {
          layers.put(layer, new ArrayList<>());
        }
        layers.get(layer).add(name);

        switch (type) {
          case "rectangle":
            shapes.put(name, new Rectangle(1, 1, new Position(0, 0), new RGBColor(0, 0, 0), false));
            break;
          case "ellipse":
            shapes.put(name, new Ellipse(1, 1, new Position(0, 0), new RGBColor(0, 0, 0), false));
            break;
          default:
            throw new IllegalArgumentException("Invalid shape type");
        }
      } else {
        throw new IllegalArgumentException("Shape name already declared");
      }
      return this;
    }

    @Override
    public AnimationBuilder<IKeyframeModel> addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
        int g2, int b2, int rotation1, int rotation2) {
      Objects.requireNonNull(name, "name cannot be null");
      if (shapes.get(name) == null) {
        throw new IllegalArgumentException("Shape name not found");
      }

      Action keyframeFirst = new RotationActionImpl(name, shapes.get(name), t1,
          new Position(x1, y1),
          w1, h1, new RGBColor(r1, g1, b1), rotation1);
      Action keyframeSecond = new RotationActionImpl(name, shapes.get(name), t2,
          new Position(x2, y2),
          w2, h2, new RGBColor(r2, g2, b2), rotation2);

      actionList.add(keyframeSecond);
      actionList.sort(Comparator.comparingInt(Action::getFirstTick));

      actionListBeginningConditions.add(keyframeFirst);
      actionListBeginningConditions.sort(Comparator.comparingInt(Action::getFirstTick));

      return this;
    }

    @Override
    public AnimationBuilder<IKeyframeModel> addKeyframe(String name, int t, int x, int y,
        int w,
        int h, int r, int g, int b) {
      return null;
    }
  }
}
