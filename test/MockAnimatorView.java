import cs3500.animator.view.IEditorView;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JButton;

/**
 * Represents a mock animator view that is used to test animator controllers.
 */
final class MockAnimatorView implements IEditorView {

  private final Appendable out;
  private final JButton button;

  /**
   * Constructs a mock animator view.
   *
   * @param out log to append output to
   */
  MockAnimatorView(Appendable out) {
    Objects.requireNonNull(out);
    this.out = out;
    button = new JButton("mock button");
  }

  /**
   * Sets the action command for the view's JButton used for testing.
   *
   * @param actionCommand command that the button should send when clicked
   */
  public void setActionCommand(String actionCommand) {
    button.setActionCommand(actionCommand);
  }

  /**
   * Clicks the button stored in this mock view, which sends an action command to all of its
   * listeners.
   */
  public void buttonClick() {
    button.doClick();
  }

  @Override
  public void makeVisible() {
    write("Make Visible");
  }

  @Override
  public void refresh() {
    write("Refresh");
  }

  @Override
  public void showErrorMessage(String error) {
    write("Error: " + error);
  }

  @Override
  public String getAnimationState() {
    write("Get Animation State");
    return null;
  }

  @Override
  public String getSVG() {
    write("Get SVG");
    return null;
  }

  @Override
  public void setActionListener(ActionListener listener) {
    button.addActionListener(listener);
  }

  @Override
  public void speedUp() {
    write("Speed Up");
  }

  @Override
  public void slowDown() {
    write("Slow Down");
  }

  @Override
  public void play() {
    write("Play");
  }

  @Override
  public void pause() {
    write("Pause");
  }

  @Override
  public void rewind() {
    write("Rewind");
  }

  @Override
  public void restart() {
    write("Restart");
  }

  // appends the given message to the output
  private void write(String message) {
    try {
      out.append(message + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String[] addShape() {
    write("Add Shape");
    return new String[]{"name", "rectangle", "layer"};
  }

  @Override
  public int[] addKeyframe() {
    write("Add Keyframe");
    return new int[]{1, 0, 0, 10, 20, 255, 255, 255, 0};
  }

  @Override
  public int[] modifyKeyframe() {
    write("Modify Keyframe");
    return new int[]{1, 0, 0, 10, 20, 255, 255, 255, 0};
  }

  @Override
  public void toggleLoop() {
    write("Toggle Loop");
  }

  @Override
  public String getSelectedShape() {
    write("Get Selected Shape");
    return "shape";
  }

  @Override
  public int getSelectedKeyframe() {
    write("Get Selected Keyframe");
    return 1;
  }

  @Override
  public void updateShapeList() {
    write("Update Shape List");
  }

  @Override
  public void updateKeyframeList() {
    write("Update Keyframe List");
  }

  @Override
  public void updateLayerList() {
    write("Update Layer List");
  }

  @Override
  public String addLayer() {
    write("Add Layer");
    return "layer";
  }

  @Override
  public String getSelectedLayer() {
    write("Get Selected Layer");
    return "selected";
  }

  @Override
  public String[] swapLayers() {
    write("Swap layers");
    return new String[]{"layer1", "layer2"};
  }
}
