/*
 * Copyright 2009 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package org.sunspotworld.demo.robotview;

import java.awt.Color;
import java.util.Vector;

/**
 * Simple Robot World model consisting of Walls & Beacons
 *
 * @author Ron Goldman
 */
public class RobotWorld {

    public class Beacon {
        public char chr;
        public int x;
        public int y;

        public Beacon(char chr, int x, int y) {
            this.chr = chr;
            this.x = x;
            this.y = y;
        }
    }

    private Vector<Wall> walls = new Vector<Wall>();
    private Vector<Beacon> beacons = new Vector<Beacon>();

    private static final int DEFAULT_WALL_WIDTH = 20;

    private int width;
    private int height;
    private int startX;
    private int startY;
    private int startHeading;


    private static RobotWorld createRoom(int w, int h) {
        RobotWorld world = new RobotWorld(w, h);
        world.setStart(w / 3, 3 * h / 4, 0);
        world.addWall(new Wall(0, 0, w + DEFAULT_WALL_WIDTH, DEFAULT_WALL_WIDTH, Color.BLUE));
        world.addWall(new Wall(0, 0, DEFAULT_WALL_WIDTH, h, Color.BLUE));
        world.addWall(new Wall(0, h, w + DEFAULT_WALL_WIDTH, DEFAULT_WALL_WIDTH, Color.BLUE));
        world.addWall(new Wall(w, 0, DEFAULT_WALL_WIDTH, h, Color.BLUE));
        return world;
    }

    public static RobotWorld createEmptyRoom(int w, int h) {
        RobotWorld world = createRoom(w, h);
        world.setStart(w / 3, 3 * h / 4, 0);
        world.addBeacon('X', w / 16, h / 12);
        world.addBeacon('O', 15 * w / 16, 11 * h / 12);
        return world;
    }

    public static RobotWorld createMaze(int w, int h) {
        RobotWorld world = createRoom(w, h);
        world.setStart(w / 16, 11 * h / 12, 0);

        world.addBeacon('X', w / 16, 23 * h / 24);
        world.addBeacon('O', 31 * w / 32, h / 12);
        world.addVerticalWall(1, 1, 2, 8, 6);
        world.addVerticalWall(1, 4, 2, 8, 6);
        world.addVerticalWall(2, 0, 1, 8, 6);
        world.addVerticalWall(2, 2, 2, 8, 6);
        world.addVerticalWall(2, 5, 1, 8, 6);
        world.addVerticalWall(3, 1, 1, 8, 6);
        world.addVerticalWall(3, 4, 1, 8, 6);
        world.addVerticalWall(4, 4, 1, 8, 6);
        world.addVerticalWall(5, 0, 2, 8, 6);
        world.addVerticalWall(5, 3, 1, 8, 6);
        world.addVerticalWall(6, 1, 2, 8, 6);
        world.addVerticalWall(6, 5, 1, 8, 6);
        world.addVerticalWall(7, 2, 2, 8, 6);

        world.addHorizontalWall(1, 3, 1, 8, 6);
        world.addHorizontalWall(2, 2, 3, 8, 6);
        world.addHorizontalWall(3, 1, 1, 8, 6);
        world.addHorizontalWall(3, 3, 2, 8, 6);
        world.addHorizontalWall(3, 4, 1, 8, 6);
        world.addHorizontalWall(4, 5, 3, 8, 6);
        world.addHorizontalWall(5, 4, 2, 8, 6);
        world.addHorizontalWall(6, 2, 1, 8, 6);
        world.addHorizontalWall(7, 1, 1, 8, 6);
        world.addHorizontalWall(7, 3, 1, 8, 6);

        return world;
    }

    public static RobotWorld createObstacleCourse(int w, int h) {
        RobotWorld world = createRoom(w, h);
        world.setStart(w / 16,  h / 2, 90);
        world.addBeacon('X', w / 16, h / 2);
        world.addBeacon('O', 15 * w / 16, h / 2);

        world.addVerticalWall(1, 0, 1, 8, 6);
        world.addVerticalWall(1, 2, 2, 8, 6);
        world.addVerticalWall(2, 1, 2, 8, 6);
        world.addVerticalWall(3, 0, 2, 8, 7);
        world.addVerticalWall(3, 3, 2, 8, 7);
        world.addVerticalWall(7, 2, 2, 8, 6);

        world.addHorizontalWall(1, 4, 2, 8, 6);

        world.addBlock(1, 5, 3, 3, 8, 6);

        world.addBlock(4, 10, 2, 2, 16, 12);
        world.addBlock(5, 10, 2, 2, 16, 12);
        world.addBlock(6, 11, 2, 2, 16, 12);
        world.addBlock(7, 10, 2, 2, 16, 12);

        world.addBlock(8, 6, 2, 4, 16, 12);
        world.addBlock(9, 5, 2, 4, 16, 12);
        world.addBlock(10, 4, 2, 4, 16, 12);

        world.addBlock(9, 2, 5, 5, 16, 12);

        world.addBlock(19, 10, 3, 3, 32, 15);
        world.addBlock(20, 11, 3, 3, 32, 15);
        world.addBlock(19, 12, 3, 3, 32, 15);

        world.addBlock(24, 10, 3, 3, 32, 15);
        world.addBlock(25, 11, 3, 3, 32, 15);
        world.addBlock(26, 12, 3, 3, 32, 15);
        world.addBlock(25, 13, 3, 3, 32, 15);
        world.addBlock(24, 14, 3, 3, 32, 15);

        world.addBlock(24, 2, 3, 3, 32, 15);
        world.addBlock(23, 3, 3, 3, 32, 15);
        world.addBlock(25, 3, 3, 3, 32, 15);
        world.addBlock(24, 4, 3, 3, 32, 15);

        return world;
    }

    public RobotWorld(int w, int h) {
        width = w;
        height = h;
    }

    public void setStart(int x, int y, int h) {
        startX = x;
        startY = y;
        startHeading = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getStartHeading() {
        return startHeading;
    }

    public Vector<Wall> getWalls() {
        return walls;
    }

    private void addHorizontalWall(int x, int y, int w, int dx, int dy) {
        double f = width / dx;
        addWall(new Wall(x * f, y * height / dy, w * f + DEFAULT_WALL_WIDTH, DEFAULT_WALL_WIDTH, Color.BLUE));
    }

    private void addVerticalWall(int x, int y, int h, int dx, int dy) {
        double f = height / dy;
        addWall(new Wall(x * width / dx, y * f, DEFAULT_WALL_WIDTH, h * f, Color.BLUE));
    }

    private void addBlock(int x, int y, int w, int h, int dx, int dy) {
        addWall(new Wall(x * width / dx, y * height / dy, w * DEFAULT_WALL_WIDTH, h * DEFAULT_WALL_WIDTH, Color.BLUE));
    }

    public void addWall(Wall w) {
        if (!walls.contains(w)) {
            walls.add(w);
        }
    }

    public void removeWall(Wall w) {
        walls.remove(w);
    }

    public Vector<Beacon> getBeacons() {
        return beacons;
    }

    public void addBeacon(char chr, int x, int y) {
        beacons.add(new Beacon(chr, x, y));
    }

}
