package cs3500.animator.model.shapes;

import java.util.Objects;


/**
 * A representation of a 2D {@link IPosition} in Cartesian coordinates.
 */
public class Position implements IPosition {

  private double x;
  private double y;

  /**
   * Constructor which creates a Position with the given x and y coordinates.
   *
   * @param x the x coordinate of this Position
   * @param y the y coordinate of this Position
   */
  public Position(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public void changePosition(IPosition pos) throws IllegalArgumentException {
    if (pos == null) {
      throw new IllegalArgumentException("Given position is null");
    }
    this.x = pos.getX();
    this.y = pos.getY();
  }

  @Override
  public IPosition copy() {
    return new Position(this.x, this.y);
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof IPosition)) {
      return false;
    }
    IPosition otherPos = (IPosition) other;
    return (Math.abs(otherPos.getX() - this.getX()) <= .00001
        && Math.abs(otherPos.getY() - this.getY()) <= .00001);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

}
