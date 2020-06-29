package cs3500.animator.model;

import cs3500.animator.model.action.Action;
import cs3500.animator.model.action.ActionImpl;
import cs3500.animator.model.shapes.Ellipse;
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
 * Implementation of an {@link AnimatorModel} that stores shapes and actions for the shapes, and
 * also canvas bounds information.
 */
public class AnimatorModelImpl extends AbstractAnimatorModel {

  /**
   * Constructor that calls the constructor of {@link AbstractAnimatorModel} and uses its default
   * fields.
   */
  public AnimatorModelImpl() {
    super();
  }

  /**
   * Constructor that calls the constructor of {@link AbstractAnimatorModel} with the given canvas
   * bound parameters.
   *
   * @throws IllegalArgumentException if the dimensions are invalid
   */
  public AnimatorModelImpl(int x, int y, int widthBound, int heightBound)
      throws IllegalArgumentException {
    super(x, y, widthBound, heightBound);
  }

  /**
   * Builder class to create a {@link ReadOnlyAnimatorModel} by adding configurations, shapes, and
   * motions to an animator model.
   */

  public static final class Builder implements AnimationBuilder<AnimatorModel> {

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
      layers.put("default", new ArrayList<>());
    }

    @Override
    public AnimatorModel build() {
      AnimatorModel model = new AnimatorModelImpl(leftX, topY, widthBound, heightBound);

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
        model.addMotion(a.getShapeName(), a.getFirstTick(), a.getFinalTick(), a.getToPosition(),
            a.getToWidth(), a.getToHeight(), a.getToColor());
      }

      return model;
    }

    @Override
    public AnimationBuilder<AnimatorModel> setBounds(int x, int y, int width, int height) {
      this.leftX = x;
      this.topY = y;
      this.widthBound = width;
      this.heightBound = height;
      return this;
    }

    @Override
    public AnimationBuilder<AnimatorModel> declareShape(String name, String type, String layer) {
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
    public AnimationBuilder<AnimatorModel> addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
        int g2, int b2, int rotation1, int rotation2) {
      Objects.requireNonNull(name, "name cannot be null");
      if (shapes.get(name) == null) {
        throw new IllegalArgumentException("Shape name not found");
      }

      Action a = new ActionImpl(name, shapes.get(name), t1, t2, new Position(x2, y2), w2, h2,
          new RGBColor(r2, g2, b2));
      actionList.add(a);
      actionList.sort(Comparator.comparingInt(Action::getFirstTick));

      Action aStarting = new ActionImpl(name, shapes.get(name), t1, t2, new Position(x1, y1), w1,
          h1, new RGBColor(r1, g1, b1));
      actionListBeginningConditions.add(aStarting);
      actionListBeginningConditions.sort(Comparator.comparingInt(Action::getFirstTick));

      return this;
    }

    @Override
    public AnimationBuilder<AnimatorModel> addKeyframe(String name, int t, int x, int y,
        int w,
        int h, int r, int g, int b) {
      return null;
    }
  }
}
