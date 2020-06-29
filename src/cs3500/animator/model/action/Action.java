package cs3500.animator.model.action;

import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IPosition;
import cs3500.animator.model.shapes.IShape;

/**
 * Represents common functionality of actions that can be performed on an {@link IShape}. Provides
 * methods to execute the Action, and describe the properties of the action.
 */
public interface Action {

  /**
   * Executes this Action from start to finish. Mutates the {@link IShape} that is being acted
   * upon.
   *
   * @throws IllegalArgumentException if the tick number provided is invalid
   */
  void execute();

  /**
   * Executes this Action until the tick number provided. Mutates the {@link IShape} that is being
   * acted upon. The tick number provided must be between the start tick and the end tick.
   *
   * @param tickTill the number of ticks of the Action to execute
   * @throws IllegalArgumentException if the tick number provided is invalid
   */
  void tickExecute(int tickTill);

  /**
   * Gets the name of the {@link IShape} associated with the Action.
   *
   * @return the name of the shape
   */
  String getShapeName();

  /**
   * Gets the {@link IShape} associated with the Action.
   *
   * @return the shape
   */
  IShape getShape();

  /**
   * Gets the starting tick value for the Action.
   *
   * @return the first tick value
   */
  int getFirstTick();

  /**
   * Gets the ending tick value for the Action.
   *
   * @return the final tick value
   */
  int getFinalTick();

  /**
   * Gets the ending position value for this Action.
   *
   * @return the final position
   */
  IPosition getToPosition();

  /**
   * Gets the ending width value for this Action.
   *
   * @return the ending width value
   */
  double getToWidth();

  /**
   * Gets the ending height value for this Action.
   *
   * @return the ending height value
   */
  double getToHeight();

  /**
   * Gets the ending {@link IColor} for this Action.
   *
   * @return the ending {@link IColor}
   */
  IColor getToColor();

  /**
   * Determines if the given {@link Action} conflicts with {@code this} action.
   *
   * @return boolean value indicating if the actions conflict
   */
  boolean conflictsWith(Action other);

  /**
   * Gets the rotation stored in the action in degrees.
   *
   * @return shape rotation in degrees
   */
  int getToDegrees();
}
