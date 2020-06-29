package cs3500.animator.model;

import cs3500.animator.model.action.Action;
import cs3500.animator.model.action.ActionImpl;
import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.IPosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract representation of an {@link AnimatorModel} that stores shapes and actions for the
 * shapes, and also canvas bounds information.
 */
public abstract class AbstractAnimatorModel implements AnimatorModel {

  protected final List<Action> actionList;
  protected final Map<String, IShape> shapes;


  private final int widthBound;
  private final int heightBound;
  private final int leftX;
  private final int topY;

  protected final Map<String, List<String>> layers;
  protected final List<String> layerOrder;

  /**
   * Constructor to initialize the list of shapes and actions to empty lists.
   */
  AbstractAnimatorModel() {
    this.actionList = new ArrayList<Action>();
    this.shapes = new LinkedHashMap<>();

    leftX = AnimatorModel.DEFAULT_LEFT_X;
    topY = AnimatorModel.DEFAULT_TOP_Y;
    widthBound = AnimatorModel.DEFAULT_WIDTH_BOUND;
    heightBound = AnimatorModel.DEFAULT_HEIGHT_BOUND;

    layers = new LinkedHashMap<>();
    layerOrder = new ArrayList<>();
    configureLayers();
  }

  /**
   * Constructor to initialize the list of shapes and actions to empty lists, and also sets the
   * canvas parameters to the specified values.
   *
   * @throws IllegalArgumentException if the dimensions provided are invalid
   */
  AbstractAnimatorModel(int x, int y, int widthBound, int heightBound)
      throws IllegalArgumentException {
    this.actionList = new ArrayList<>();
    this.shapes = new LinkedHashMap<>();

    if (heightBound < 0 || widthBound < 0) {
      throw new IllegalArgumentException("Invalid dimensions");
    }
    this.leftX = x;
    this.topY = y;
    this.widthBound = widthBound;
    this.heightBound = heightBound;

    layers = new LinkedHashMap<>();
    layerOrder = new ArrayList<>();
    configureLayers();
  }

  private void configureLayers() {
    layers.put("default", new ArrayList<>());
    layerOrder.add("default");
  }

