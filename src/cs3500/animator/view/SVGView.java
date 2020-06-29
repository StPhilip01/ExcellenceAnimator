package cs3500.animator.view;

import cs3500.animator.model.action.Action;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.ReadOnlyAnimatorModel;
import java.awt.event.ActionListener;

/**
 * SVG based {@link AnimatorView} for an animation. Provides functionality to view an animation in
 * SVG format. Does not support text/GUI viewing functionality.
 */
public class SVGView implements AnimatorView {

  protected final ReadOnlyAnimatorModel model;
  protected final int speed;

  /**
   * Constructs an SVG based view for an animation.
   *
   * @param model the read only animation model
   * @param speed the speed to play at (ticks per second)
   * @throws IllegalArgumentException if the model is null
   */
  public SVGView(ReadOnlyAnimatorModel model, int speed) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    if (speed <= 0) {
      throw new IllegalArgumentException("Invalid speed");
    }

    this.model = model;
    this.speed = speed * 1000;
  }

  @Override
  public String getSVG() {
    StringBuilder result = new StringBuilder("");

    result.append(
        "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + (model.getWidth() + model.getLeftX())
            + "\" height=\"" + (model.getHeight() + model.getTopY()) + "\" version=\"1.1\">\n");

    for (String shapeName : model.getShapeNames()) {
      IShape shape = model.shapeAt(shapeName, 0);
      switch (shape.toString()) {
        case "rectangle":
          result.append(String.format(
              "\t<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill="
                  + "\"rgb(%d,%d,%d)\" visibility=\"%s\">\n",
              shapeName, (int) shape.getCurrentPosition().getX(),
              (int) shape.getCurrentPosition().getY(), (int) shape.getWidth(),
              (int) shape.getHeight(), (int) shape.getColor().getRed(),
              (int) shape.getColor().getGreen(), (int) shape.getColor().getBlue(), "visible"));

          addAnimateTags(result, shapeName, "rectangle");

          result.append("\t</rect>\n");
          break;
        case "ellipse":
          result.append(String.format(
              "\t<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" "
                  + "fill=\"rgb(%d,%d,%d)\" visibility=\"%s\">\n",
              shapeName, (int) shape.getCurrentPosition().getX(),
              (int) shape.getCurrentPosition().getY(), (int) (shape.getWidth() * .5),
              (int) (shape.getHeight() * .5), (int) shape.getColor().getRed(),
              (int) shape.getColor().getGreen(), (int) shape.getColor().getBlue(), "visible"));

          addAnimateTags(result, shapeName, "ellipse");

          result.append("\t</ellipse>\n");
          break;
        default:
          break;
      }

    }

    result.append("</svg>");

    return result.toString();
  }

  protected void addAnimateTags(StringBuilder result, String shapeName, String shapeType) {
    String xAttribute;
    String yAttribute;
    String widthAttribute;
    String heightAttribute;

    switch (shapeType) {
      case "rectangle":
        xAttribute = "x";
        yAttribute = "y";
        widthAttribute = "width";
        heightAttribute = "height";

        break;
      case "ellipse":
        xAttribute = "cx";
        yAttribute = "cy";
        widthAttribute = "rx";
        heightAttribute = "ry";
        break;

      default:
        return;
    }
    for (Action a : model.getShapeActions(shapeName)) {

      IShape shapeBefore = model.shapeAt(shapeName, a.getFirstTick());
      IShape shapeAfter = model.shapeAt(shapeName, a.getFinalTick());

      int begin = a.getFirstTick() * (1000 * 1000 / speed);
      int dur = (a.getFinalTick() * (1000 * 1000 / speed)) - begin;

      if (Math.abs(shapeBefore.getWidth() - shapeAfter.getWidth()) > .0001) {
        result.append(String.format(
            "\t\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\""
                + " attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            begin, dur, widthAttribute,
            widthAttribute.equals("width") ? (int) shapeBefore.getWidth()
                : (int) (shapeBefore.getWidth() * .5),
            widthAttribute.equals("width") ? (int) shapeAfter.getWidth()
                : (int) (shapeAfter.getWidth() * .5)));
      }

      if (Math.abs(shapeBefore.getHeight() - shapeAfter.getHeight()) > .0001) {
        result.append(String.format(
            "\t\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\""
                + " attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            begin, dur, heightAttribute,
            heightAttribute.equals("height") ? (int) shapeBefore.getHeight()
                : (int) (shapeBefore.getHeight() * .5),
            heightAttribute.equals("height") ? (int) shapeAfter.getHeight()
                : (int) (shapeAfter.getHeight() * .5)));
      }

      if (Math.abs(shapeBefore.getCurrentPosition().getX() - shapeAfter.getCurrentPosition().getX())
          > .0001) {
        result.append(String.format(
            "\t\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\""
                + " attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            begin, dur, xAttribute, (int) shapeBefore.getCurrentPosition().getX(),
            (int) shapeAfter.getCurrentPosition().getX()));
      }

      if (Math.abs(shapeBefore.getCurrentPosition().getY() - shapeAfter.getCurrentPosition().getY())
          > .0001) {
        result.append(String.format(
            "\t\t<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\""
                + " attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            begin, dur, yAttribute, (int) shapeBefore.getCurrentPosition().getY(),
            (int) shapeAfter.getCurrentPosition().getY()));
      }

      if (!shapeBefore.getColor().equals(shapeAfter.getColor())) {
        result.append(String.format(
            "\t\t<animate attributeType=\"css\" begin=\"%dms\" dur=\"%dms\" "
                + "attributeName=\"%s\" from=\"rgb(%d,%d,%d)\" "
                + "to=\"rgb(%d,%d,%d)\" fill=\"freeze\"/>\n",
            begin, dur, "fill", (int) shapeBefore.getColor().getRed(),
            (int) shapeBefore.getColor().getGreen(), (int) shapeBefore.getColor().getBlue(),
            (int) shapeAfter.getColor().getRed(), (int) shapeAfter.getColor().getGreen(),
            (int) shapeAfter.getColor().getBlue()));
      }

    }
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
