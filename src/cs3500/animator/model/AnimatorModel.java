package cs3500.animator.model;

import cs3500.animator.model.action.Action;
import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.IPosition;
import java.util.List;

/**
 * Represents common functionality for an animation model. Provides methods for users to add motions
 * to shapes, and run animations based on specified motions. Manages the lists of motions and
 * shapes. Motions cannot overlap both in time and type of transformation.
 *
 * <p>INVARIANT: The list of shape motions is sorted by first tick value.
 */
public interface AnimatorModel {

  /**
   * CONSTANT DEFAULT VALUES FOR VIEW BOUNDS.
   */
  int DEFAULT_LEFT_X = 0;
  int DEFAULT_TOP_Y = 0;
  int DEFAULT_HEIGHT_BOUND = 500;
  int DEFAULT_WIDTH_BOUND = 500;

  /**
   * Adds the given shape to this animation model.
   *
   * @param name  the name of the shape
   * @param shape the shape
   * @throws IllegalArgumentException if a shape with the same name has already been added
   * @throws IllegalArgumentException if null arguments are provided
   */
  void createShape(String name, IShape shape);

  /**
   * Adds the given shape to this animation model.
   *
   * @param name      the name of the shape
   * @param shape     the shape
   * @param layerName the layer name
   * @throws IllegalArgumentException if a shape with the same name has already been added
   * @throws IllegalArgumentException if null arguments are provided
   * @throws IllegalArgumentException if the layer name is invalid
   */
  void createShape(String name, IShape shape, String layerName);

  /**
   * Finds the {@link IShape} associated with the given name. If the name is not found, then null is
   * returned.
   *
   * @param name the name of the shape to look for
   * @return the shape that's found, or null if not found
   * @throws IllegalArgumentException if the name is null
   */
  IShape findShape(String name);

  /**
   * Returns the shape stored in the model that is mutated according to stored actions at a given
   * tick.
   *
   * @param name the name of the shape
   * @param tick the tick value
   * @throws IllegalArgumentException if the name is null
   * @throws IllegalArgumentException if the shape is not found
   * @throws IllegalArgumentException if the tick value is invalid
   */
  IShape shapeAt(String name, int tick);

  /**
   * Returns a list of the {@link IShape}'s that have been added to this model.
   *
   * @return a list of the shapes added to the model
   */
  List<IShape> getShapes();

  /**
   * Returns a list of the shape names stored by the model.
   *
   * @return a list of the shape names
   */
  List<String> getShapeNames();

  /**
   * Adds the described shape action to the list of actions stored in the model.
   *
   * @param name       the name of the shape
   * @param firstTick  the starting tick value
   * @param finalTick  the ending tick value
   * @param toPosition the position to move the shape to
   * @param toWidth    the final width of the shape
   * @param toHeight   the final height of the shape
   * @param toColor    the final color of the shape
   * @throws IllegalArgumentException if invalid dimensions are provided
   * @throws IllegalArgumentException if null arguments are provided
   * @throws IllegalArgumentException if this action conflicts with an existing one
   */
  void addMotion(String name, int firstTick, int finalTick, IPosition toPosition, double toWidth,
      double toHeight, IColor toColor);

  /**
   * Clears all of the stored shapes and action in the model's lists.
   */
  void reset();

  /**
   * Gets the width bound of this animation.
   *
   * @return the width of this animation's canvas
   */
  int getWidth();

  /**
   * Gets the height bound of this animation.
   *
   * @return the height of this animation's canvas
   */
  int getHeight();

  /**
   * Gets the left-x point of this animation.
   *
   * @return the left-x of this animation's canvas
   */
  int getLeftX();

  /**
   * Gets the top-y point of this animation.
   *
   * @return the top-y of this animation's canvas
   */
  int getTopY();

  /**
   * Gets the final tick value of this animation.
   *
   * @return the final tick value
   */
  int getFinalTick();

  /**
   * Gets a list of actions for the specified shape.
   *
   * @param shapeName the name of the shape
   * @return a list of Actions for the shape
   * @throws IllegalArgumentException if shapeName is null
   */
  List<Action> getShapeActions(String shapeName);

  /**
   * Removes the specified shape and all of its associated actions from this model.
   *
   * @param shapeName the name of the shape
   * @throws IllegalArgumentException if the name is null
   */
  void removeShape(String shapeName);

  /**
   * Creates a layer with the specified name in the model.
   *
   * @param layerName the layer name
   * @throws IllegalArgumentException if the layer name is null or if the layer already exists
   */
  void createLayer(String layerName);

  /**
   * Swaps the order of the two specified layers.
   *
   * @param layer1 the first layer
   * @param layer2 the second layer
   * @throws IllegalArgumentException if the arguments are null or the layers are not found
   */
  void swapLayers(String layer1, String layer2);

  /**
   * Removes the specified layer from the model.
   *
   * @param layerName the layer name
   * @throws IllegalArgumentException if the name is null or the layer is not found
   */
  void removeLayer(String layerName);

  /**
   * Gets the layer name for the specified shape.
   *
   * @param shapeName name of the shape
   * @return layer name
   * @throws IllegalArgumentException if the shape name is null or not found
   */
  String getShapeLayer(String shapeName);

  /**
   * Returns an ordered list of the layers stored in the model.
   *
   * @return a list of layer names
   */
  List<String> getLayers();
}
