package cs3500.animator.view;

import cs3500.animator.model.shapes.IPosition;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.ReadOnlyAnimatorModel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Represents a panel to draw animations on. Supports functionality for playing and controlling the
 * visual viewing of an animation.
 */
public class AnimationPanel extends JPanel {

  private final ReadOnlyAnimatorModel model;
  private Timer timer;

  private int tick;
  private final int initialSpeed;

  /**
   * Constructs an animation panel with the given animation model and the given inital speed.
   *
   * @param model        the read only animation model
   * @param initialSpeed the initial speed (ticks per second)
   * @throws IllegalArgumentException if the model is null
   */
  public AnimationPanel(ReadOnlyAnimatorModel model, int initialSpeed)
      throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    tick = 0;
    this.initialSpeed = initialSpeed;

  }

  /**
   * Paints this panel. Currently supports rectangle and ellipse shapes from the model.
   *
   * @param g graphics to render
   */

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    List<String> names = model.getShapeNames();

    for (String layerName : model.getLayers()) {

      for (String name : names) {

        if (model.getShapeLayer(name).equals(layerName)) {

          IShape shapeA = model.shapeAt(name, tick);
          if (!shapeA.getVisibility()) {
            continue;
          }

          IPosition pos = shapeA.getCurrentPosition();
          double xA = pos.getX();
          double yA = pos.getY();
          double wA = shapeA.getWidth();
          double hA = shapeA.getHeight();
          double[] colorA = shapeA.getColor().getRGB();

          Graphics2D graphics2D = (Graphics2D) g.create();
          graphics2D.rotate(-1 * Math.toRadians(shapeA.getRotation()), xA + wA / 2, yA + hA / 2);

          graphics2D.setColor(new Color((int) (colorA[0]), (int) (colorA[1]), (int) (colorA[2])));

          switch (shapeA.toString()) {
            case "rectangle":
              graphics2D.fillRect((int) (xA), (int) (yA), (int) (wA), (int) (hA));
              break;
            case "ellipse":
              graphics2D.fillOval((int) (xA), (int) (yA), (int) (wA), (int) (hA));
              break;
            default:
              throw new IllegalStateException("Cannot draw given shape");
          }

          graphics2D.dispose();

        }


      }


    }


  }

  /**
   * Signals the panel to send actions to the given listener. Starts this panel's timer.
   *
   * @param listener the action listener
   * @throws IllegalArgumentException if the listener is null
   */
  public void setActionListener(ActionListener listener) throws IllegalArgumentException {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    this.timer = new Timer(1000 / Math.abs(initialSpeed), listener);
    timer.setActionCommand("Timer Tick");

    timer.start();
  }

  /**
   * Gets the current tick value.
   *
   * @return current tick value
   */
  public int getTick() {
    return this.tick;
  }

  /**
   * Sets the current tick value to the given tick.
   *
   * @param tick tick value
   * @throws IllegalArgumentException if the given tick is invalid
   */
  public void setTick(int tick) throws IllegalArgumentException {
    if (tick < 0) {
      throw new IllegalArgumentException("Invalid tick value");
    }
    this.tick = tick;
  }

  /**
   * Starts the timer for this panel and plays the animation.
   */
  public void play() {
    this.timer.start();
  }

  /**
   * Pauses the animation.
   */
  public void pause() {
    this.timer.stop();
  }

  /**
   * Rewinds the animation and starts the timer.
   */
  public void rewind() {
    this.timer.start();
  }

  /**
   * Sets the speed of this panel and adjusts the timer.
   *
   * @param speed speed value
   */
  public void setSpeed(int speed) {
    this.timer.setDelay(1000 / Math.abs(speed));
  }
}
