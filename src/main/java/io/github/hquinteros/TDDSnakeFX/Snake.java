package io.github.hquinteros.TDDSnakeFX;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private Direction direction = Direction.RIGHT;
    private Point2D headPosition;
    private Point2D previousTailPosition;
    private List<Point2D> body = new ArrayList<>();

    public Snake(Point2D headPosition){
        this.headPosition = headPosition;
        this.previousTailPosition = headPosition;
        body.add(headPosition);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void update() {
        headPosition = headPosition.add(direction.vector);
        previousTailPosition = body.remove(body.size() - 1);
        body.add(0, headPosition);
    }

    public Point2D getHeadPosition() {
        return headPosition;
    }

    public boolean isCollidingWith(Food food) {
        return headPosition.equals(food.getPosition());
    }

    public int getLength() {
        return body.size();
    }

    public List<Point2D> getBody() {
        return body;
    }

    public void grow() {
        body.add(previousTailPosition);
    }

    public boolean isOutOfBounds(int size) {
        return headPosition.getX() < 0 || headPosition.getY() < 0 || headPosition.getX() > size || headPosition.getY() > size;
    }

    public boolean isDead() {
        //head position should be the 0th element, if its not then it collided with the body.
        return body.lastIndexOf(headPosition) > 0;
    }
}
