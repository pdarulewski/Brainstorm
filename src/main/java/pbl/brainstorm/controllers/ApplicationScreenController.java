package pbl.brainstorm.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import pbl.brainstorm.IdeaNode;
import pbl.brainstorm.dao.Dao;
import pbl.brainstorm.dao.IdeaNodeListDaoFactory;

public class ApplicationScreenController implements Initializable {

    private static final int MARGIN = 15;

    @FXML
    private Pane applicationScreen;

    private final ColorPicker strokePicker = new ColorPicker(Color.BROWN);
    private final ColorPicker fillPicker = new ColorPicker(Color.ANTIQUEWHITE);

    private String strokeColour = getWebColour(strokePicker);
    private String fillColour = getWebColour(fillPicker);

    private final ContextMenu cm = new ContextMenu();

    private final MenuItem addMainNode = new MenuItem("Add the main node");
    private final MenuItem addNewNode = new MenuItem("Add a new node");
    private final MenuItem takeSnapshot = new MenuItem("Take a snapshot...");
    private final MenuItem saveState = new MenuItem("Save the state of the current session...");
    private final MenuItem changeStroke = new MenuItem("Change stroke colour", strokePicker);
    private final MenuItem changeFill = new MenuItem("Change fill colour", fillPicker);

    private List<IdeaNode> list;

    private IdeaNode mainNode = null;

    private String serverIP;
    private int serverPort;

    private MyThread myThread;

    public void setAddress(String address) {

        this.serverIP = address;

    }

    public void setPort(int port) {

        this.serverPort = port;

    }

    public void setList(List<IdeaNode> list) {

        this.list = list;

    }

    public String getWebColour(ColorPicker picker) {

        return "#" + Integer.toHexString(picker.getValue().hashCode());

    }

    private class MyThread implements Runnable {

        @Override
        public void run() {

            try {

                synchronized (this) {

                    while (true) {

                        while (isSuspended) {

                            wait();

                        }

                        try (Socket socket = new Socket(serverIP, serverPort);
                                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());) {

                            output.writeObject(list);
                            output.flush();

                            list = (ArrayList<IdeaNode>) input.readObject();

                            assignMainNode();

                            ObservableList<Node> tempList = applicationScreen.getChildren();

                            Platform.runLater(() -> removeFromGUI(tempList));

                            Platform.runLater(() -> drawGraph());

                        } catch (ClassNotFoundException e) {

                            System.err.println("The list has not come from the server");

                        } catch (IOException ex) {

                            System.err.println(ex);

                        } catch (Exception exc) {

                            System.err.println(exc);

                        }

                    }

                }

            } catch (InterruptedException e) {

                System.err.println(e);

            }

        }

        public void start() {

            if (thread == null) {

                thread = new Thread(this);

                thread.setDaemon(true);

                thread.start();
            }
        }

        public void suspend() {

            isSuspended = true;

        }

        public synchronized void resume() {

            isSuspended = false;

            notify();

        }

