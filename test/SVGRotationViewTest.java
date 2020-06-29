import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.BasicAnimatorControllerImpl;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.KeyframeModel;
import cs3500.animator.model.ReadOnlyAnimatorModel;
import cs3500.animator.model.ReadOnlyAnimatorModelImpl;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.SVGRotationView;
import cs3500.animator.view.SVGView;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for {@link SVGRotationView} implementation of {@link AnimatorView}.
 */
public class SVGRotationViewTest {

  // empty view
  private AnimatorView emptyView;

  // view with a shape and an action for the shape
  private AnimatorView view;

  @Before
  public void init() {
    AnimatorModel emptyModelMutable = new AnimatorModelImpl.Builder().build();

    ReadOnlyAnimatorModel emptyModel = new ReadOnlyAnimatorModelImpl(emptyModelMutable);
    emptyView = new SVGView(emptyModel, 1);

    AnimatorModel modelMutable = new KeyframeModel.Builder()
        .declareShape("sanjana", "rectangle", "default")
        .addMotion("sanjana", 0, 0, 0, 20, 30, 200, 10, 10,
            200, 2, 5, 20, 30, 200, 10, 10, 0, 200)
        .addMotion("sanjana", 201, 0, 0, 20, 30, 200, 10, 10,
            2000, 2, 5, 20, 30, 200, 10, 10, 200, 400).build();

    ReadOnlyAnimatorModel model = new ReadOnlyAnimatorModelImpl(modelMutable);
    view = new SVGRotationView(model, 5);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getAnimationState() {
    String a = view.getAnimationState();
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

  @Test
  public void emptySVG() {
    assertEquals(
        "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"500\" height=\"500\" "
            + "version=\"1.1\">\n"
            + "</svg>", emptyView.getSVG());
  }

  @Test
  public void nonEmptySVG() {
    assertEquals(
        "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"500\" height=\"500\""
            + " version=\"1.1\">\n"
            + "\t<rect id=\"sanjana\" x=\"0\" y=\"0\" width=\"21\" height=\"31\" "
            + "fill=\"rgb(200,10,10)\" visibility=\"visible\">\n"
            + "\t\t<animateTransform attributeName=\"transform\" attributeType=\"XML\""
            + " begin=\"40000ms\" type=\"rotate\" from=\"200 10 15\" to=\"400 10 15\" "
            + "dur=\"360000ms\" repeatCount=\"1\"></animateTransform>\n"
            + "\t</rect>\n"
            + "</svg>", view.getSVG());
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