  @Override
  public void createLayer(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null");
    }
    if (layers.keySet().contains(layerName)) {
      throw new IllegalArgumentException("Layer already created");
    }
    layers.put(layerName, new ArrayList<>());
    layerOrder.add(layerName);
  }

  @Override
  public void removeLayer(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null");
    }
    if (!layers.keySet().contains(layerName)) {
      throw new IllegalArgumentException("Layer does not exist");
    }
    List<String> namesInLayer = layers.get(layerName);
    for (String shapeName : namesInLayer) {
      removeShapeHelper(shapeName);
    }
    layers.remove(layerName);
    layerOrder.remove(layerName);
  }

  @Override
  public void swapLayers(String layer1, String layer2) throws IllegalArgumentException {
    if (layer1 == null || layer2 == null) {
      throw new IllegalArgumentException("Layer names cannot be null");
    }

    if (!layers.keySet().contains(layer1) || !layers.keySet().contains(layer2)) {
      throw new IllegalArgumentException("Layer does not exist");
    }

    Collections.swap(layerOrder, layerOrder.indexOf(layer1), layerOrder.indexOf(layer2));
  }

  @Override
  public String getShapeLayer(String shapeName) throws IllegalArgumentException,
      IllegalStateException {
    return getLayerHelper(shapeName);
  }

  @Override
  public List<String> getLayers() {
    return new ArrayList<>(layerOrder);
  }

  private String getLayerHelper(String shapeName) {
    if (shapeName == null) {
      throw new IllegalArgumentException("Shape name cannot be null");
    }
    if (shapes.get(shapeName) == null) {
      throw new IllegalArgumentException("Shape not found");
    }
    for (Map.Entry<String, List<String>> entry : layers.entrySet()) {

      String layerName = entry.getKey();

      for (String name : entry.getValue()) {
        if (name.equals(shapeName)) {
          return layerName;
        }
      }
    }
    throw new IllegalStateException("Layer not found");
  }


  @Override
  public IShape shapeAt(String name, int tick) {
    if (name == null || shapes.get(name) == null || tick < 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }

    IShape startingShape = shapes.get(name).copy();

    for (Action a : actionList) {
      if (a.getShapeName().equals(name)) {

        if (a.getFirstTick() < tick) {
          if (a.getFinalTick() < tick) {
            // execute the whole action on the shape
            Action action = new ActionImpl(name, startingShape, a.getFirstTick(), a.getFinalTick(),
                a.getToPosition(), a.getToWidth(), a.getToHeight(),
                a.getToColor());
            action.execute();
          } else {
            // execute the action only up until "tick"
            Action action = new ActionImpl(name, startingShape, a.getFirstTick(), a.getFinalTick(),
                a.getToPosition(), a.getToWidth(), a.getToHeight(),
                a.getToColor());
            action.tickExecute(tick);
          }
        }

      }
    }
    return startingShape;
  }

  @Override
  public void createShape(String name, IShape shape) throws IllegalArgumentException {
    createShape(name, shape, "default");
  }

  @Override
  public void createShape(String name, IShape shape, String layer) throws IllegalArgumentException {
    if (name == null || shape == null || layer == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    if (shapes.get(name) != null) {
      throw new IllegalArgumentException("Shape name already created");
    }

    if (!layers.keySet().contains(layer)) {
      throw new IllegalArgumentException("Layer does not exist");
    }

    shapes.put(name, shape.copy());
    layers.get(layer).add(name);
  }

  @Override
  public List<String> getShapeNames() {
    List<String> names = new ArrayList<>();
    for (String name : shapes.keySet()) {
      names.add(name);
    }
    return names;
  }

  @Override
  public IShape findShape(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name is null");
    }

    if (shapes.get(name) == null) {
      return null;
    } else {
      return shapes.get(name).copy();
    }

  }

  @Override
  public void addMotion(String name, int firstTick, int finalTick, IPosition toPosition,
      double toWidth, double toHeight, IColor toColor) throws IllegalArgumentException {

    if (name == null) {
      throw new IllegalArgumentException("Name is null");
    }

    IShape shape = shapes.get(name);
    if (shape == null) {
      throw new IllegalArgumentException("Invalid shape");
    }

    Action action = new ActionImpl(name, shape, firstTick, finalTick, toPosition, toWidth, toHeight,
        toColor);

    this.validateAction(action);

    actionList.add(action);
    actionList.sort(Comparator.comparingInt(Action::getFirstTick));
  }

  protected void validateAction(Action action) throws IllegalArgumentException {
    List<Action> sameShapeActions = new ArrayList<>();

    for (Action a : actionList) {
      if (a.getShapeName().equals(action.getShapeName())) {
        sameShapeActions.add(a);
      }
    }

    if (sameShapeActions.size() == 0) {
      return;
    } else {
      for (Action a : sameShapeActions) {

        if (a.conflictsWith(action)) {
          throw new IllegalArgumentException("Conflicts with previous action");
        }

      }
    }
  }

  @Override
  public void reset() {
    this.actionList.clear();
    this.shapes.clear();
  }

  @Override
  public List<IShape> getShapes() {
    ArrayList<IShape> shapeList = new ArrayList<>();
    for (IShape shape : shapes.values()) {
      shapeList.add(shape.copy());
    }
    return shapeList;
  }

  @Override
  public int getHeight() {
    return this.heightBound;
  }

  @Override
  public int getWidth() {
    return this.widthBound;
  }

  @Override
  public int getLeftX() {
    return this.leftX;
  }

  @Override
  public int getTopY() {
    return this.topY;
  }

  @Override
  public int getFinalTick() {
    int maxTick = 0;
    for (Action a : actionList) {
      maxTick = Math.max(maxTick, a.getFinalTick());
    }
    return maxTick;
  }


  @Override
  public List<Action> getShapeActions(String shapeName) throws IllegalArgumentException {
    if (shapeName == null) {
      throw new IllegalArgumentException("Shape name cannot be null");
    }
    List<Action> list = new ArrayList<>();

    for (Action a : actionList) {
      if (a.getShapeName().equals(shapeName)) {
        list.add(a);
      }
    }
    return list;
  }

  @Override
  public void removeShape(String shapeName) throws IllegalArgumentException {
    if (shapeName == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    removeShapeHelper(shapeName);
  }

  private void removeShapeHelper(String shapeName) {
    shapes.remove(shapeName);
    List<Action> list = new ArrayList<>();

    for (Action a : actionList) {
      if (a.getShapeName().equals(shapeName)) {
        list.add(a);
      }
    }
    actionList.removeAll(list);
  }
}
