package cs3500.animator.model.shapes;

import java.util.Objects;

/**
 * Represents the abstract class to implement {@link IShape}. Provides a common representation for
 * shapes.
 */
public abstract class AbstractShape implements IShape {

  private double width;
  private double height;
  private final IPosition currentPosition;
  private IColor currentColor;
  private boolean visible;
  private int rotation;

  /**
   * Constructor which creates a shape with the given parameters.
   *
   * @param width           the width of the shape
   * @param height          the height of the shape
   * @param currentPosition the position of the shape
   * @param currentColor    the color of the shape
   * @param rotation        the rotation of the shape in degrees
   * @param visible         the visibility of the shape
   * @throws IllegalArgumentException if null arguments are provided
   * @throws IllegalArgumentException if the provided dimensions are not positive
   */
  AbstractShape(double width, double height, IPosition currentPosition, IColor currentColor,
      boolean visible, int rotation)
      throws IllegalArgumentException {
    validateDimension(width);
    validateDimension(height);

    if (currentColor == null || currentPosition == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    this.width = width;
    this.height = height;
    this.currentColor = currentColor;
    this.currentPosition = currentPosition;
    this.visible = visible;
    this.rotation = rotation;
  }

  // throws an exception if the given dimension is not positive.
  // used to enforce the invariant property.
  private static void validateDimension(double dimension) {
    if (dimension <= 0) {
      throw new IllegalArgumentException("Invalid dimension");
    }
  }

  /**
   * Overrides the Object superclass's {@code toString()} method to produce the type of this shape.
   *
   * @return the name of this shape
   */
  @Override
  public abstract String toString();

  @Override
  public abstract IShape copy();

  @Override
  public void move(IPosition toPosition) {
    this.currentPosition.changePosition(toPosition);
  }

  @Override
  public void grow(double deltaWidth, double deltaHeight) throws IllegalArgumentException {
    if (this.height + deltaHeight > 0 && this.width + deltaWidth > 0) {
      this.height += deltaHeight;
      this.width += deltaWidth;
    } else {
      throw new IllegalArgumentException("Shape dimensions cannot be set to negative");
    }
  }

  @Override
  public void rotateTo(int degrees) {
    this.rotation = degrees;
  }

  @Override
  public void changeColor(IColor toColor) {
    this.currentColor = toColor;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public boolean getVisibility() {
    return this.visible;
  }

  @Override
  public void setVisibility(boolean visible) {
    this.visible = visible;
  }

  @Override
  public IPosition getCurrentPosition() {
    return this.currentPosition.copy();
  }

  @Override
  public IColor getColor() {
    return this.currentColor;
  }

  @Override
  public int getRotation() {
    return this.rotation;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof IShape)) {
      return false;
    }
    IShape otherShape = (IShape) other;
    return otherShape.getWidth() == this.width
        && otherShape.getHeight() == this.height
        && otherShape.getCurrentPosition().equals(this.currentPosition)
        && otherShape.getColor().equals(this.currentColor)
        && otherShape.getVisibility() == this.visible
        && otherShape.getRotation() == this.rotation;
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(this.width, this.height, this.currentPosition, this.currentColor, this.visible,
            this.rotation);
  }
}
