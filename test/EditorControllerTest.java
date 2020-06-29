import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.EditorController;
import cs3500.animator.model.IKeyframeModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for a {@link EditorController} using a mock view and mock model.
 */
public class EditorControllerTest {

  // output for mock model and view
  private Appendable mockModelOutput;
  private Appendable viewOutput;

  private MockAnimatorView mockView;

  @Before
  public void init() {
    mockModelOutput = new StringBuilder();
    viewOutput = new StringBuilder();

    IKeyframeModel mockModel = new MockAnimatorModel(mockModelOutput);
    mockView = new MockAnimatorView(viewOutput);

    EditorController cont = new EditorController(mockModel, mockView, viewOutput);
    mockView.setActionListener(cont);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1Null() {
    AnimatorController c = new EditorController(null, mockView, viewOutput);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2Null() {
    AnimatorController c = new EditorController(null, null, viewOutput);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3Null() {
    AnimatorController c = new EditorController(null, null, null);
  }

  // Sets the mock view's JButton to send the given command to its listeners,
  // then clicks the button so that the listeners are notified
  private void run(String command) {
    mockView.setActionCommand(command);
    mockView.buttonClick();
  }

  @Test
  public void addShape() {
    assertEquals("", viewOutput.toString());
    assertEquals("", mockModelOutput.toString());
    run("Add Shape");
    assertEquals("Add Shape\n"
        + "Update Shape List\n", viewOutput.toString());
    assertEquals("Create Shape name rectangle layer\n", mockModelOutput.toString());
  }

  @Test
  public void removeShape() {
    assertEquals("", viewOutput.toString());
    assertEquals("", mockModelOutput.toString());
    run("Remove Shape");
    assertEquals("Get Selected Shape\n"
        + "Update Shape List\n"
        + "Update Keyframe List\n", viewOutput.toString());
    assertEquals("Remove Shape: shape\n", mockModelOutput.toString());
  }


  @Test
  public void addKeyframe() {
    assertEquals("", viewOutput.toString());
    assertEquals("", mockModelOutput.toString());
    run("Add Keyframe");
    assertEquals("Add Keyframe\n"
        + "Get Selected Shape\n"
        + "Get Selected Shape\n"
        + "Update Keyframe List\n", viewOutput.toString());
    assertEquals("Add Keyframe: shape 1 0.00 0.00 10.00 20.00 255.00 255.00 255.00 0\n",
        mockModelOutput.toString());
  }

  @Test
  public void modifyKeyframe() {
    assertEquals("", viewOutput.toString());
    assertEquals("", mockModelOutput.toString());
    run("Modify Keyframe");
    assertEquals("Get Selected Keyframe\n"
        + "Get Selected Shape\n"
        + "Modify Keyframe\n"
        + "Get Selected Shape\n"
        + "Get Selected Shape\n"
        + "Update Keyframe List\n", viewOutput.toString());
    assertEquals("Remove Keyframe: shape 1\n"
            + "Add Keyframe: shape 1 0.00 0.00 10.00 20.00 255.00 255.00 255.00 0\n",
        mockModelOutput.toString());
  }

  @Test
  public void removeKeyframe() {
    assertEquals("", viewOutput.toString());
    assertEquals("", mockModelOutput.toString());
    run("Remove Keyframe");
    assertEquals("Get Selected Shape\n"
        + "Get Selected Keyframe\n"
        + "Update Keyframe List\n", viewOutput.toString());
    assertEquals("Remove Keyframe: shape 1\n", mockModelOutput.toString());
  }


  @Test
  public void toggleLoop() {
    assertEquals("", viewOutput.toString());
    assertEquals("", mockModelOutput.toString());
    run("Loop Toggled");
    assertEquals("Toggle Loop\n", viewOutput.toString());
    assertEquals("", mockModelOutput.toString());
  }

  @Test
  public void addLayer() {
    assertEquals("", viewOutput.toString());
    assertEquals("", mockModelOutput.toString());
    run("Add Layer");
    assertEquals("Add Layer\n"
        + "Update Layer List\n"
        + "Update Shape List\n"
        + "Update Keyframe List\n", viewOutput.toString());
    assertEquals("Create Layer layer\n", mockModelOutput.toString());
  }

  @Test
  public void removeLayer() {
    assertEquals("", viewOutput.toString());
    assertEquals("", mockModelOutput.toString());
    run("Remove Layer");
    assertEquals("Get Selected Layer\n"
        + "Update Layer List\n"
        + "Update Shape List\n"
        + "Update Keyframe List\n", viewOutput.toString());
    assertEquals("Remove Layer selected\n", mockModelOutput.toString());
  }

  @Test
  public void swapLayers() {
    assertEquals("", viewOutput.toString());
    assertEquals("", mockModelOutput.toString());
    run("Swap Layer");
    assertEquals("Swap layers\n"
        + "Update Layer List\n"
        + "Update Shape List\n"
        + "Update Keyframe List\n", viewOutput.toString());
    assertEquals("Swap Layerslayer1 layer2\n", mockModelOutput.toString());
  }
}