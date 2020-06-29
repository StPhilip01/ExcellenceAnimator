package cs3500.animator.model.shapes;

/**
 * Represents common functionality for implementations of color. All IColor implementations must be
 * able to be represented in red, green, and blue intensities from 0-255 inclusive.
 */
public interface IColor {

  /**
   * Gets the red intensity of this color as a value from 0-255 inclusive.
   *
   * @return the red intensity
   */
  double getRed();

  /**
   * Gets the green intensity of this color as a value from 0-255 inclusive.
   *
   * @return the green intensity
   */
  double getGreen();

  /**
   * Gets the blue intensity of this color as a value from 0-255 inclusive.
   *
   * @return the blue intensity
   */
  double getBlue();

  /**
   * Gets an array of the red, green, and blue intensities of this color.
   *
   * @return an array with the 3 intensities
   */
  double[] getRGB();
}
