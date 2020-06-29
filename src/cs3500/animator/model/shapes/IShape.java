package cs3500.animator.model.shapes;

/**
 * Represents common functionality for shapes. Shapes have a height, width, {@link Position}, {@link
 * IColor}, visibility, and can be moved, resized, and recolored.
 * <p></p>
 * INVARIANT: The shape's dimensions are positive.
 */
public interface IShape {

  /**
   * Produces a copy of this shape with the same properties.
   *
   * @return a copy of this shape
   */
  IShape copy();

  /**
   * Moves this shape to the given position.
   *
   * @param toPosition position to be moved to
   * @throws IllegalArgumentException if the specified position is invalid
   */
  void move(IPosition toPosition);

  /**
   * Resizes this shape based on the given width and height deltas.
   *
   * @param deltaWidth  the value to change the width by
   * @param deltaHeight the value to change the height by
   * @throws IllegalArgumentException if the resizing invalidates the dimensions of the shape
   */
  void grow(double deltaWidth, double deltaHeight);

  /**
   * Recolors this shape to the given color.
   *
   * @param toColor The color to change to
   * @throws IllegalArgumentException if the color is null
   */
  void changeColor(IColor toColor);

  /**
   * Gets the width of this shape.
   *
   * @return the width of this shape.
   */
  double getWidth();

  /**
   * Gets the height of this shape.
   *
   * @return the height of this shape.
   */
  double getHeight();

  /**
   * Gets the visibility of this shape.
   *
   * @return the visibility of this shape.
   */
  boolean getVisibility();

  /**
   * Sets the visibility of this shape to the given value.
   *
   * @param visible the visibility value
   */
  void setVisibility(boolean visible);

  /**
   * Gets the current position of this shape.
   *
   * @return the current position of this shape
   */
  IPosition getCurrentPosition();

  /**
   * Gets the current color of this shape.
   *
   * @return the current color of this shape
   */
  IColor getColor();

  /**
   * Gets the rotation of this shape.
   *
   * @return the rotation in degrees
   */
  int getRotation();

  /**
   * Rotates this shape to the specified degrees.
   *
   * @param degrees degrees to rotate to
   */
  void rotateTo(int degrees);
}
