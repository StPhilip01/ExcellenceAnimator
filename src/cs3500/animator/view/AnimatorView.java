package cs3500.animator.view;

import java.awt.event.ActionListener;

/**
 * Functionality for a view of an animation. Supports methods for text, SVG, and GUI views. Has
 * functionality that is implementation-specific so methods can throw unsupported operation
 * exceptions.
 */

public interface AnimatorView {

  /**
   * Signals the view to make itself visible.
   */
  void makeVisible();

  /**
   * Signals the view to draw itself.
   */
  void refresh();

  /**
   * Signals the view to show the given error message.
   *
   * @param error the error message to show
   */
  void showErrorMessage(String error);

  /**
   * Gets a text representation of the animation being played.
   *
   * @return a textual representation of the animation
   */
  String getAnimationState();

  /**
   * Gets a SVG representation of the animation being played.
   *
   * @return a SVG representation of the animation
   */
  String getSVG();

  /**
   * Signals the view to send actions to the given listener.
   *
   * @param listener the action listener
   */
  void setActionListener(ActionListener listener);

  /**
   * Signals the view to speed up.
   */
  void speedUp();

  /**
   * Signals the view to slow down.
   */
  void slowDown();

  /**
   * Signals the view to play with the initial speed.
   */
  void play();

  /**
   * Signals the view to pause.
   */
  void pause();

  /**
   * Signals the view to rewind with the initial speed.
   */
  void rewind();

  /**
   * Signals the view to restart the animation from the beginning with the initial speed.
   */
  void restart();

}
