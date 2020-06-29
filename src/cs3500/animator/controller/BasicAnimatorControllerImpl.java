package cs3500.animator.controller;

import cs3500.animator.model.ReadOnlyAnimatorModel;
import cs3500.animator.view.AnimatorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Flushable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of an {@link AnimatorController} that provides functionality to run an animation
 * with a specified model and view. Acts as an {@link ActionListener} for Java Swing events.
 * Currently supports textual, visual, and SVG views.
 */
public class BasicAnimatorControllerImpl implements AnimatorController, ActionListener {

  private final AnimatorView view;
  protected final Map<String, Runnable> commands;
  private final Appendable output;

  /**
   * Constructs a controller with the given model, view, and output mode.
   *
   * @param model the animation model
   * @param view  the animation view
   * @param out   the output mode/file
   */
  public BasicAnimatorControllerImpl(ReadOnlyAnimatorModel model, AnimatorView view,
      Appendable out) {

    if (model == null || view == null || out == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    this.view = view;
    this.output = out;

    commands = new HashMap<>();
    configureCommands();
  }

  @Override
  public void run() {
    try {
      view.setActionListener(this);
      view.makeVisible();
    } catch (UnsupportedOperationException uoe) {
      try {
        String out = view.getAnimationState();

        Flushable outputFlushable = (Flushable) output;
        output.append(out);
        outputFlushable.flush();
      } catch (UnsupportedOperationException | IOException e1) {
        try {
          String out = view.getSVG();

          Flushable outputFlushable = (Flushable) output;
          output.append(out);
          outputFlushable.flush();
        } catch (UnsupportedOperationException | IOException e2) {
          throw new IllegalStateException("Cannot communicate with the view");
        }
      }
    }
  }

  protected void configureCommands() {
    commands.put("Quit", () -> {
      System.exit(0);
    });

    commands.put("Timer Tick", () -> {
      view.refresh();
    });

    commands.put("Speed Up", () -> {
      view.speedUp();
    });

    commands.put("Slow Down", () -> {
      view.slowDown();
    });

    commands.put("Play", () -> {
      view.play();
    });

    commands.put("Pause", () -> {
      view.pause();
    });

    commands.put("Rewind", () -> {
      view.rewind();
    });

    commands.put("Restart", () -> {
      view.restart();
    });


  }

  private String processCommand(String command) {
    StringBuilder output = new StringBuilder();
    Runnable cmd = null;

    cmd = commands.getOrDefault(command, null);
    if (cmd == null) {
      output.append(String.format("Unknown command %s", command));
    } else {
      cmd.run();
      output.append("Successfully executed: " + command);
    }

    return output.toString();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      String status = processCommand(e.getActionCommand());
    } catch (Exception ex) {
      view.showErrorMessage(ex.getMessage());
    }
  }
}
