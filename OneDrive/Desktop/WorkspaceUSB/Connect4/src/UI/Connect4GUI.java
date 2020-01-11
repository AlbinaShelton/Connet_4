package UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Core.Connect4;
import UI.Connect4TextConsole;

/**
 * Connect4GUI class is generate the graphics for the Connect4 game. Implements
 * JavaFx and Connect4 last updates 11/02/2019
 * 
 * @author albin
 * @version 2.0
 */
public class Connect4GUI extends Application {

	Connect4 game = new Connect4();

	private Stage window;
	private Scene startUpScene;
	boolean compGame = false;
	private Pane pane = new Pane();
	private int colums = 7;
	private int rows = 6;
	private Token[][] boardGrid = new Token[colums][rows];
	private List<Token> compList = new ArrayList<>();
	private int t_size = 100;
	private boolean redT = false;

	/**
	 * Method to launch start menu of the game
	 * 
	 * @param stage
	 */
	private class Token extends Circle {
		boolean red;

		public Token(boolean red) {
			super(t_size / 2, red ? Color.YELLOW : Color.RED);
			this.red = red;
			setCenterX(t_size / 2);
			setCenterY(t_size / 2);

		}
	}

	/**
	 * This method is given user to chose the game
	 * 
	 * @param stage window to ask play Console or GUI
	 */
	public void startWindow(Stage stage) {

		window = stage;
		Label startLabel = new Label("How would you like to play the game?");
		startLabel.setFont(new Font("Britannic Bold", 22));
		Button GUIButton = new Button("GUI Game");
		GUIButton.setStyle("-fx-font: 20 arial; -fx-base: lightblue;");
		GUIButton.setOnMouseClicked(e -> {
			GUIMenu(window);
		});
		Button textButton = new Button("Console Game");
		textButton.setStyle("-fx-font: 20 arial; -fx-base: lightblue;");
		textButton.setOnMouseClicked(e -> {
			Connect4TextConsole startGame = new Connect4TextConsole(new Connect4());
			stage.close();
			startGame.start();
		});

		/** Creating the scene for start menu			*/
		HBox lBox = new HBox(8);
		lBox.getChildren().add(startLabel);
		lBox.setPadding(new Insets(10, 10, 10, 10));
		lBox.setAlignment(Pos.CENTER);

		/** Buttons in HBox		*/
		HBox localB = new HBox(8);
		localB.getChildren().addAll(GUIButton, textButton);
		localB.setPadding(new Insets(10, 10, 10, 10));
		localB.setAlignment(Pos.CENTER);

		/** Add HBox to Pane		*/
		BorderPane bPane = new BorderPane();
		bPane.setStyle("-fx-background-color: whitesmoke");
		bPane.setTop(lBox);
		bPane.setCenter(localB);

		/** Set the scene		*/
		startUpScene = new Scene(bPane, 400, 150);
		window.setScene(startUpScene);
		window.setTitle("Connect 4 Game");
		Stage newStage = (Stage) window.getScene().getWindow();
		window.show();
	}

	/**
	 * Menu for GUI game
	 * 
	 * @param stage to create new window for 1 or 2 players
	 */
	private void GUIMenu(Stage stage) {
		window = stage;

		/** Adding Hbox		*/
		Label newLabel = new Label(" How do you want to play your game?");
		newLabel.setFont(new Font("Britannic Bold", 23));
		HBox labelBox = new HBox(8);
		labelBox.getChildren().add(newLabel);
		labelBox.setPadding(new Insets(10, 10, 10, 10));
		labelBox.setAlignment(Pos.CENTER);

		/** Adding Buttons for Menuf		*/
		Button compButton = new Button("Against Computer");
		compButton.setStyle("-fx-font: 20 arial; -fx-base: lightblue;");
		Button playButton = new Button("Against Player");
		playButton.setStyle("-fx-font: 20 arial; -fx-base: lightblue;");

		HBox localB = new HBox(8);
		localB.getChildren().addAll(compButton, playButton);
		localB.setPadding(new Insets(10, 10, 10, 10));
		localB.setAlignment(Pos.CENTER);

		/** add to Pane		*/
		BorderPane bPane = new BorderPane();
		bPane.setStyle("-fx-background-color: whitesmoke");
		bPane.setTop(newLabel);
		bPane.setCenter(localB);

		/** set Scene		*/
		startUpScene = new Scene(bPane, 400, 150);
		window.setScene(startUpScene);
		window.setTitle("Connect 4 Game");

		Stage newStage = (Stage) window.getScene().getWindow();
		window.show();

		/** Player VS Player game mode		*/
		playButton.setOnAction(e -> pVSplayer(new Stage()));

		compButton.setOnAction(e -> {
			compGame = true;
			pVScomp(new Stage());
		});

	}

