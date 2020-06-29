package cs3500.animator.controller;

import cs3500.animator.model.IKeyframeModel;
import cs3500.animator.model.ReadOnlyAnimatorModelImpl;
import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Position;
import cs3500.animator.model.shapes.RGBColor;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.view.IEditorView;

/**
 * Implementation of an {@link AnimatorController} that provides functionality to run an animation
 * with a specified keyframe supporting model and visual editor view. Acts as an ActionListener for
 * Java Swing events. Currently supports visual editor views. Supports all of the commands in {@link
 * BasicAnimatorControllerImpl} but also adds commands for adding, removing, and modifying
 * keyframes, creating and removing shapes, and looping the animation.
 */
public class EditorController extends BasicAnimatorControllerImpl {

  private final IKeyframeModel model;
  private final IEditorView view;

  /**
   * Constructs a controller to facilitate communication between the keyframe model and the editor
   * view.
   *
   * @param model model that supports keyframes
   * @param view  animation editor view
   * @param out   output mode
   * @throws IllegalArgumentException if the arguments are null
   */
  public EditorController(IKeyframeModel model, IEditorView view,
      Appendable out) {
    super(new ReadOnlyAnimatorModelImpl(model), view, out);

    this.model = model;
    this.view = view;

    configureCommands();
  }

  protected void configureCommands() {
    super.configureCommands();

    commands.put("Add Layer", () -> {
      String newLayerName = view.addLayer();
      if (newLayerName != null) {
        model.createLayer(newLayerName);
      }
      updateViewLists();
    });

    commands.put("Remove Layer", () -> {
      model.removeLayer(view.getSelectedLayer());
      updateViewLists();
    });

    commands.put("Swap Layer", () -> {
      String[] layerNames = view.swapLayers();
      if (layerNames.length > 0) {
        model.swapLayers(layerNames[0], layerNames[1]);
        updateViewLists();
      }
    });

    commands.put("Remove Keyframe", () -> {
      model.removeKeyframe(view.getSelectedShape(), view.getSelectedKeyframe());
      view.updateKeyframeList();
    });

    commands.put("Add Keyframe", () -> {
      int[] newKeyframeInfo = view.addKeyframe();
      addNewKeyframe(newKeyframeInfo);
    });

    commands.put("Modify Keyframe", () -> {
      int tick = view.getSelectedKeyframe();
      model.removeKeyframe(view.getSelectedShape(), tick);
      int[] newKeyframeInfo = view.modifyKeyframe();

      addNewKeyframe(newKeyframeInfo);

    });

    commands.put("Add Shape", () -> {
      String[] newShapeInfo = view.addShape();
      if (newShapeInfo.length == 0) {
        return;
      }
      IShape newShape;
      switch (newShapeInfo[1]) {
        case "rectangle":
          newShape = new Rectangle(1, 1, new Position(0, 0), new RGBColor(0, 0, 0), false);
          break;
        case "ellipse":
          newShape = new Ellipse(1, 1, new Position(0, 0), new RGBColor(0, 0, 0), false);
          break;
        default:
          throw new IllegalStateException("Unexpected shape type: " + newShapeInfo[1]);
      }

      model.createShape(newShapeInfo[0], newShape, newShapeInfo[2]);
      view.updateShapeList();
    });

    commands.put("Remove Shape", () -> {
      model.removeShape(view.getSelectedShape());
      view.updateShapeList();
      view.updateKeyframeList();
    });

    commands.put("Loop Toggled", () -> {
      view.toggleLoop();
    });

  }

  private void updateViewLists() {
    view.updateLayerList();
    view.updateShapeList();
    view.updateKeyframeList();
  }

  private void addNewKeyframe(int[] newKeyframeInfo) {
    if (newKeyframeInfo.length == 0) {
      return;
    }
    if (view.getSelectedShape() != null) {
      model.addKeyframe(view.getSelectedShape(), newKeyframeInfo[0],
          new Position(newKeyframeInfo[1], newKeyframeInfo[2]), newKeyframeInfo[3],
          newKeyframeInfo[4],
          new RGBColor(newKeyframeInfo[5], newKeyframeInfo[6], newKeyframeInfo[7]),
          newKeyframeInfo[8]);
    } else {
      throw new IllegalStateException("No shape selected");
    }
    view.updateKeyframeList();
  }


}
