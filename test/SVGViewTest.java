import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.BasicAnimatorControllerImpl;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.ReadOnlyAnimatorModel;
import cs3500.animator.model.ReadOnlyAnimatorModelImpl;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.SVGView;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents unit testing for {@link SVGView} implementation of {@link AnimatorView}.
 */
public class SVGViewTest {

  // empty view
  private AnimatorView emptyView;

  // view with a shape and an action for the shape
  private AnimatorView view;

  @Before
  public void init() {
    AnimatorModel emptyModelMutable = new AnimatorModelImpl.Builder().build();

    ReadOnlyAnimatorModel emptyModel = new ReadOnlyAnimatorModelImpl(emptyModelMutable);
    emptyView = new SVGView(emptyModel, 1);

    AnimatorModel modelMutable = new AnimatorModelImpl.Builder()
        .declareShape("sanjana", "rectangle", "default")
        .addMotion("sanjana", 0, 0, 0, 20, 30, 200, 10, 10,
            200, 2, 5, 20, 30, 200, 10, 10, 0, 0).build();

    ReadOnlyAnimatorModel model = new ReadOnlyAnimatorModelImpl(modelMutable);
    view = new SVGView(model, 5);
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
            + "\t<rect id=\"sanjana\" x=\"0\" y=\"0\" width=\"21\" height=\"31\" fill=\""
            + "rgb(200,10,10)\" visibility=\"visible\">\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"40000ms\" attributeName="
            + "\"width\" from=\"21\" to=\"20\" fill=\"freeze\"/>\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"40000ms\" attributeName="
            + "\"height\" from=\"31\" to=\"30\" fill=\"freeze\"/>\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"40000ms\" attributeName="
            + "\"x\" from=\"0\" to=\"2\" fill=\"freeze\"/>\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"0ms\" dur=\"40000ms\" attributeName="
            + "\"y\" from=\"0\" to=\"5\" fill=\"freeze\"/>\n"
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