	/**
	 * Launch to two players
	 * 
	 * @param stage to create a new window
	 */
	private void pVSplayer(Stage stage) {
		stage.setOnCloseRequest(e -> {
			e.consume();
			closeGame();
		});
		stage.setTitle("Player VS Player");
		stage.setScene(new Scene(board()));
		stage.getScene().getWindow();
		stage.show();
		window.close();
		System.out.println((redT ? "Player #1: YELLOW" : "Player #2: RED ") + " turn");

	}

	/**
	 * Launch to player vs computer
	 * 
	 * @param stage to create a new window
	 */
	private void pVScomp(Stage stage) {
		stage.setOnCloseRequest(e -> {
			e.consume();
			closeGame();
		});
		stage.setTitle("Player VS Computer");
		stage.setScene(new Scene(board()));
		stage.getScene().getWindow();
		stage.show();
		window.close();
		System.out.println((redT ? "Computer: YELLOW" : "Player: RED ") + " turn");
	}

	/**
	 * Parent method to create the grid for a game
	 * 
	 * @return the game board
	 */
	private Parent board() {

		Pane rootPane = new Pane();
		rootPane.getChildren().add(pane);
		Shape gridShape = createGrid();
		rootPane.getChildren().add(gridShape);
		rootPane.getChildren().addAll(grid());

		return rootPane;
	}

	/**
	 * This method let user to know what columns they are currently
	 * 
	 * @return list of the columns.
	 */

	public List<Rectangle> grid() {
		List<Rectangle> list = new ArrayList<>();

		for (int ii = 0; ii < colums; ii++) {
			Rectangle rectangle = new Rectangle(t_size * (rows + 1), t_size);
			rectangle.setTranslateX(ii * (t_size + 6) + t_size / 3);
			rectangle.setFill(Color.TRANSPARENT);

			rectangle.setOnMouseEntered(e -> rectangle.setFill(Color.rgb(204, 250, 250, 0.10)));
			rectangle.setOnMouseExited(e -> rectangle.setFill(Color.TRANSPARENT));

			/** to pass in color		*/
			final int col_ii = ii;

			rectangle.setOnMouseClicked(e -> {
				placeT(new Token(redT), col_ii);

				if (compGame) {
					placeCompT(new Token(compGame));
				}
			});
			list.add(rectangle);
		}
		return list;

	}

	/**
	 * placeCompT method placed tokens when game Computer VS player
	 * 
	 * @param token on the board
	 */
	private void placeCompT(Token token) {
		int r = rows - 1;
		Random rand = new Random();
		int col = rand.nextInt(7);

		do {
			if (!getToken(col, r).isPresent()) {
				break;
			}
			r--;
		} while (r >= 0);

		if (r < 0) {
			return;
		}
		/** add token		*/
		boardGrid[col][r] = token;
		compList.add(token);
		/** make token visible		*/
		pane.getChildren().add(token);
		token.setTranslateX(col * (t_size + 6) + t_size / 3);
		token.setTranslateY(r * (t_size + 6) + t_size / 3);

		/** row-game winner check		*/
		int cRow = r;
		if (gameWin(col, cRow)) {
			gameOver();
		}

		redT = !redT;
		System.out.println((redT ? "Computer: YELLOW" : "Player: RED ") + " turn");

	}

	/**
	 * placeT is method that place tokens in selected columns. Game player VS player
	 * 
	 * @param token the player object
	 * @param col   the columns selected with mouse.
	 */
	private void placeT(Token token, int col) {

		int r = rows - 1;

		do {
			if (!getToken(col, r).isPresent()) {
				break;
			}
			r--;
		} while (r >= 0);

		if (r < 0) {
			return;
		}
		/** add token		*/
		boardGrid[col][r] = token;
		compList.add(token);
		/** make token visible		*/
		pane.getChildren().add(token);
		token.setTranslateX(col * (t_size + 6) + t_size / 3);
		token.setTranslateY(r * (t_size + 6) + t_size / 3);

		/** row-game winner check		*/
		int cRow = r;

		if (gameWin(col, cRow)) {
			gameOver();
		}
		if (compGame) {
			redT = !redT;
			System.out.println((redT ? "Computer: YELLOW" : "Player: RED ") + " turn");
		} else {
			redT = !redT;
			System.out.println((redT ? "Player: YELLOW" : "Player: RED ") + " turn");
		}

	}

