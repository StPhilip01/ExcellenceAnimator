package cs3500.animator.model;

import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IPosition;

/**
 * Interface to represent additional functionality for an {@link AnimatorModel}, allowing support
 * for adding and removing keyframes for shapes.
 */
public interface IKeyframeModel extends AnimatorModel {

  /**
   * Adds the specified keyframe to the given shape in the model. Maintains the sorted order of the
   * action list.
   *
   * @param name       name of the shape
   * @param tick       tick of the keyframe
   * @param toPosition position to go to
   * @param toWidth    width to go to
   * @param toHeight   height to go to
   * @param toColor    color to go to
   * @param rotation   the rotation in degrees
   * @throws IllegalArgumentException if arguments are null
   * @throws IllegalArgumentException if the shape is not found
   * @throws IllegalArgumentException if this keyframe conflicts with another action
   */
  void addKeyframe(String name, int tick, IPosition toPosition,
      double toWidth, double toHeight, IColor toColor, int rotation);

  /**
   * Removes the keyframe at the specified tick for the given shape.
   *
   * @param name name of the shape
   * @param tick tick to remove the keyframe from
   * @throws IllegalArgumentException if arguments are null or invalid
   * @throws IllegalArgumentException if the shape is not found
   */
  void removeKeyframe(String name, int tick);
}
