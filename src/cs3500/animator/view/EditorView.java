package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyAnimatorModel;
import cs3500.animator.model.action.Action;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * This class is a view for an animation, allowing for modifications of animations by creating,
 * removing, and modifying shapes and their keyframes. This view offers all of the functionality in
 * {@link VisualView} but also adds visual components for adding, removing, and modifying keyframes,
 * creating and removing shapes, and looping the animation. This view displays all of the shapes
 * stored in the model, and all of the keyframes for the selected shape.
 */

public class EditorView extends VisualView implements IEditorView {

  private final JScrollPane animationScrollPane;
  private final JScrollPane fullScroll;

  private final JSlider slider;

  private final JButton playButton;
  private final JButton pauseButton;
  private final JButton restartButton;
  private final JButton speedUpButton;
  private final JButton slowDownButton;
  private final JButton quitButton;
  private final JButton rewindButton;

  private final JCheckBox loopBox;

  private final JLabel tickLabel;
  private final JLabel speedLabel;

  private final JButton addShape;
  private final JButton removeShape;
  private final JButton addKeyframe;
  private final JButton removeKeyframe;
  private final JButton modifyKeyframe;

  private final JButton addLayer;
  private final JButton removeLayer;
  private final JButton swapLayer;

  private final DefaultListModel<String> shapesListModel;
  private final JList<String> shapesList;
  private final DefaultListModel<String> keyframesListModel;
  private final JList<String> keyframeList;
  private final DefaultListModel<String> layersListModel;
  private final JList<String> layersList;

  private boolean looping;

  /**
   * Constructs an editor view for the given model. Runs the animation at the given speed.
   *
   * @param model        the read only version of the model
   * @param initialSpeed the initial speed to play the animation at
   * @throws IllegalArgumentException if the model is null
   */
  public EditorView(ReadOnlyAnimatorModel model, int initialSpeed) throws IllegalArgumentException {

    super(model, initialSpeed, new AnimationPanel(model, initialSpeed));
    this.getContentPane().removeAll();

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    this.setTitle("Excellence Animation Editor");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    JPanel fullPanel = new JPanel(new BorderLayout());
    fullScroll = new JScrollPane(fullPanel);

    JPanel topPanel = new JPanel(new BorderLayout());
    fullPanel.add(topPanel, BorderLayout.NORTH);

    drawingPanel.setPreferredSize(
        new Dimension(model.getWidth() + model.getLeftX(),
            model.getHeight() + model.getTopY()));
    drawingPanel.setBackground(Color.WHITE);

    this.animationScrollPane = new JScrollPane(drawingPanel);

    JPanel borderLayoutPanel = new JPanel(new BorderLayout());
    borderLayoutPanel.setPreferredSize(
        new Dimension(Math.min(500, model.getWidth() + model.getLeftX()),
            Math.min(500, model.getHeight() + model.getTopY())));

    borderLayoutPanel.add(animationScrollPane, BorderLayout.CENTER);

    slider = new JSlider(0, model.getFinalTick());
    slider.setSnapToTicks(true);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);

    borderLayoutPanel.add(slider, BorderLayout.SOUTH);

    topPanel.add(borderLayoutPanel, BorderLayout.CENTER);

    JPanel textPanel = new JPanel();
    BoxLayout boxLayout = new BoxLayout(textPanel, BoxLayout.Y_AXIS);
    textPanel.setLayout(boxLayout);

    tickLabel = new JLabel("Tick: ");
    speedLabel = new JLabel("Speed: ");
    textPanel.add(tickLabel);
    textPanel.add(speedLabel);

    loopBox = new JCheckBox("Looping");
    loopBox.setActionCommand("Loop Toggled");
    textPanel.add(loopBox);

