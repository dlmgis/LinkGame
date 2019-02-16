package cn.fouad.commons;


/**
 * 点类型
 * 二维点类型，提供设置和获取方法
 * 点的八个方向
 * XY方向上是否共线
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    /**
     * @param obj 点的对象
     * @return 返回传入的参数和调用者两点是否重合
     */
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point point = (Point) obj;
            return point.getX() == this.getX() && point.getY() == this.getY();
        }
        return false;
    }

    /**
     * @param point 点坐标
     * @return 我在他的左边
     */
    public boolean isLeft(Point point) {
        return point.getX() > this.getX() && point.getY() == this.getY();

    }


    /**
     * @param point 点坐标
     * @return 我在他的右边
     */
    public boolean isRight(Point point) {
        return point.getX() < this.getX() && point.getY() == this.getY();
    }

    /**
     * @param point 点坐标
     * @return 我在他的上面
     */
    public boolean isUp(Point point) {
        return point.getX() == this.getX() && point.getY() > this.getY();
    }


    /**
     * @param point 点坐标
     * @return 我在他的下面
     */
    public boolean isDown(Point point) {
        return point.getX() == this.getX() && point.getY() < this.getY();

    }


    /**
     * @param point 点坐标
     * @return 我在他的左上方
     */
    public boolean isLeftUp(Point point) {
        return point.getX() > this.getX() && point.getY() > this.getY();
    }


    /**
     * @param point 点坐标
     * @return 我在你的左下方
     */
    public boolean isLeftDown(Point point) {
        return point.getX() > this.getX() && point.getY() < this.getY();
    }


    /**
     * @param point 点坐标
     * @return 我在他的右上方
     */
    public boolean isRightUp(Point point) {
        return point.getX() < this.getX() && point.getY() > this.getY();
    }


    /**
     * @param point 点坐标
     * @return 我在他的右下方
     */
    public boolean isRightDown(Point point) {
        return point.getX() < this.getX() && point.getY() < this.getY();

    }

    /**
     * @param point 点坐标
     * @return 我和你在X上一条线
     */
    public boolean inSameXLine(Point point) {
        return point.getY() == this.getY();
    }

    /**
     * @param point 点坐标
     * @return 我和你在Y上一条线
     */
    public boolean inSameYLine(Point point) {
        return point.getX() == this.getX();
    }
}
