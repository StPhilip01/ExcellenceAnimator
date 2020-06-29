import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;


import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.RGBColor;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for the {@link RGBColor} implementation of {@link IColor}.
 */
public class RGBColorTest {

  IColor red;
  IColor green;
  IColor blue;
  IColor black;
  IColor white;

  @Before
  public void init() {
    red = new RGBColor(255, 0, 0);
    green = new RGBColor(0, 255, 0);
    blue = new RGBColor(0, 0, 255);
    black = new RGBColor(0, 0, 0);
    white = new RGBColor(255, 255, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorRedNegative() {
    IColor r = new RGBColor(-1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorGreenNegative() {
    IColor r = new RGBColor(0, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorBlueNegative() {
    IColor r = new RGBColor(0, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorAllNegative() {
    IColor r = new RGBColor(-1, -1, -1);
  }

  @Test
  public void testGetRed() {
    assertEquals(255, red.getRed(), .0001);
    assertEquals(0, green.getRed(), .0001);
    assertEquals(0, blue.getRed(), .0001);
    assertEquals(0, black.getRed(), .0001);
    assertEquals(255, white.getRed(), .0001);
  }

  @Test
  public void testGetGreen() {
    assertEquals(0, red.getGreen(), .0001);
    assertEquals(255, green.getGreen(), .0001);
    assertEquals(0, blue.getGreen(), .0001);
    assertEquals(0, black.getGreen(), .0001);
    assertEquals(255, white.getGreen(), .0001);
  }

  @Test
  public void testGetBlue() {
    assertEquals(0, red.getBlue(), .0001);
    assertEquals(0, green.getBlue(), .0001);
    assertEquals(255, blue.getBlue(), .0001);
    assertEquals(0, black.getBlue(), .0001);
    assertEquals(255, white.getBlue(), .0001);
  }

  @Test
  public void testGetRGB() {
    assertArrayEquals(new double[]{255, 0, 0}, red.getRGB(), .0001);
    assertArrayEquals(new double[]{0, 255, 0}, green.getRGB(), .0001);
    assertArrayEquals(new double[]{0, 0, 255}, blue.getRGB(), .0001);
    assertArrayEquals(new double[]{0, 0, 0}, black.getRGB(), .0001);
    assertArrayEquals(new double[]{255, 255, 255}, white.getRGB(), .0001);
  }

  @Test
  public void testEquals() {
    IColor black2 = new RGBColor(0, 0, 0);

    assertTrue(black.equals(black2));
    assertTrue(black2.equals(black));
    assertTrue(black.equals(black));

    assertFalse(black.equals(blue));
    assertFalse(blue.equals(black));
    assertFalse(blue == null);
  }

  @Test
  public void testHashCode() {
    IColor black2 = new RGBColor(0, 0, 0);
    assertTrue(black.hashCode() == black2.hashCode());
    assertTrue(black.hashCode() == black.hashCode());
    assertFalse(blue.hashCode() == black.hashCode());
  }

}