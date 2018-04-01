package io.github.hquinteros.TDDSnakeFX;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;


class SnakeTest {

    @Test
    void testSnakeMoves() {
        Snake snake = new Snake(new Point2D(0, 0));

        snake.setDirection(Direction.RIGHT);

        snake.update();

        assertThat(snake.getPosition(), is(new Point2D(1, 0)));
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testAllSnakeMoves(Direction direction){
        Snake snake = new Snake(new Point2D(0, 0));

        snake.setDirection(direction);

        snake.update();

        assertThat(snake.getPosition(), is(direction.vector));
    }

    @Test
    void testSnakeFoodCollision() {
        //snake and food are occupying the same tile
        Snake snake = new Snake(new Point2D(10, 5));

        Food food = new Food(new Point2D(10, 5));

        assertTrue(snake.isCollidingWith(food));
    }
}
