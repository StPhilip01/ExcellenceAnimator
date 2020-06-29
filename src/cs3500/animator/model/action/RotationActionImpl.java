package cs3500.animator.model.action;

import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IPosition;
import cs3500.animator.model.shapes.IShape;

/**
 * Keyframe based action for {@link IShape} that also stores properties for rotation in degrees.
 */
public class RotationActionImpl extends KeyframeAction implements Action {

  private final int degrees;

  /**
   * Constructs a RotationActionImpl associated with the given {@link IShape}. The Action stores the
   * properties for the shape at the given tick value, including rotation.
   *
   * @param shapeName  the name of the associated shape
   * @param shape      the shape
   * @param tick       the tick value
   * @param toPosition the final position
   * @param toWidth    the final width
   * @param toHeight   the final height
   * @param toColor    the final color
   * @param degrees    the rotation in degrees
   * @throws IllegalArgumentException if null arguments are passed
   * @throws IllegalArgumentException if the dimensions passed for the shape are not positive
   * @throws IllegalArgumentException if the tick value is negative
   */
  public RotationActionImpl(String shapeName, IShape shape, int tick,
      IPosition toPosition, double toWidth, double toHeight,
      IColor toColor, int degrees) throws IllegalArgumentException {
    super(shapeName, shape, tick, toPosition, toWidth, toHeight, toColor);
    this.degrees = degrees;
  }

  @Override
  public int getToDegrees() {
    return this.degrees;
  }

  @Override
  public String toString() {
    return super.toString() + " degrees=" + this.degrees;
  }
}