        private Thread thread;
        private boolean isSuspended = false;
    }

    private void removeFromGUI(ObservableList<Node> tempList) {

        for (int i = 0; i < tempList.size(); i++) {

            Node x = tempList.get(i);

            if (!(x instanceof TextField) && !(x instanceof ColorPicker)) {

                tempList.remove(x);

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        strokePicker.getStyleClass().add("button");
        fillPicker.getStyleClass().add("button");

        strokePicker.setOnAction(t -> {

            strokeColour = getWebColour(strokePicker);

        });

        fillPicker.setOnAction(t -> {

            fillColour = getWebColour(fillPicker);

        });

        myThread = new MyThread();

        myThread.start();

    }

    private void assignMainNode() {

        for (IdeaNode x : list) {

            if (x.isMain()) {

                mainNode = x;
                break;

            }

        }

    }

    private void drawGraph() {

        drawLines();

        for (IdeaNode x : list) {

            Text text = createText(x.getContent(), MARGIN);

            double textWidth = text.getLayoutBounds().getWidth();

            Group group = new Group();

            if (x.isMain()) {

                group.getChildren().add(createMainNode(textWidth / 2 + MARGIN, x.getX(), x.getY(), x.getStroke(), x.getFill()));

            } else {

                group.getChildren().add(createRectangle(textWidth, x.getX(), x.getY(), x.getStroke(), x.getFill()));

            }

            moveText(text, x.getX(), x.getY());

            group.getChildren().add(text);

            applicationScreen.getChildren().add(group);

        }

    }

    private void drawLines() {

        for (IdeaNode x : list) {

            if (!x.isMain()) {

                applicationScreen.getChildren().add(drawLine(x));

            }

        }

    }

    private Line drawLine(IdeaNode node) {

        Line line = new Line();

        line.setStroke(Color.DIMGREY);
        line.setStrokeWidth(3);
        line.setStartX(node.getX());
        line.setStartY(node.getY());
        line.setEndX(node.getParent().getX());
        line.setEndY(node.getParent().getY());

        return line;

    }

    @FXML
    private void handleMousePressed(final MouseEvent event) {

        if (event.isSecondaryButtonDown()) {

            if (!cm.getItems().isEmpty()) {

                cm.getItems().clear();

            }

            addNewNode.setOnAction(new EventHandler<ActionEvent>() {

                double xCentre;
                double yCentre;

                @Override
                public void handle(ActionEvent e) {

                    myThread.suspend();

                    final ContextMenu subMenu = new ContextMenu();

                    for (final IdeaNode x : list) {

                        String s = new String();

                        if (x.isMain()) {

                            s = "(MAIN NODE)";

                        }

                        final MenuItem item = new MenuItem(x.getContent() + " " + s);

                        subMenu.getItems().add(item);

                        item.setOnAction((ActionEvent t) -> {

                            xCentre = x.getX();
                            yCentre = x.getY();

                        });
                    }

                    subMenu.show(applicationScreen, event.getScreenX(), event.getScreenY());

                    final TextField tf = new TextField();

                    tf.setPromptText("Add a new item...");

                    tf.requestFocus();

                    tf.relocate(event.getSceneX(), event.getSceneY());

                    applicationScreen.getChildren().add(tf);

                    tf.addEventHandler(KeyEvent.KEY_PRESSED, (final KeyEvent keyEvent) -> {

                        if (keyEvent.getCode() == KeyCode.ENTER) {

                            refreshingNode(tf, event, xCentre, yCentre);

                            sendToServer();

                            myThread.resume();
                        }
                    });

                }

            });

            addMainNode.setOnAction((ActionEvent e) -> {

                myThread.suspend();

                final TextField tf = new TextField();

                tf.setPromptText("Add the main topic...");

                tf.requestFocus();

                tf.relocate(event.getSceneX(), event.getSceneY());

                applicationScreen.getChildren().add(tf);

                tf.addEventHandler(KeyEvent.KEY_PRESSED, (final KeyEvent keyEvent) -> {

                    if (keyEvent.getCode() == KeyCode.ENTER) {

                        refreshingMainNode(tf, event);

                        sendToServer();

                        strokePicker.setValue(Color.web("#3b596b"));
                        fillPicker.setValue(Color.web("#85bade"));

                        strokeColour = getWebColour(strokePicker);
                        fillColour = getWebColour(fillPicker);

                        myThread.resume();
                    }
                });
            });

            takeSnapshot.setOnAction(t -> { // Must be checked on Windows

                WritableImage snapshot = applicationScreen.getScene().snapshot(null);

                BufferedImage tempImg = SwingFXUtils.fromFXImage(snapshot, null);

                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("Choose where to save the snapshot:");

                fileChooser.setInitialFileName("snapshot.png");

                File outputFile = fileChooser.showSaveDialog(applicationScreen.getScene().getWindow());

                if (outputFile != null) {

                    try {

                        ImageIO.write(tempImg, "png", outputFile);

                    } catch (IOException ex) {

                        System.err.println(ex);

                    }
                }

            });

            saveState.setOnAction(t -> {

                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("Choose where to save the current session:");

                fileChooser.setInitialFileName("session.dat");

                File outputFile = fileChooser.showSaveDialog(applicationScreen.getScene().getWindow());

                if (outputFile != null) {

                    try (Dao<List<IdeaNode>> dao = IdeaNodeListDaoFactory.getFileDao(outputFile.getAbsolutePath());) {

                        dao.write(list);

                    } catch (Exception ex) {

                        System.err.println(ex);

                    }

                }

            });

            if (mainNode != null) {

                addMainNode.setDisable(true);
                addNewNode.setDisable(false);

            }

            if (mainNode == null) {

                addNewNode.setDisable(true);

            }

            cm.getItems().addAll(addMainNode, addNewNode, takeSnapshot,
                    saveState, changeStroke, changeFill);

            cm.show(applicationScreen, event.getScreenX(), event.getScreenY());

        }

        if (event.isPrimaryButtonDown()) {

            cm.hide();

        }
    }

    private void sendToServer() {

        try (final Socket socket = new Socket(serverIP, serverPort);
                final ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                final ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject(list);

            output.flush();

            ArrayList<IdeaNode> trash = (ArrayList<IdeaNode>) input.readObject();

        } catch (IOException e1) {

            System.err.println(e1);
            System.err.println("The socket for reading the object has problem");

        } catch (ClassNotFoundException ex) {

            System.err.println(ex);

        }

    }

    private void refreshingNode(final TextField tf, MouseEvent event, double xCentre, double yCentre) {

        Text text = createText(tf.getText(), MARGIN);

        double textWidth = text.getLayoutBounds().getWidth();

        final Rectangle shape = createRectangle(textWidth, event.getScreenX(), event.getScreenY(), strokeColour, fillColour);

        IdeaNode parent = null;

        for (IdeaNode x : list) {

            if (x.getX() == xCentre && x.getY() == yCentre) {

                parent = x;

            }

        }

        list.add(new IdeaNode(text.getText(), event.getScreenX(), event.getScreenY(), false, parent, strokeColour, fillColour));

        Group group = new Group(shape, text);

        applicationScreen.getChildren().add(group);

        moveText(text, event.getScreenX(), event.getScreenY());

        tf.setVisible(false);

    }

    private void refreshingMainNode(final TextField tf, MouseEvent event) {

        Text text = createText(tf.getText(), MARGIN);

        double textWidth = text.getLayoutBounds().getWidth();

        final Circle circle = createMainNode(textWidth / 2 + MARGIN, event.getScreenX(), event.getScreenY(), strokeColour, fillColour);

        mainNode = new IdeaNode(text.getText(), event.getScreenX(), event.getScreenY(), true, null, strokeColour, fillColour);

        list.add(mainNode);

        Group group = new Group(circle, text);

        applicationScreen.getChildren().add(group);

        moveText(text, event.getScreenX(), event.getScreenY());

        tf.setVisible(false);

    }

    private Circle createMainNode(double radius, double x, double y, String stroke, String fill) {

        final Circle circle = new Circle(radius);

        circle.setStroke(Color.web(stroke));
        circle.setStrokeWidth(10);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setFill(Color.web(fill));
        circle.relocate(x - radius, y - radius);

        return circle;
    }

    private void moveText(Text text, double x, double y) {

        double width = text.getBoundsInLocal().getWidth();
        double height = text.getBoundsInLocal().getHeight();

        text.relocate(x - width / 2, y - height / 2);

    }

    private Rectangle createRectangle(double width, double x, double y, String stroke, String fill) {

        final Rectangle shape = new Rectangle(width + 20, 40);

        shape.relocate(x - (width + 20) / 2, y - 20);
        shape.setStrokeType(StrokeType.OUTSIDE);
        shape.setStrokeLineJoin(StrokeLineJoin.ROUND);
        shape.setStrokeWidth(10);
        shape.setFill(Color.web(fill));
        shape.setStroke(Color.web(stroke));

        return shape;
    }

    private Text createText(String name, int fontSize) {

        final Text text = new Text(name);

        text.setFont(new Font(fontSize));
        text.setBoundsType(TextBoundsType.VISUAL);

        return text;

    }

}
