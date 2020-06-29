import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.BasicAnimatorControllerImpl;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.ReadOnlyAnimatorModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for {@link BasicAnimatorControllerImpl}. Uses a mock model and mock view
 * to confirm that the controller is passing communication correctly.
 */
public class BasicAnimatorControllerImplTest {

  private Appendable viewOutput;

  private BasicAnimatorControllerImpl cont;
  private MockAnimatorView mockView;

  @Before
  public void init() {
    viewOutput = new StringBuilder();

    AnimatorModel mockModel = new MockAnimatorModel(new StringBuilder());
    mockView = new MockAnimatorView(viewOutput);

    cont = new BasicAnimatorControllerImpl(new ReadOnlyAnimatorModelImpl(mockModel), mockView,
        viewOutput);
    mockView.setActionListener(cont);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1Null() {
    AnimatorController c = new BasicAnimatorControllerImpl(null, mockView, viewOutput);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2Null() {
    AnimatorController c = new BasicAnimatorControllerImpl(null, null, viewOutput);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3Null() {
    AnimatorController c = new BasicAnimatorControllerImpl(null, null, null);
  }

  // method to have an ActionEvent sent from the view to the controller
  private void run(String command) {
    mockView.setActionCommand(command);
    mockView.buttonClick();
  }

  @Test
  public void testRestart() {
    run("Restart");
    assertEquals("Restart\n", viewOutput.toString());
  }

  @Test
  public void testTimerTick() {
    run("Timer Tick");
    assertEquals("Refresh\n", viewOutput.toString());
  }

  @Test
  public void testSpeedUp() {
    run("Speed Up");
    assertEquals("Speed Up\n", viewOutput.toString());
  }

  @Test
  public void testSlowDown() {
    run("Slow Down");
    assertEquals("Slow Down\n", viewOutput.toString());
  }

  @Test
  public void testPlay() {
    run("Play");
    assertEquals("Play\n", viewOutput.toString());
  }

  @Test
  public void testPause() {
    run("Pause");
    assertEquals("Pause\n", viewOutput.toString());
  }

  @Test
  public void testRewind() {
    run("Rewind");
    assertEquals("Rewind\n", viewOutput.toString());
  }

  @Test
  public void testRun() {
    assertEquals("", viewOutput.toString());
    cont.run();
    assertEquals("Make Visible\n", viewOutput.toString());
  }

  @Test
  public void actionPerformed() {
    assertEquals("", viewOutput.toString());
    cont.actionPerformed(null);
    assertEquals("Error: null\n", viewOutput.toString());
  }
}
