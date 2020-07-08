**README**
==========
# **Excellence**
Excellence Animator is a program that allows for creation of animations through standardized inputs. Excellence plays the given animation input with the specified viewing method. Users can set the initial speed, which defaults to 1 tick per second, and the output file, which defaults to System.out.
 * Views supported include: text, visual, svg, edit, svg-rotation
 * Extra Credit features: 
    * Scrubbing: Changes to EditorView only
    * Rotation: Added method to model, use new implementation of RotationActionImpl, optional rotation property for all shapes
    * Layering: Changed AnimationReader and AnimationBuilder to support optionally adding a layer, added methods to model to support layers & layer ordering

#### **Usage**
* java -jar EasyAnimator.jar -in input_file.txt -view SVG -out output.SVG -speed 10

* java -jar EasyAnimator.jar -in input_file.txt -view edit

## **AnimatorModel**
This interface represents a model that can be used to 
add motions to shapes, and see the state of a shape at a specified tick value. Shapes are stored by their names, and can only be acted upon by adding motions to them. Motions are stored in a list that is always sorted by the first tick-value of the contained motions. Motions cannot overlap both in time and type of transformation. The model stores constants for canvas bounds, and can retrieve stored values for canvas bounds.
* Added methods to support observing, creating, removing, and reordering layers.

### **AbstractAnimatorModel**
Abstract representation of an animator model that provides functionality to store shapes and actions, while also providing implementations of methods to view the current state of the model.
* Implemented layer methods

### **AnimatorModelImpl**
A concrete implementation of AnimatorModel that provides the exact same functionality and data representation as the AbstractAnimatorModel implementation. Has a Builder that returns the created model.
* Builder now returns a mutable version of the model, not a read only version.

### **IKeyframeModel**
Interface to represent additional functionality for an AnimatorModel, allowing support for adding and removing keyframes for shapes.
* Design Change: Representing motions as keyframes. Keyframes can be added and removed to represent the state of a shape at a given time. This was done to make editing shape motions easier for us. Our ActionImpl was not easy to modify motions, so we adapted it to support keyframe representation.

### **KeyframeModel**
A concrete implementation of IKeyframeModel that provides the functionality and data representation as the AbstractAnimatorModel implementation, but uses keyframes to represent shape transformations.
Has a Builder that returns the created model. Uses KeyframeAction as the Action implementation.

## **ReadOnlyAnimatorModel**
A read only interface for an AnimatorModel that provides methods to observe the state of a model. It does not allow for mutation of the model.
* Added methods to observe layer information

### **ReadOnlyAnimatorModelImpl**
Implementation of a ReadOnlyAnimatorModel that delegates all observer method calls to an AnimatorModel.
* Implemented layer observer methods

## **Action**
This interface represents actions and transformations that can be performed on IShape objects. Actions can be executed, thus mutating the IShape it is associated with. Actions also know if they are conflicting with another Action.

### **ActionImpl**
A concrete implementation of an Action that can modify an IShape's size, position, and/or color. 
* We now allow for starting and ending ticks to equal each other and to equal 0.

### **KeyframeAction**
An Action that stores the properties of a shape at a given tick value. Executing a KeyframeAction only sets the visibility of the shape to true, and does not mutate the shape. KeyframeAction will conflict with another Action if it has the same tick value. The toString() method is overridden to provide a textual description of this Action.

### **RotationActionImpl** (Extra Credit)
A KeyframeAction that supports rotation as an IShape property.

## **IShape**
This interface represents shapes that have a height, width, position, color and visibility. A shape's dimensions are always positive. Shapes can be moved, resized, and recolored. However, shapes can be extended to allow for more transformations and properties. 
* Added method to observe a shapes rotation, and rotate a shape to a given angle in degrees

### **AbstractShape**
This represents an abstract class to provide for common representations of IShape objects. All AbstractShape objects keep track of their current position, color, visibility, height and width, and can modify these properties.
* Added constructor to wrap around existing constructors, adding in an optional rotation parameter

### **Rectangle**
A concrete implementation of an IShape that represents a rectangle.
* Rotation support

### **Ellipse**
A concrete implementation of an IShape that represents an ellipse.
* Rotation support

## **IColor**
This interface represents the data for a color. It can be represented in terms of red, green, and blue intensities, ranging from 0-255 inclusive.

### **RGBColor**
A concrete implementation of an IColor that represents colors solely based on their red, green, and blue intensity values (0-255 inclusive).

## **IPosition**
This interface represents 2D positions that can be represented in terms of their x and y Cartesian coordinates.

### **Position**
A concrete representation of an IPosition in Cartesian coordinates.

## **AnimationBuilder**
This interface constructs a final document based on input added by methods.
* Declare shape takes in a layer parameter

## **AnimationReader**
A factory for producing new animations, given a source of shapes and a builder for constructing animations.
* Reader can optionally read in layer and rotation information
* Layers are optionally added after a shape's name
* Rotation is specified at the end of a motion, optionally adding both initial rotation and final rotation to the end of a motion

## **AnimatorView**
Functionality for a view of an animation. Supports methods for text, SVG, and GUI views. Has functionality that is implementation-specific so methods can throw unsupported operation exceptions.

### **VisualView**
GUI based view for an animation. Provides functionality to play and control an animation's view. Does not support text/SVG viewing functionality. Currently supports playing, pausing, rewinding, speeding up, slowing down, restarting, and quitting the animation.

#### **AnimationPanel**
Implementation of a JPanel that has support for keeping track of time and drawing animations according to the time. 
* Support for rotation in shapes

### **TextView**
Text based view for an animation. Provides functionality to get the animation state as text. Does not support GUI/SVG functionality.
* Added in canvas bound information to text output

### **SVGView**
SVG based view for an animation. Provides functionality to view an animation in SVG format. Does not support text/GUI viewing functionality.

### **SVGRotationView** (Extra Credit)
SVG based view for an animation's rotation only. Provides functionality to view an animation in SVG format. Does not support text/GUI viewing functionality.

### **IEditorView**
Interface to represent editing functionality for the visual view. Adds supports for looping the animation, and adding, removing, and modifying shapes and their keyframes. Also has all of the viewing functionalities offered by AnimatorView.
* Added methods to support commands for observing, adding, removing, and swapping layers.

### **EditorView**
This class is a view for an animation, allowing for modifications of animations by creating, removing, and modifying shapes and their keyframes. This view offers all of the functionality in VisualView but also adds visual components for adding, removing, and modifying keyframes, creating and removing shapes, and looping the animation. This view displays all of the shapes stored in the model, and all of the keyframes for the selected shape.
* Added support for layering capabilities

## **AnimatorController**
A controller to link the model and the view. Provides functionality to run the program and facilitate communication between the model and the view.

### **BasicAnimatorControllerImpl**
Implementation of an AnimatorController that provides functionality to run an animation with a specified model and view. Acts as an ActionListener for Java Swing events. It currently supports textual, visual, and SVG views.

### **EditorController**
Implementation of an AnimatorController that provides functionality to run an animation with a specified keyframe supporting model and visual editor view. Acts as an ActionListener for Java Swing events. Acts as a ListSelectionListener for a Swing JList. Currently supports visual editor views. Supports all of the commands in BasicAnimatorControllerImpl but also adds commands for adding, removing, and modifying keyframes, creating and removing shapes, and looping the animation.
* Added commands for adding, removing, and swapping layers.
