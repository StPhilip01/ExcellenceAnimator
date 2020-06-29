package cs3500.animator.model.shapes;

/**
 * Class which represents an ellipse shape.
 */
public class Ellipse extends AbstractShape {

  /**
   * Constructs an Ellipse based on the given parameters.
   *
   * @param width           the width of the ellipse
   * @param height          the height of the ellipse
   * @param currentPosition the current position of the ellipse
   * @param currentColor    the current color of the ellipse
   * @param visible         the visibility of the ellipse
   * @param rotation        the rotation of the ellipse in degrees
   * @throws IllegalArgumentException if any of the given arguments are null
   * @throws IllegalArgumentException if the given dimensions are not positive
   */
  public Ellipse(double width, double height, IPosition currentPosition, IColor currentColor,
      boolean visible, int rotation) {
    super(width, height, currentPosition, currentColor, visible, rotation);
  }

  /**
   * Constructs an Ellipse based on the given parameters.
   *
   * @param width           the width of the ellipse
   * @param height          the height of the ellipse
   * @param currentPosition the current position of the ellipse
   * @param currentColor    the current color of the ellipse
   * @param visible         the visibility of the ellipse
   * @throws IllegalArgumentException if any of the given arguments are null
   * @throws IllegalArgumentException if the given dimensions are not positive
   */
  public Ellipse(double width, double height, IPosition currentPosition, IColor currentColor,
      boolean visible) {
    this(width, height, currentPosition, currentColor, visible, 0);
  }

  @Override
  public IShape copy() {
    return new Ellipse(this.getWidth(), this.getHeight(), this.getCurrentPosition(),
        this.getColor(), this.getVisibility(), this.getRotation());
  }

  @Override
  public String toString() {
    return "ellipse";
  }
}
