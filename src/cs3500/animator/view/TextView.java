package cs3500.animator.view;

import cs3500.animator.model.action.Action;
import cs3500.animator.model.shapes.IColor;
import cs3500.animator.model.shapes.IPosition;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.ReadOnlyAnimatorModel;
import java.awt.event.ActionListener;

/**
 * Text based {@link AnimatorView} for an animation. Provides functionality to get the animation
 * state as text. Does not support GUI/SVG functionality.
 */
public class TextView implements AnimatorView {

  private final ReadOnlyAnimatorModel model;

  /**
   * Constructs a textual view for an animation model.
   *
   * @param model the read only animation model
   * @throws IllegalArgumentException if the model is null
   */
  public TextView(ReadOnlyAnimatorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  @Override
  public void makeVisible() {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public void refresh() {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public void showErrorMessage(String error) {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public String getAnimationState() {
    return this.getState();
  }

  private String getState() {
    StringBuilder result = new StringBuilder("");
    result.append(String
        .format("canvas %d %d %d %d\n", model.getLeftX(), model.getTopY(), model.getWidth(),
            model.getHeight()));
    String titleTemplate = "%-8s %-10s %-5s %-4s %-4s %-4s %-4s %-4s %-4s %-4s \t\t "
        + "%-5s %-4s %-4s %-4s %-4s %-4s %-4s %-4s\n";
    String shapeTemplate = "%-8s %-10s %s\n";
    result.append(String.format(titleTemplate,
        "action", "Name", "t", "X", "Y", "W", "H", "R", "G", "B", "t", "X", "Y", "W", "H", "R", "G",
        "B"));

    for (String name : model.getShapeNames()) {

      IShape shape = model.shapeAt(name, 0);

      result.append(String.format(shapeTemplate, "shape", name, shape.toString()));

      for (Action a : model.getShapeActions(name)) {

        IShape shapeMutated = model.shapeAt(a.getShapeName(), a.getFirstTick());
        IPosition mutatedPosition = shapeMutated.getCurrentPosition();
        IColor mutatedColor = shapeMutated.getColor();

        // motion name tick1 x y w h r g b   t2 x y w h r g b
        String temp = "%-8s %-10s %-5d %-4.0f %-4.0f %-4.0f %-4.0f %-4d %-4d %-4d \t\t "
            + "%-5d %-4.0f %-4.0f %-4.0f %-4.0f %-4d %-4d %-4d\n";

        String formatted = String.format(temp,
            "motion", a.getShapeName(), a.getFirstTick(),
            mutatedPosition.getX(), mutatedPosition.getY(),
            shapeMutated.getWidth(),
            shapeMutated.getHeight(), (int) mutatedColor.getRed(),
            (int) mutatedColor.getGreen(), (int) mutatedColor.getBlue(),
            a.getFinalTick(), a.getToPosition().getX(), a.getToPosition().getY(),
            a.getToWidth(), a.getToHeight(),
            (int) a.getToColor().getRed(), (int) a.getToColor().getGreen(),
            (int) a.getToColor().getBlue());

        result.append(formatted);
      }

      result.append("\n");
    }

    return result.toString().trim();
  }

  @Override
  public String getSVG() {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public void setActionListener(ActionListener listener) {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public void speedUp() {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public void slowDown() {
    throw new UnsupportedOperationException("Operation not supported");
  }


  @Override
  public void play() {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public void pause() {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public void rewind() {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public void restart() {
    throw new UnsupportedOperationException("Operation not supported");
  }

}
