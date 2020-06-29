package cs3500.animator.model;

import cs3500.animator.model.action.Action;
import cs3500.animator.model.shapes.IShape;
import java.util.List;

/**
 * Implementation of a {@link ReadOnlyAnimatorModel} that provides methods to observe a {@link
 * AnimatorModel} without mutating it.
 */
public class ReadOnlyAnimatorModelImpl implements ReadOnlyAnimatorModel {

  private final AnimatorModel model;

  /**
   * Constructs a read only model that delegates actions to a given {@link AnimatorModel}.
   *
   * @param model the model to read from
   * @throws IllegalArgumentException if the model is null
   */
  public ReadOnlyAnimatorModelImpl(AnimatorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }
    this.model = model;
  }

  @Override
  public IShape shapeAt(String name, int tick) throws IllegalArgumentException {
    IShape shape = model.shapeAt(name, tick);

    if (shape != null) {
      return shape;
    } else {
      throw new IllegalArgumentException("Shape not found");
    }
  }

  @Override
  public List<String> getShapeNames() {
    return model.getShapeNames();
  }

  @Override
  public int getWidth() {
    return model.getWidth();
  }

  @Override
  public int getHeight() {
    return model.getHeight();
  }

  @Override
  public int getLeftX() {
    return model.getLeftX();
  }

  @Override
  public int getTopY() {
    return model.getTopY();
  }

  @Override
  public int getFinalTick() {
    return model.getFinalTick();
  }

  @Override
  public List<Action> getShapeActions(String shapeName) {
    return model.getShapeActions(shapeName);
  }


  @Override
  public List<String> getLayers() {
    return model.getLayers();
  }

  @Override
  public String getShapeLayer(String name) {
    return model.getShapeLayer(name);
  }
}
