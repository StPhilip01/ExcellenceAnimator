package cs3500.animator.model.action;

import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IPosition;
import cs3500.animator.model.shapes.IShape;

/**
 * This class represents an Action that stores the properties of an {@link IShape} at a given tick
 * value. Executing a KeyframeAction only sets the visibility of the shape to true, and does not
 * mutate the shape. KeyframeAction will conflict with another Action if it has the same tick value.
 * The {@code toString()} method is overridden to provide a textual description of this Action.
 */
public class KeyframeAction extends ActionImpl {

  /**
   * Constructs a KeyframeAction associated with the given {@link IShape}. The Action stores the
   * properties for the shape at the given tick value.
   *
   * @param shapeName  the name of the associated shape
   * @param shape      the shape
   * @param tick       the tick value
   * @param toPosition the final position
   * @param toWidth    the final width
   * @param toHeight   the final height
   * @param toColor    the final color
   * @throws IllegalArgumentException if null arguments are passed
   * @throws IllegalArgumentException if the dimensions passed for the shape are not positive
   * @throws IllegalArgumentException if the tick value is negative
   */
  public KeyframeAction(String shapeName, IShape shape, int tick, IPosition toPosition,
      double toWidth,
      double toHeight, IColor toColor) throws IllegalArgumentException {
    super(shapeName, shape, tick, tick, toPosition, toWidth, toHeight, toColor);
  }

  @Override
  public void tickExecute(int tickTill) {
    executeTicksHelper(super.getFinalTick());
  }


  @Override
  public void execute() {
    executeTicksHelper(super.getFinalTick());
  }


  @Override
  public boolean conflictsWith(Action other) {
    if (other == null) {
      throw new IllegalArgumentException();
    }
    // they are operating on the same shape
    if (!super.getShapeName().equals(other.getShapeName())) {
      return false;
    }

    return this.getFirstTick() == other.getFirstTick();
  }

  // When this action is executed, set the visibility of the shape to true
  protected void executeTicksHelper(int ticks) throws IllegalArgumentException {
    super.shape.setVisibility(true);
  }

  @Override
  public String toString() {
    return String.format("time=%d,  pos=(%d,%d), size=(%d,%d), color=(%d,%d,%d)", getFirstTick(),
        (int) getToPosition().getX(), (int) getToPosition().getY(), (int) getToWidth(),
        (int) getToHeight(), (int) getToColor().getRed(), (int) getToColor().getGreen(),
        (int) getToColor().getBlue());
  }

}
