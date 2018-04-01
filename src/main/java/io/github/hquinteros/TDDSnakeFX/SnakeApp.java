package io.github.hquinteros.TDDSnakeFX;

//using fxgl 3.9, using latest fxgl version has bug where snake never stops growing. Was fixed by downgrading versions and not changing any logic in the code
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.app.DSLKt.onKeyDown;


public class SnakeApp extends GameApplication {

    private static final int GAME_SIZE = 24;
    private static final int TILE_SIZE = 30;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setCloseConfirmation(false);
        settings.setProfilingEnabled(false);
        settings.setMenuEnabled(true);
        settings.setMenuKey(KeyCode.ESCAPE);
        settings.setTitle("Classic Snake");
        settings.setIntroEnabled(false);
        settings.setFullScreen(false);

        settings.setWidth(TILE_SIZE * GAME_SIZE);
        settings.setHeight(TILE_SIZE * GAME_SIZE);
    }

    private Game game;

    @Override
    protected void initInput() {
        onKeyDown(KeyCode.W, "UP", () -> game.setDirection(Direction.UP));
        onKeyDown(KeyCode.S, "DOWN", () -> game.setDirection(Direction.DOWN));
        onKeyDown(KeyCode.A, "LEFT", () -> game.setDirection(Direction.LEFT));
        onKeyDown(KeyCode.D, "RIGHT", () -> game.setDirection(Direction.RIGHT));
    }
    private double speed = 0.15;

    @Override
    protected void initGame() {
        game = new Game(GAME_SIZE);
        speed = 0.155 - getGameState().getGameDifficulty().ordinal() * .041;
    }

    private double t = 0;

    @Override
    protected void onUpdate(double tpf) {
        t += tpf;

        if (t > speed) {
            t = 0;
            game.update();

            if (game.isOver()) {
                getDisplay().showMessageBox("Game over", this::startNewGame);
            }
        }

        render();
    }

    private void render() {
        GraphicsContext g = getGameScene().getGraphicsContext();

        g.setFill(Color.BLUE);
        game.getSnake().getBody().forEach(p -> g.fillRect(p.getX() * TILE_SIZE, p.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE));

        g.setFill(Color.YELLOW);
        g.fillRect(game.getFood().getPosition().getX() * TILE_SIZE, game.getFood().getPosition().getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}