package cs3500.animator.model.action;

import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IPosition;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Position;
import cs3500.animator.model.shapes.RGBColor;
import java.util.Objects;

/**
 * A class to represent an {@link Action} that can act upon a {@link IShape}'s size, position, and
 * color based upon given tick values.
 */

public class ActionImpl implements Action {

  private final String shapeName;
  protected final IShape shape;
  private final int firstTick;
  private final int finalTick;
  private final IPosition toPosition;
  private final double toWidth;
  private final double toHeight;
  private final IColor toColor;

  /**
   * Constructs an Action associated with the given {@link IShape} and the specified properties that
   * are realized after the Action is executed.
   *
   * @param shapeName  the name of the associated shape
   * @param shape      the shape
   * @param firstTick  the beginning tick value
   * @param finalTick  the final tick value
   * @param toPosition the final position
   * @param toWidth    the final width
   * @param toHeight   the final height
   * @param toColor    the final color
   * @throws IllegalArgumentException if null arguments are passed
   * @throws IllegalArgumentException if the dimensions passed for the shape are not positive
   * @throws IllegalArgumentException if the tick values are negative or invalid
   */

  public ActionImpl(String shapeName, IShape shape, int firstTick, int finalTick,
      IPosition toPosition, double toWidth, double toHeight, IColor toColor)
      throws IllegalArgumentException {
    if (shape == null || toPosition == null || toColor == null || shapeName == null || toWidth <= 0
        || toHeight <= 0 || firstTick < 0 || finalTick < 0 || finalTick < firstTick) {
      throw new IllegalArgumentException("Null/Invalid arguments passed");
    }

    this.shapeName = shapeName;
    this.shape = shape;
    this.firstTick = firstTick;
    this.finalTick = finalTick;
    this.toPosition = toPosition;
    this.toWidth = toWidth;
    this.toHeight = toHeight;
    this.toColor = toColor;
  }

  @Override
  public void tickExecute(int tickTill) {
    this.executeTicksHelper(tickTill);
  }


  @Override
  public void execute() {
    this.executeTicksHelper(this.finalTick);
  }

  protected void executeTicksHelper(int ticks) throws IllegalArgumentException {
    if (ticks < this.firstTick || ticks > this.finalTick) {
      throw new IllegalArgumentException("Invalid tick number");
    }

    this.shape.setVisibility(true);

    double currentX = this.shape.getCurrentPosition().getX();
    double currentY = this.shape.getCurrentPosition().getY();

    double[] currentColorValues = rgbValues(this.shape.getColor());
    double rCurrent = currentColorValues[0];
    double gCurrent = currentColorValues[1];
    double bCurrent = currentColorValues[2];

    double deltaTick = finalTick - firstTick;

    if (Math.abs(finalTick - firstTick) <= .00001) {
      return;
    }

    double widthSpeed = (toWidth - this.shape.getWidth()) / deltaTick;
    double heightSpeed = (toHeight - this.shape.getHeight()) / deltaTick;

    double xSpeed = (toPosition.getX() - currentX) / deltaTick;
    double ySpeed = (toPosition.getY() - currentY) / deltaTick;

    double redSpeed = (toColor.getRed() - rCurrent) / deltaTick;
    double greenSpeed = (toColor.getGreen() - gCurrent) / deltaTick;
    double blueSpeed = (toColor.getBlue() - bCurrent) / deltaTick;

    for (int i = firstTick; i < ticks; i++) {
      currentX = this.shape.getCurrentPosition().getX();
      currentY = this.shape.getCurrentPosition().getY();
      currentColorValues = rgbValues(this.shape.getColor());
      rCurrent = currentColorValues[0];
      gCurrent = currentColorValues[1];
      bCurrent = currentColorValues[2];
      shape.move(new Position(currentX + xSpeed, currentY + ySpeed));
      shape.changeColor(
          new RGBColor((rCurrent + redSpeed), (gCurrent + greenSpeed), (bCurrent + blueSpeed)));
      shape.grow(widthSpeed, heightSpeed);
    }
  }

  @Override
  public String getShapeName() {
    return this.shapeName;
  }

  @Override
  public IShape getShape() {
    return this.shape.copy();
  }

  @Override
  public int getFirstTick() {
    return this.firstTick;
  }

  @Override
  public int getFinalTick() {
    return this.finalTick;
  }

  @Override
  public IPosition getToPosition() {
    return this.toPosition.copy();
  }

  @Override
  public double getToWidth() {
    return this.toWidth;
  }

  @Override
  public double getToHeight() {
    return this.toHeight;
  }

  @Override
  public IColor getToColor() {
    return this.toColor;
  }

  @Override
  public boolean conflictsWith(Action other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Action cannot be null");
    }
    // they are operating on the same shape
    if (!this.shapeName.equals(other.getShapeName())) {
      return false;
    }

    // they overlap in time
    if (this.firstTick < other.getFinalTick()
        && other.getFirstTick() < this.finalTick) {
      // they overlap in sizing
      if ((Math.abs(this.toHeight - this.shape.getHeight()) > .00001
          && Math.abs(other.getToHeight() - other.getShape().getHeight()) > .00001)
          || (Math.abs(this.toWidth - this.shape.getWidth()) > .00001
          && Math.abs(other.getToWidth() - other.getShape().getWidth()) > .00001)) {
        return true;
      } // they overlap in moving
      else if ((!this.toPosition.equals(this.shape.getCurrentPosition())
          && !other.getToPosition().equals(other.getShape().getCurrentPosition()))) {

        return true;
      } // they overlap in recoloring
      else if ((!this.toColor.equals(this.shape.getColor()))
          && (!other.getToColor().equals(other.getShape().getColor()))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int getToDegrees() {
    throw new UnsupportedOperationException("Operation not supported");
  }

  protected static double[] rgbValues(IColor color) {
    double r = color.getRed();
    double g = color.getGreen();
    double b = color.getBlue();
    return new double[]{r, g, b};
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Action)) {
      return false;
    }
    Action otherAction = (Action) other;
    return otherAction.getShapeName().equals(this.shapeName)
        && otherAction.getShape().equals(this.shape)
        && otherAction.getFirstTick() == this.firstTick
        && otherAction.getFinalTick() == this.finalTick
        && otherAction.getToPosition().equals(this.toPosition)
        && Math.abs(otherAction.getToWidth() - this.toWidth) <= .0001
        && Math.abs(otherAction.getToHeight() - this.toHeight) <= .0001
        && otherAction.getToColor().equals(this.toColor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.shapeName, this.shape, this.firstTick, this.finalTick, this.toPosition,
        this.toWidth, this.toHeight, this.toColor);
  }


}
