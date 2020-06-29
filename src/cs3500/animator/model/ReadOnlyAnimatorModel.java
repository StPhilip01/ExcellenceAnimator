package cs3500.animator.model;

import cs3500.animator.model.action.Action;
import cs3500.animator.model.shapes.IShape;
import java.util.List;

/**
 * Read only interface for a {@link AnimatorModel} that provides methods to observe the state of a
 * model. Does not allow for mutation of the model.
 */
public interface ReadOnlyAnimatorModel {

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
   * Returns a list of the shape names stored by the model.
   *
   * @return a list of the shape names
   */
  List<String> getShapeNames();


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
   * Gets an ordered list of layers stored by the model.
   *
   * @return a list of layers
   */
  List<String> getLayers();

  /**
   * Gets the layer name for the specified shape.
   *
   * @param name name of the shape
   * @return layer name
   * @throws IllegalArgumentException if the shape name is null or not found
   */
  String getShapeLayer(String name);

}
