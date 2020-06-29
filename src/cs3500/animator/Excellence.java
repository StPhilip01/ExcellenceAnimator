package cs3500.animator;

import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.BasicAnimatorControllerImpl;
import cs3500.animator.controller.EditorController;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.IKeyframeModel;
import cs3500.animator.model.KeyframeModel;
import cs3500.animator.model.ReadOnlyAnimatorModel;
import cs3500.animator.model.ReadOnlyAnimatorModelImpl;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.IEditorView;
import cs3500.animator.view.SVGRotationView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Excellence Animation is a program that allows for creation of animations through standardized
 * inputs. Excellence plays the given animation input with the specified viewing method. Users can
 * set the initial speed, which defaults to 1 tick per second, and the output file, which defaults
 * to System.out.
 *
 * <p>Views supported include:<ul> <li>"text"</li> <li>"visual"</li> <li>"svg"</li>
 * <li>"edit"</li><li>"svg-rotation"</li></ul>
 */
public final class Excellence {

  private static final List<String> viewNames = Arrays
      .asList("text", "visual", "svg", "edit", "svg-rotation");

  /**
   * Main method to run an Excellence animation.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      showError("No arguments provided");
      System.exit(0);
    }

    List<String> argList = Arrays.asList(args);

    int inIndex = argList.indexOf("-in");
    if (inIndex == -1) {
      showError("Required input argument not found");
      System.exit(0);
    }
    String input = argList.get(inIndex + 1);

    FileReader file = null;
    try {
      file = new FileReader(input);
    } catch (FileNotFoundException e) {
      showError(e.getMessage());
      System.exit(0);
    }

    int speed = 1;
    if (argList.indexOf("-speed") != -1) {
      try {
        speed = Integer.parseInt(argList.get(argList.indexOf("-speed") + 1));
      } catch (NumberFormatException e) {
        showError("Invalid speed value. Using default speed of 1");
      }
    }

    Appendable output = System.out;
    if (argList.indexOf("-out") != -1) {
      try {
        String outputName = argList.get(argList.indexOf("-out") + 1);
        output = new PrintWriter(outputName);
      } catch (FileNotFoundException e) {
        showError("Output file not found. Using default output System.out");
      }
    }

    if (file != null) {
      int viewIndex = argList.indexOf("-view");
      if (viewIndex == -1) {
        showError("Required view argument not found");
        System.exit(0);
      }
      String viewName = argList.get(viewIndex + 1);
      if (viewNames.contains(viewName)) {

        AnimatorController cont = null;

        if (viewName.equals("edit") || viewName.equals("svg-rotation")) {
          KeyframeModel.Builder builder = new KeyframeModel.Builder();
          IKeyframeModel model = AnimationReader
              .parseFile(file, builder);

          if (viewName.equals("edit")) {
            IEditorView view = (IEditorView) getView(viewName, new ReadOnlyAnimatorModelImpl(model),
                speed);
            cont = new EditorController(model, view, output);
          } else {
            ReadOnlyAnimatorModel readOnlyModel = new ReadOnlyAnimatorModelImpl(model);
            AnimatorView view = getView(viewName, readOnlyModel, speed);
            cont = new BasicAnimatorControllerImpl(readOnlyModel, view, output);
          }


        } else {
          AnimatorModel model = AnimationReader
              .parseFile(file, new AnimatorModelImpl.Builder());

          ReadOnlyAnimatorModel readOnlyModel = new ReadOnlyAnimatorModelImpl(model);
          AnimatorView view = getView(viewName, readOnlyModel, speed);

          cont = new BasicAnimatorControllerImpl(readOnlyModel, view, output);

        }

        cont.run();
      } else {
        showError("Invalid view name");
      }

    }
  }

  // shows the given error message
  private static void showError(String error) {
    JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  // factory method to create the specified view
  private static AnimatorView getView(String viewName, ReadOnlyAnimatorModel model, int speed) {
    switch (viewName) {
      case "text":
        return new TextView(model);
      case "visual":
        return new VisualView(model, speed);
      case "svg":
        return new SVGView(model, speed);
      case "svg-rotation":
        return new SVGRotationView(model, speed);
      case "edit":
        return getEditorView(model, speed);
      default:
        showError("Invalid view name");
        return null;
    }
  }

  private static IEditorView getEditorView(ReadOnlyAnimatorModel model, int speed) {
    return new EditorView(model, speed);
  }

}
