package io.github.hquinteros.TDDSnakeFX;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;


class SnakeTest {
    //

    //can use a BeforeEach to initialize snake to a new snake on every test without having to write it manually
    //for every test

    @Test
    void testSnakeMoves() {
        Snake snake = new Snake(new Point2D(0, 0));

        snake.setDirection(Direction.RIGHT);

        snake.update();

        assertThat(snake.getHeadPosition(), is(new Point2D(1, 0)));
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testAllSnakeMoves(Direction direction){
        Snake snake = new Snake(new Point2D(0, 0));

        snake.setDirection(direction);

        snake.update();

        assertThat(snake.getHeadPosition(), is(direction.vector));
    }

    @Test
    void testSnakeFoodCollision() {
        //snake and food are occupying the same tile
        Snake snake = new Snake(new Point2D(10, 5));

        Food food = new Food(new Point2D(10, 5));

        assertTrue(snake.isCollidingWith(food));
    }

    @Test
    void testSnakeGrows(){
        //within the context of the game the snake can only grow if it moves and collides with a food object
        Snake snake = new Snake(new Point2D(0 , 0));
        snake.setDirection(Direction.RIGHT);
        snake.update();
        snake.grow();

        //two asserts in one test, preferable to have one assert per test
        assertThat(snake.getLength(), is(2));
        assertThat(snake.getBody(), hasItem(new Point2D(0, 0)));

    }

    //tests for X and Y being < 0 and being > 24
    @ParameterizedTest
    @ValueSource(ints = {-1, 25})
    void testSnakeOutOfBounds(int coordinates) {
        //assume 24 is the bounds for the game
        Snake snake = new Snake(new Point2D(coordinates, coordinates));
        assertTrue(snake.isOutOfBounds(24));
        //can add a second test to assertFalse
    }

    @Test
    void testSnakeDies() {
        Snake snake = new Snake(new Point2D(0, 0));
        //snake grows multiple times
        for (int i = 0; i < 5; i++) {
            snake.setDirection(Direction.RIGHT);
            snake.update();
            snake.grow();
        }
        //begin moving snake in a way to collide with itself, up -> left > down.
        snake.setDirection(Direction.UP);
        snake.update();

        snake.setDirection(Direction.LEFT);
        snake.update();


        snake.setDirection(Direction.DOWN);
        snake.update();


        assertTrue(snake.isDead());
    }
}
