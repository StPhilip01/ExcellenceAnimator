package cs3500.animator.model.shapes;

import java.util.Objects;

/**
 * A class that represents a color based on three intensity values (red, green, blue).
 * <p></p>
 * RGB values are represented as values from 0-255 inclusive.
 */
public class RGBColor implements IColor {

  private final double red;
  private final double green;
  private final double blue;

  /**
   * Constructs a RGBColor with the specified color intensity values.
   *
   * @param red   the red intensity
   * @param green the green intensity
   * @param blue  the blue intensity
   * @throws IllegalArgumentException if the intensities are not between 0 and 255 inclusive
   */
  public RGBColor(double red, double green, double blue) {
    validateRGB(red);
    validateRGB(green);
    validateRGB(blue);
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  // throws an exception if the given intensity is invalid
  private static void validateRGB(double value) {

    if ((int) value == 0 || (int) value == 255) {
      return;
    }

    if (value < 0 || value > 255) {
      throw new IllegalArgumentException("RGB values must be between 0 and 255 inclusive");
    }
  }

  @Override
  public double getRed() {
    return this.red;
  }

  @Override
  public double getGreen() {
    return this.green;
  }

  @Override
  public double getBlue() {
    return this.blue;
  }

  @Override
  public double[] getRGB() {
    return new double[]{this.red, this.green, this.blue};
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof IColor)) {
      return false;
    }
    IColor otherColor = (IColor) other;
    return Math.abs(otherColor.getRed() - this.red) <= .0001
        && Math.abs(otherColor.getGreen() - this.green) <= .0001
        && Math.abs(otherColor.getBlue() - this.blue) <= .0001;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }
}
