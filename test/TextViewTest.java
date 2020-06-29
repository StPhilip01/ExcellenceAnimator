import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.BasicAnimatorControllerImpl;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.ReadOnlyAnimatorModel;
import cs3500.animator.model.ReadOnlyAnimatorModelImpl;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.TextView;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for {@link TextView} implementation of {@link AnimatorView}.
 */
public class TextViewTest {

  // empty view
  private AnimatorView emptyView;

  // view with a shape and a motion for that shape
  private AnimatorView view;

  @Before
  public void init() {
    ReadOnlyAnimatorModel emptyModel = new ReadOnlyAnimatorModelImpl(
        new AnimatorModelImpl.Builder().build());
    emptyView = new TextView(emptyModel);

    ReadOnlyAnimatorModel model = new ReadOnlyAnimatorModelImpl(new AnimatorModelImpl.Builder()
        .declareShape("sanjana", "rectangle", "default")
        .addMotion("sanjana", 0, 0, 0, 20, 30, 200, 10,
            10, 200, 2, 5, 20, 30, 200, 10, 10, 0, 0).build());
    view = new TextView(model);
  }

  @Test
  public void emptyAnimationState() {
    assertEquals(
        "canvas 0 0 500 500\n"
            + "action   Name       t     X    Y    W    H    R    G    B    \t\t"
            + " t     X    Y    W    H    R    G    B",
        emptyView.getAnimationState());
  }


  @Test
  public void nonEmptyAnimationState() {
    assertEquals(
        "canvas 0 0 500 500\n"
            + "action   Name       t     X    Y    W    H    R    G    B    \t\t"
            + " t     X    Y    W    H    R    G    B   \n"
            + "shape    sanjana    rectangle\n"
            + "motion   sanjana    0     0    0    21   31   200  10   10   \t\t"
            + " 200   2    5    20   30   200  10   10",
        view.getAnimationState());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void makeVisible() {
    emptyView.makeVisible();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void refresh() {
    emptyView.refresh();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void showErrorMessage() {
    emptyView.showErrorMessage("sanjana is nagging stefan");
  }


  @Test(expected = UnsupportedOperationException.class)
  public void getSVG() {
    emptyView.getSVG();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void setActionListener() {
    BasicAnimatorControllerImpl cont = new BasicAnimatorControllerImpl(
        new ReadOnlyAnimatorModelImpl(new AnimatorModelImpl.Builder().build()), view, System.out);
    emptyView.setActionListener(cont);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void speedUp() {
    emptyView.speedUp();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void slowDown() {
    emptyView.slowDown();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void play() {
    emptyView.play();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void pause() {
    emptyView.pause();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void rewind() {
    emptyView.rewind();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void restart() {
    emptyView.restart();
  }
}