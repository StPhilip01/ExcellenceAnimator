package cs3500.animator.view;

/**
 * Interface to represent editing functionality for an animation view. Adds supports for looping the
 * animation, and adding, removing, and modifying shapes and their keyframes. Also has all of the
 * viewing functionalities offered by {@link AnimatorView}.
 */
public interface IEditorView extends AnimatorView {

  /**
   * Signals the view to add a shape. If no shape is added, then return an empty array.
   *
   * @return an array with the values for the shapes name and type.
   */
  String[] addShape();

  /**
   * Signals the view to prompt the user to add a new keyframe. Returns the values for the new
   * keyframe, or an empty array if the operation was cancelled/invalid.
   *
   * @return an array of the tick, x, y, width, height, and RGB values for the new keyframe
   */
  int[] addKeyframe();

  /**
   * Signals the view to prompt the user to modify the currently selected keyframe. Returns the new
   * values for the keyframe, or an empty array if the operation was cancelled/invalid.
   *
   * @return an array of the tick, x, y, width, height, and RGB values for the new keyframe
   */
  int[] modifyKeyframe();

  /**
   * Signals the view to toggle the looping condition.
   */
  void toggleLoop();

  /**
   * Gets the currently selected shape, or null if no shape is selected.
   *
   * @return the currently selected shape
   */
  String getSelectedShape();

  /**
   * Gets the tick value of the currently selected keyframe.
   *
   * @return the tick value of the selected keyframe
   * @throws IllegalStateException if no keyframe is selected
   */
  int getSelectedKeyframe();

  /**
   * Signals the view to update the shape list with all of the shapes stored in the model.
   */
  void updateShapeList();

  /**
   * Signals the view to update the keyframe list with all of the keyframes associated with the
   * selected shape.
   */
  void updateKeyframeList();


  /**
   * Signals the view to update the layer list with all of the layers in the model.
   */
  void updateLayerList();

  /**
   * Signals the view to prompt the user to add a layer, returning the layer name, or null if the
   * process was cancelled.
   *
   * @return the new layer name
   */
  String addLayer();

  /**
   * Gets the selected layer name.
   *
   * @return the selected layer name
   */
  String getSelectedLayer();

  /**
   * Signals the view to prompt the user to select two layers to be swapped, and returns those two
   * layer names, or null if the operation was cancelled.
   *
   * @return array of two layer names
   */
  String[] swapLayers();


}
