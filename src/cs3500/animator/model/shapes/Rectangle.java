package cs3500.animator.model.shapes;

/**
 * Class which represents a rectangle shape.
 */
public class Rectangle extends AbstractShape {

  /**
   * Constructs a Rectangle based on the given parameters.
   *
   * @param width           the width of the rectangle
   * @param height          the height of the rectangle
   * @param currentPosition the current position of the rectangle
   * @param currentColor    the current color of the rectangle
   * @param visible         the visibility of the rectangle
   * @param rotation        the rotation of the rectangle in degrees
   * @throws IllegalArgumentException if any of the given arguments are null
   * @throws IllegalArgumentException if the given dimensions are not positive
   */
  public Rectangle(double width, double height, IPosition currentPosition, IColor currentColor,
      boolean visible, int rotation) {
    super(width, height, currentPosition, currentColor, visible, rotation);
  }

  /**
   * Constructs a Rectangle based on the given parameters, defaulting rotation to 0.
   *
   * @param width           the width of the rectangle
   * @param height          the height of the rectangle
   * @param currentPosition the current position of the rectangle
   * @param currentColor    the current color of the rectangle
   * @param visible         the visibility of the rectangle
   * @throws IllegalArgumentException if any of the given arguments are null
   * @throws IllegalArgumentException if the given dimensions are not positive
   */
  public Rectangle(double width, double height, IPosition currentPosition, IColor currentColor,
      boolean visible) {
    this(width, height, currentPosition, currentColor, visible, 0);
  }

  @Override
  public IShape copy() {
    return new Rectangle(this.getWidth(), this.getHeight(), this.getCurrentPosition(),
        this.getColor(), this.getVisibility(), this.getRotation());
  }

  @Override
  public String toString() {
    return "rectangle";
  }
}
