package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyAnimatorModel;
import cs3500.animator.model.action.Action;
import cs3500.animator.model.shapes.IShape;
import java.util.List;

/**
 * SVG based {@link AnimatorView} for an animation. Provides functionality to view an animation in
 * SVG format. Only supports viewing rotation, and not translation. Does not support text/GUI
 * viewing functionality.
 */
public class SVGRotationView extends SVGView {

  /**
   * Constructs an SVG based view for an animation's rotation properties.
   *
   * @param model the read only animation model
   * @param speed the speed to play at (ticks per second)
   * @throws IllegalArgumentException if the model is null
   */
  public SVGRotationView(ReadOnlyAnimatorModel model, int speed)
      throws IllegalArgumentException {
    super(model, speed);
  }

  @Override
  protected void addAnimateTags(StringBuilder result, String shapeName, String shapeType) {

    List<Action> actions = model.getShapeActions(shapeName);

    for (int i = 0; i < actions.size() - 1; i++) {
      Action currentAction = actions.get(i);
      Action nextAction = actions.get(i + 1);

      if (currentAction.getToDegrees() != nextAction.getToDegrees()) {
        IShape shapeAt0 = model.shapeAt(shapeName, 0);
        IShape shapeBefore = model.shapeAt(shapeName, currentAction.getFirstTick());
        IShape shapeAfter = model.shapeAt(shapeName, nextAction.getFirstTick());

        int begin = currentAction.getFirstTick() * (1000 * 1000 / speed);
        int dur = (nextAction.getFinalTick() * (1000 * 1000 / speed)) - begin;

        if (shapeBefore.getRotation() != shapeAfter.getRotation()) {

          switch (shapeType) {
            case "ellipse":
              result.append(String.format(
                  "\t\t<animateTransform attributeName=\"transform\" attributeType=\"XML\" "
                      + "type=\"rotate\" "
                      + "from=\"%d %d %d\" to=\"%d %d %d\" dur=\"%dms\" repeatCount=\"1\">"
                      + "</animateTransform>\n",
                  shapeBefore.getRotation(),
                  (int) (shapeAt0.getCurrentPosition().getX()),
                  (int) (shapeAt0.getCurrentPosition().getY()),
                  shapeAfter.getRotation(),
                  (int) (shapeAt0.getCurrentPosition().getX()),
                  (int) (shapeAt0.getCurrentPosition().getY()),
                  dur));
              break;

            case "rectangle":
              result.append(String.format(
                  "\t\t<animateTransform attributeName=\"transform\" attributeType=\"XML\" "
                      + "begin=\"%dms\" type=\"rotate\" "
                      + "from=\"%d %d %d\" to=\"%d %d %d\" dur=\"%dms\" repeatCount=\"1\">"
                      + "</animateTransform>\n",
                  begin,
                  shapeBefore.getRotation(),
                  (int) (shapeAt0.getCurrentPosition().getX() + shapeAt0.getWidth() * .5),
                  (int) (shapeAt0.getCurrentPosition().getY() + shapeAt0.getHeight() * .5),
                  shapeAfter.getRotation(),
                  (int) (shapeAt0.getCurrentPosition().getX() + shapeAt0.getWidth() * .5),
                  (int) (shapeAt0.getCurrentPosition().getY() + shapeAt0.getHeight() * .5),
                  dur));
              break;
            default:
              break;
          }


        }
      }
    }
  }

}

