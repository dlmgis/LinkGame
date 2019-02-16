package cn.fouad.commons;

import java.awt.image.BufferedImage;

import cn.fouad.utils.ImageUtils;

/**
 * 一个连连看元素 游戏图片
 * beginPoint 图片左上角的坐标
 * endPoint 图片右下角的坐标，由构造方法自动获得
 * image 显示的图片
 * indexPoint 索引点
 */
public class Piece {
    private Point beginPoint;
    private Point endPoint;
    private Point indexPoint;
    private BufferedImage image;

    /**
     * @param beginPoint 起始点
     * @param indexPoint 索引点
     * @param image 图片
     */
    public Piece(Point beginPoint, Point indexPoint, BufferedImage image) {
        this.beginPoint = beginPoint;
        this.endPoint = new Point(beginPoint.getX() + image.getWidth(),
                beginPoint.getY() + image.getHeight());
        this.indexPoint = indexPoint;
        this.image = image;
    }

    /**
     * @param beginPoint 起始点
     * @param indexPoint 索引点
     * @param xStep X步长
     * @param yStep Y步长
     */
    public Piece(Point beginPoint, Point indexPoint, int xStep, int yStep) {
        this.beginPoint = beginPoint;
        this.endPoint = new Point(beginPoint.getX() + xStep, beginPoint.getY()
                + yStep);
        this.indexPoint = indexPoint;
        this.image = null;
    }

    public Piece() {

    }

    /**
     * @param indexPoint 索引点
     */
    public Piece(Point indexPoint) {
        super();
        this.beginPoint = null;
        this.endPoint = null;
        this.indexPoint = indexPoint;
        this.image = null;
    }

    /**
     * 获取起始点X坐标
     * @return 起始点X的值
     */
    public int getBeginX() {
        return getBeginPoint().getX();
    }

    /**
     * 获取起始点Y坐标
     * @return 起始点Y的值
     */
    public int getBeginY() {
        return getBeginPoint().getY();
    }

    public int getEndX() {
        return getEndPoint().getX();
    }

    public int getEndY() {
        return getEndPoint().getY();
    }

    /**
     * 获取X坐标值
     * @return X坐标值
     */
    public int getXIndex() {
        return getIndexPoint().getX();
    }

    /**
     * 获取Y坐标值
     * @return Y坐标值
     */
    public int getYIndex() {
        return getIndexPoint().getY();
    }

    /**
     * 获取起始点坐标
     * @return 起始点坐标
     */
    public Point getBeginPoint() {
        return beginPoint;
    }

    /**
     * 设置起始点坐标
     * @param beginPosition 起始点坐标
     */
    public void setBeginPoint(Point beginPosition) {
        this.beginPoint = beginPosition;
    }

    /**
     * @return 设置点坐标
     */
    private Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPosition) {
        this.endPoint = endPosition;
    }

    /**
     * 获取图片索引
     * @return 点坐标
     */
    public Point getIndexPoint() {
        return indexPoint;
    }

    public void setIndexPoint(Point indexPosition) {
        this.indexPoint = indexPosition;
    }

    /**
     * 获取图片
     * @return 图片
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * 设置图片
     * @param image 图片
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * 判断是否有相同的图片
     * @param piece 游戏图片
     * @return 是否有相同图片
     */
    public boolean HasSameImage(Piece piece) {
        return ImageUtils.equals(image, piece.getImage());
    }

}
