import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.ReadOnlyAnimatorModelImpl;
import cs3500.animator.view.AnimationPanel;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for {@link AnimationPanel} class.
 */
public class AnimationPanelTest {

  private AnimationPanel panel;

  @Before
  public void setUp() {
    panel = new AnimationPanel(
        new ReadOnlyAnimatorModelImpl(new AnimatorModelImpl.Builder().build()), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setActionListenerNull() {
    panel.setActionListener(null);
  }

  @Test
  public void getTick() {
    assertEquals(0, panel.getTick());
  }

  @Test
  public void setTick() {
    assertEquals(0, panel.getTick());
    panel.setTick(10);
    assertEquals(10, panel.getTick());
  }
}