    topPanel.add(textPanel, BorderLayout.EAST);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 3));
    topPanel.add(buttonPanel, BorderLayout.SOUTH);

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

    JPanel bottomPanel = new JPanel(new BorderLayout());

    JPanel layersPanel = new JPanel();
    BoxLayout boxLayout5 = new BoxLayout(layersPanel, BoxLayout.Y_AXIS);
    layersPanel.setLayout(boxLayout5);
    JLabel layersLabel = new JLabel("Layers:");
    layersPanel.add(layersLabel);

    this.layersListModel = new DefaultListModel<>();
    layersListModel.addAll(model.getLayers());
    layersList = new JList<>(layersListModel);
    layersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane layersScroll = new JScrollPane(layersList);
    layersPanel.add(layersScroll);

    JPanel layersButtonPanel = new JPanel();
    addLayer = new JButton("Add Layer");
    addLayer.setActionCommand("Add Layer");
    layersButtonPanel.add(addLayer);

    removeLayer = new JButton("Remove Layer");
    removeLayer.setActionCommand("Remove Layer");
    layersButtonPanel.add(removeLayer);

    swapLayer = new JButton("Swap Layer");
    swapLayer.setActionCommand("Swap Layer");
    layersButtonPanel.add(swapLayer);

    layersPanel.add(layersButtonPanel);
    bottomPanel.add(layersPanel, BorderLayout.WEST);

    JPanel leftPanel = new JPanel();
    BoxLayout boxLayout2 = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
    leftPanel.setLayout(boxLayout2);
    JLabel shapesLabel = new JLabel("Shapes:");
    leftPanel.add(shapesLabel);

    this.shapesListModel = new DefaultListModel<>();
    shapesListModel.addAll(model.getShapeNames());
    shapesList = new JList<>(shapesListModel);
    shapesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane shapesScroll = new JScrollPane(shapesList);
    leftPanel.add(shapesScroll);

    JPanel leftButtonPanel = new JPanel();
    addShape = new JButton("Add Shape");
    addShape.setActionCommand("Add Shape");
    leftButtonPanel.add(addShape);

    removeShape = new JButton("Remove Shape");
    removeShape.setActionCommand("Remove Shape");
    leftButtonPanel.add(removeShape);

    leftPanel.add(leftButtonPanel);
    bottomPanel.add(leftPanel, BorderLayout.CENTER);

    JPanel rightPanel = new JPanel();
    BoxLayout boxLayout3 = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
    rightPanel.setLayout(boxLayout3);
    JLabel keyframeLabel = new JLabel("Keyframes:");
    rightPanel.add(keyframeLabel);

    this.keyframesListModel = new DefaultListModel<String>();
    keyframeList = new JList<String>(keyframesListModel);
    JScrollPane keyframeScroll = new JScrollPane(keyframeList);
    rightPanel.add(keyframeScroll);

    JPanel rightButtonPanel = new JPanel();
    addKeyframe = new JButton("Add Keyframe");
    addKeyframe.setActionCommand("Add Keyframe");
    rightButtonPanel.add(addKeyframe);

    removeKeyframe = new JButton("Remove Keyframe");
    removeKeyframe.setActionCommand("Remove Keyframe");
    rightButtonPanel.add(removeKeyframe);

    modifyKeyframe = new JButton("Modify Keyframe");
    modifyKeyframe.setActionCommand("Modify Keyframe");
    rightButtonPanel.add(modifyKeyframe);

    rightPanel.add(rightButtonPanel);

    bottomPanel.add(rightPanel, BorderLayout.EAST);
    fullPanel.add(bottomPanel, BorderLayout.SOUTH);

    this.add(fullScroll);

    speed = initialSpeed;
    deltaTick = 1;
    looping = false;

    this.pack();
  }

  @Override
  public void refresh() {
    if ((!looping && (drawingPanel.getTick() == model.getFinalTick() && deltaTick > 0)) || (
        drawingPanel.getTick() == 0 && deltaTick < 0)) {
      deltaTick = 0;
      drawingPanel.pause();
      return;
    }

    if (looping && (drawingPanel.getTick() == model.getFinalTick())) {
      drawingPanel.setTick(1);
      drawingPanel.play();
    } else {
      this.drawingPanel.setTick(drawingPanel.getTick() + deltaTick);
    }

    updateInfoText();
    updateSlider();
    this.fullScroll.revalidate();
    this.animationScrollPane.revalidate();
    this.repaint();
  }

  // updates the slider value to the current tick value
  private void updateSlider() {
    this.slider.setValue(drawingPanel.getTick());
  }

  private void updateInfoText() {
    this.tickLabel.setText("Tick: " + drawingPanel.getTick());
    this.speedLabel.setText("Speed: " + this.speed);
  }

  @Override
  public void setActionListener(ActionListener listener) {
    super.setActionListener(listener);

    playButton.addActionListener(listener);
    pauseButton.addActionListener(listener);
    rewindButton.addActionListener(listener);
    restartButton.addActionListener(listener);
    speedUpButton.addActionListener(listener);
    slowDownButton.addActionListener(listener);
    quitButton.addActionListener(listener);

    drawingPanel.setActionListener(listener);
    loopBox.addActionListener(listener);
    addShape.addActionListener(listener);
    removeShape.addActionListener(listener);
    addKeyframe.addActionListener(listener);
    removeKeyframe.addActionListener(listener);
    modifyKeyframe.addActionListener(listener);

    addLayer.addActionListener(listener);
    removeLayer.addActionListener(listener);
    swapLayer.addActionListener(listener);

    layersList.addListSelectionListener((e -> {
      this.updateShapeList();
      this.updateKeyframeList();
    }));

    shapesList.addListSelectionListener((e -> {
      this.updateKeyframeList();
    }));

    slider.addChangeListener((e -> {
      // if the slider's value is different from the current tick value, then pause animation
      // and set the tick to the slider's value
      if (slider.getValue() != drawingPanel.getTick()) {
        super.pause();
        drawingPanel.setTick(slider.getValue());
        updateInfoText();
      }
    }));
  }

  @Override
  public String[] addShape() {
    JPanel panel = new JPanel(new GridLayout(3, 2));
    JLabel nameLabel = new JLabel("Name: ");
    panel.add(nameLabel);
    JTextField nameText = new JTextField();
    panel.add(nameText);

    JLabel typeLabel = new JLabel("Type: ");
    panel.add(typeLabel);
    JComboBox typeBox = new JComboBox(new String[]{"rectangle", "ellipse"});
    panel.add(typeBox);

    JLabel layerLabel = new JLabel("Layer: ");
    panel.add(layerLabel);
    JComboBox layerBox = new JComboBox(model.getLayers().toArray());
    panel.add(layerBox);

    JOptionPane.showConfirmDialog(this, panel, "Add Shape", JOptionPane.OK_CANCEL_OPTION);
    String newShapeName = nameText.getText();
    String newShapeType = (String) typeBox.getSelectedItem();
    String newLayer = (String) layerBox.getSelectedItem();

    if (newShapeName != null && newShapeType != null && !newShapeName.equals("") && !newShapeType
        .equals("") && newLayer != null && !newLayer.equals("")) {
      return new String[]{newShapeName, newShapeType, newLayer};
    }
    return new String[]{};
  }


  @Override
  public void toggleLoop() {
    this.looping = !looping;
  }

  @Override
  public String getSelectedShape() {
    return shapesList.getSelectedValue();
  }

  @Override
  public void updateShapeList() {

    shapesListModel.clear();
    String layer = layersList.getSelectedValue();
    if (layer != null) {
      List<String> names = model.getShapeNames();
      for (String name : names) {
        if (model.getShapeLayer(name).equals(layer)) {
          shapesListModel.addElement(name);
        }
      }
    }
  }

  @Override
  public void updateLayerList() {
    layersListModel.clear();
    layersListModel.addAll(model.getLayers());
  }

  @Override
  public String addLayer() {

    JPanel panel = new JPanel(new GridLayout(1, 2));
    JLabel nameLabel = new JLabel("Layer Name: ");
    panel.add(nameLabel);
    JTextField nameText = new JTextField();
    panel.add(nameText);

    JOptionPane.showConfirmDialog(this, panel, "Add Layer", JOptionPane.OK_CANCEL_OPTION);
    String newLayerName = nameText.getText();
    if (newLayerName != null && !newLayerName.equals("")) {
      return newLayerName;
    }
    return null;
  }

  @Override
  public String getSelectedLayer() {
    return layersList.getSelectedValue();
  }

  @Override
  public String[] swapLayers() {
    JPanel panel = new JPanel(new GridLayout(2, 2));
    JLabel firstLabel = new JLabel("First Layer:");
    panel.add(firstLabel);
    JTextField nameText1 = new JTextField();
    panel.add(nameText1);

    JLabel secondLabel = new JLabel("Second Layer:");
    panel.add(secondLabel);
    JTextField nameText2 = new JTextField();
    panel.add(nameText2);

    JOptionPane.showConfirmDialog(this, panel, "Swap Layers", JOptionPane.OK_CANCEL_OPTION);
    String layer1 = nameText1.getText();
    String layer2 = nameText2.getText();
    if (layer1 != null && !layer1.equals("") && layer2 != null && !layer2.equals("")) {
      return new String[]{layer1, layer2};
    }
    return new String[]{};
  }

  @Override
  public void updateKeyframeList() {
    keyframesListModel.clear();
    String name = shapesList.getSelectedValue();
    if (name != null) {
      List<Action> actions = model.getShapeActions(name);
      for (Action a : actions) {
        keyframesListModel.addElement(a.toString());
      }
    }
  }

  @Override
  public int getSelectedKeyframe() {
    String keyframeToString = keyframeList.getSelectedValue();
    if (keyframeToString != null) {
      String[] splitted = keyframeToString.split("\\s+");
      String tick = splitted[0].substring(splitted[0].indexOf("=") + 1, splitted[0].length() - 1);
      return Integer.parseInt(tick);
    }
    throw new IllegalStateException("No keyframe selected");
  }

  @Override
  public int[] addKeyframe() {
    return createEditKeyframePanel("New Keyframe: " + shapesList.getSelectedValue(), true);
  }

  @Override
  public int[] modifyKeyframe() {
    return createEditKeyframePanel(
        "Modify Keyframe: " + shapesList.getSelectedValue() + " at time=" + getSelectedKeyframe(),
        false);
  }

  // creates a JOptionPane to get user input for editing a keyframe
  private int[] createEditKeyframePanel(String title, boolean editTick) {
    if (shapesList.getSelectedValue() == null) {
      this.showErrorMessage("No shape selected");
      return new int[]{};
    }

    JPanel panel = new JPanel(new GridLayout(7, 2));
    JTextField tickText = new JTextField();

    tickText.setEditable(editTick);

    if (!editTick) {
      tickText.setText(getSelectedKeyframe() + "");
    }

    panel.add(new JLabel("Tick: "));
    panel.add(tickText);

    panel.add(new JLabel("X: "));
    JTextField xText = new JTextField();
    panel.add(xText);

    panel.add(new JLabel("Y: "));
    JTextField yText = new JTextField();
    panel.add(yText);

    panel.add(new JLabel("Width: "));
    JTextField wText = new JTextField();
    panel.add(wText);

    panel.add(new JLabel("Height: "));
    JTextField hText = new JTextField();
    panel.add(hText);

    panel.add(new JLabel("Rotation (degrees): "));
    JTextField rotText = new JTextField();
    panel.add(rotText);

    panel.add(new JLabel("Color: "));

    JPanel colorButtonPanel = new JPanel();

    JLabel colorChooserDisplay = new JLabel("      ");
    colorChooserDisplay.setOpaque(true);
    colorChooserDisplay.setBackground(Color.BLUE);
    colorButtonPanel.add(colorChooserDisplay);

    JButton colorButton = new JButton("Choose color");
    colorButton.setActionCommand("Button Clicked");

    final int[] red = new int[1];
    final int[] green = new int[1];
    final int[] blue = new int[1];

    colorButton.addActionListener(e -> {
      if (e.getActionCommand().equals("Button Clicked")) {
        Color col = JColorChooser
            .showDialog(fullScroll, "Choose a color", Color.BLUE);
        colorChooserDisplay.setBackground(col);
        red[0] = col.getRed();
        green[0] = col.getGreen();
        blue[0] = col.getBlue();
      }
    });
    colorButtonPanel.add(colorButton);
    panel.add(colorButtonPanel);

    JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION);

    return getNewKeyframeValues(tickText, xText, yText, wText, hText, rotText, red, green, blue);
  }

  // processes user input text into values to pass into keyframe creation
  private int[] getNewKeyframeValues(JTextField tickText, JTextField xText, JTextField yText,
      JTextField wText, JTextField hText, JTextField rotText, int[] red, int[] green, int[] blue) {
    try {
      int tick = Integer.parseInt(tickText.getText());
      int x = Integer.parseInt(xText.getText());
      int y = Integer.parseInt(yText.getText());
      int w = Integer.parseInt(wText.getText());
      int h = Integer.parseInt(hText.getText());
      int rotation = Integer.parseInt(rotText.getText());
      return new int[]{tick, x, y, w, h, red[0], green[0], blue[0], rotation};
    } catch (NumberFormatException e) {
      return new int[]{};
    }
  }


}
