package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyAnimatorModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * GUI based {@link AnimatorView} for an animation. Provides functionality to play and control an
 * animation's view. Does not support text/SVG viewing functionality.
 */
public class VisualView extends JFrame implements AnimatorView {

  protected final ReadOnlyAnimatorModel model;
  protected AnimationPanel drawingPanel;
  private final JScrollPane scrollPane;

  private final JButton playButton;
  private final JButton pauseButton;
  private final JButton restartButton;
  private final JButton speedUpButton;
  private final JButton slowDownButton;
  private final JButton quitButton;
  private final JButton rewindButton;

  protected int deltaTick;
  protected final int initialSpeed;
  protected int speed;

  /**
   * Constructs a GUI based view for the given model and the given initial speed.
   *
   * @param model read only model for animations
   * @param speed initial speed (ticks per second)
   * @throws IllegalArgumentException if the model is null
   */
  public VisualView(ReadOnlyAnimatorModel model, int speed) throws IllegalArgumentException {
    super();

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    this.model = model;

    this.setTitle("Excellence Animation");

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    drawingPanel = new AnimationPanel(model, speed);
    drawingPanel.setPreferredSize(
        new Dimension(model.getWidth() + model.getLeftX(), model.getHeight() + model.getTopY()));
    drawingPanel.setBackground(Color.white);

    this.scrollPane = new JScrollPane(drawingPanel);
    this.add(scrollPane, BorderLayout.CENTER);

    //button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 3));
    this.add(buttonPanel, BorderLayout.SOUTH);

    //buttons
    playButton = new JButton("Play");
    playButton.setActionCommand("Play");
    buttonPanel.add(playButton);

    pauseButton = new JButton("Pause");
    pauseButton.setActionCommand("Pause");
    buttonPanel.add(pauseButton);

    rewindButton = new JButton("Rewind");
    rewindButton.setActionCommand("Rewind");
    buttonPanel.add(rewindButton);

    restartButton = new JButton("Restart");
    restartButton.setActionCommand("Restart");
    buttonPanel.add(restartButton);

    speedUpButton = new JButton("Speed Up");
    speedUpButton.setActionCommand("Speed Up");
    buttonPanel.add(speedUpButton);

    slowDownButton = new JButton("Slow Down");
    slowDownButton.setActionCommand("Slow Down");
    buttonPanel.add(slowDownButton);

    quitButton = new JButton("Quit");
    quitButton.setActionCommand("Quit");
    buttonPanel.add(quitButton);

    this.deltaTick = 1;
    this.speed = speed;
    initialSpeed = speed;

    this.pack();
  }

  public VisualView(ReadOnlyAnimatorModel model, int speed, AnimationPanel animationPanel)
      throws IllegalArgumentException {
    this(model, speed);
    this.drawingPanel = animationPanel;
  }


  @Override
  public void setActionListener(ActionListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }

    playButton.addActionListener(listener);
    pauseButton.addActionListener(listener);
    rewindButton.addActionListener(listener);
    restartButton.addActionListener(listener);
    speedUpButton.addActionListener(listener);
    slowDownButton.addActionListener(listener);
    quitButton.addActionListener(listener);

    drawingPanel.setActionListener(listener);
  }

  @Override
  public void slowDown() {
    if (this.speed > 1) {
      speed -= 1;
    } else if (this.speed < -1) {
      speed += 1;
    }
    drawingPanel.setSpeed(speed);
  }

  @Override
  public void speedUp() {
    if (this.speed >= 1) {
      speed += 1;
    } else if (this.speed <= -1) {
      speed -= 1;
    }
    drawingPanel.setSpeed(speed);
  }

  @Override
  public void play() {
    deltaTick = 1;
    speed = initialSpeed;
    drawingPanel.setSpeed(speed);
    drawingPanel.play();
  }

  @Override
  public void pause() {
    deltaTick = 0;
    drawingPanel.pause();
  }

  @Override
  public void rewind() {
    deltaTick = -1;
    speed = -1 * Math.abs(initialSpeed);
    drawingPanel.setSpeed(speed);
    drawingPanel.rewind();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {

    if ((drawingPanel.getTick() == model.getFinalTick() && deltaTick > 0) || (
        drawingPanel.getTick() == 0 && deltaTick < 0)) {
      deltaTick = 0;
      drawingPanel.pause();
      return;
    }

    this.drawingPanel.setTick(drawingPanel.getTick() + deltaTick);
    this.scrollPane.revalidate();
    this.repaint();
  }

  @Override
  public void restart() {
    deltaTick = 1;
    speed = initialSpeed;
    drawingPanel.setSpeed(speed);
    drawingPanel.setTick(0);
    drawingPanel.play();
  }


  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public String getAnimationState() {
    throw new UnsupportedOperationException("Operation not supported");
  }

  @Override
  public String getSVG() {
    throw new UnsupportedOperationException("Operation not supported");
  }

}