	/**
	 * gameWin method read the winner by getting 4 rows(horizontally, vertically,
	 * diagonally.
	 * 
	 * @param col
	 * @param cRow
	 * @return true(if winner)/false(if lost)
	 */
	private boolean gameWin(int col, int cRow) {
		/** horizontal		*/
		List<Point2D> horizList = IntStream.rangeClosed(col - 3, col + 3).mapToObj(c -> new Point2D(c, cRow))
				.collect(Collectors.toList());
		/** vertically		*/
		List<Point2D> verList = IntStream.rangeClosed(cRow - 3, cRow + 3).mapToObj(r -> new Point2D(col, r))
				.collect(Collectors.toList());
		/** diagonal		*/
		Point2D dPointR = new Point2D(col - 3, cRow + 3);
		List<Point2D> diagRight = IntStream.rangeClosed(0, 6).mapToObj(i -> dPointR.add(i, -i))
				.collect(Collectors.toList());

		Point2D dPointL = new Point2D(col - 3, cRow - 3);
		List<Point2D> diagLeft = IntStream.rangeClosed(0, 6).mapToObj(i -> dPointL.add(i, i))
				.collect(Collectors.toList());

		return winCheck(horizList) || winCheck(verList) || winCheck(diagLeft) || winCheck(diagRight);
	}

	/**
	 * Helper method to check if there winner combinations in rows
	 * 
	 * @param slot checking combinations on the board
	 * @return true(if win) / false( if lost)
	 */
	private boolean winCheck(List<Point2D> slot) {
		int combination = 0;
		for (Point2D s : slot) {
			int col = (int) s.getX();
			int row = (int) s.getY();
			Token token = getToken(col, row).orElse(new Token(!redT));
			if (token.red == redT) {
				combination++;
				if (combination == 4) {
					return true;
				}
			} else {
				combination = 0;
			}
		}
		return false;
	}

	/**
	 * gameOver method tells the winner of the game.
	 */
	private void gameOver() {

		Stage stage = new Stage();
		stage.setTitle("WINNER!");
		Label label = new Label("CONGRATULATIONS!");
		label.setFont(new Font("Britannic Bold", 22));
		Button exitButton = new Button("EXIT");
		VBox vpane = new VBox(8);

		if (redT == true) {
			Label label2 = new Label("YELLOW WON!!!");
			label2.setFont(new Font("Britannic Bold", 22));
			vpane.getChildren().addAll(label, label2, exitButton);
			vpane.setAlignment(Pos.CENTER);
			exitButton.setOnAction(e -> {
				System.out.println("EXIT");
				System.exit(0);
			});
		} else {
			Label label3 = new Label("RED WON!!!");
			label3.setFont(new Font("Britannic Bold", 22));
			vpane.getChildren().addAll(label, label3, exitButton);
			vpane.setAlignment(Pos.CENTER);
			exitButton.setOnAction(e -> {
				System.out.println("EXIT");
				System.exit(0);
			});
		}
		Scene scene = new Scene(vpane, 300, 150);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * getToken return the token to the right columns on a game
	 * 
	 * @param c columns on the board
	 * @param r rows on the board
	 * @return Null if empty and the token for the board.
	 */
	private Optional<Token> getToken(int c, int r) {
		if (c < 0 || c >= colums || r < 0 || r >= rows) {
			return Optional.empty();
		}
		return Optional.ofNullable(boardGrid[c][r]);
	}

	/**
	 * createGrid method create a grid for the game Create the color and lightning
	 * by the columns
	 * 
	 * @return shape
	 */
	private Shape createGrid() {

		Shape shape = new Rectangle((colums + 1) * t_size, (rows + 1) * t_size);

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < colums; y++) {
				Circle circle = new Circle(t_size / 2);
				circle.setCenterX(t_size / 2);
				circle.setCenterY(t_size / 2);
				circle.setTranslateX(y * (t_size + 6) + t_size / 3);
				circle.setTranslateY(x * (t_size + 6) + t_size / 3);

				shape = Shape.subtract(shape, circle);
			}
		}

		Light.Distant lDistant = new Light.Distant();
		lDistant.setAzimuth(45.0);
		lDistant.setElevation(30.0);

		Lighting lighting = new Lighting();
		lighting.setLight(lDistant);
		lighting.setSurfaceScale(5.0);

		shape.setFill(Color.ROYALBLUE);
		shape.setEffect(lighting);
		return shape;
	}

	/**
	 * closeGame asking if user wants to leave the game
	 */
	private void closeGame() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exit Game");
		alert.setHeaderText("Are you sure want to leave?");
		ButtonType yes = new ButtonType("YES");
		ButtonType no = new ButtonType("NO");

		alert.getButtonTypes().setAll(yes, no);
		Optional<ButtonType> yeOptional = alert.showAndWait();
		if (yeOptional.get() == yes) {
			Platform.exit();
		} else if (yeOptional.get() == no) {
			alert.close();
		}

	}

	/**
	 * Launch point method of game
	 * 
	 * @param primaryStage main stage of game.
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		startWindow(primaryStage);
	}

	/**
	 * Main method to launch program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
}
