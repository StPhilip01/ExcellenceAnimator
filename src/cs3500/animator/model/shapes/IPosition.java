package cs3500.animator.model.shapes;

/**
 * Represents common functionality for 2D positions that can be represented in terms of their x and
 * y Cartesian coordinates.
 */
public interface IPosition {

  /**
   * Gets the x-value of this Position.
   *
   * @return x value
   */
  double getX();

  /**
   * Gets the y-value of this Position.
   *
   * @return y value
   */
  double getY();

  /**
   * Changes this Position's coordinate to the given Position's coordinates.
   *
   * @param pos Position to change to
   * @throws IllegalArgumentException if the specified position is null
   */
  void changePosition(IPosition pos);

  /**
   * Creates a copy of this position with the same coordinates.
   *
   * @return a new Position with the same coordinates as this one
   */
  IPosition copy();

}
