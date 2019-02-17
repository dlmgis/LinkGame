package cn.fouad.commons;

/**
 * rowNums     棋盘行数
 * columnNums  棋盘列数
 * beginPoint  起始点
 * perGrade    等级
 * limitedTime 限制时间，单位秒 ，时间到了结束游戏
 */
public class GameConfiguration {

    private int rowNums;
    private int columnNums;
    private Point beginPoint;
    private long perGrade;
    private long limitedTime;
    public final int MODELNUM = 5; // 模式数量 ，游戏所包含的布局方式

    /**
     * @param rowNums     棋盘行数
     * @param columnNums  棋盘列数
     * @param beginPoint  起始点
     * @param perGrade    每个格子的边长（格子是正方形的）
     * @param limitedTime 限制时间，单位秒 ，时间到了结束游戏
     */
    public GameConfiguration(int rowNums, int columnNums, Point beginPoint,
                             long perGrade, long limitedTime) {
        super();
        this.rowNums = rowNums;
        this.columnNums = columnNums;
        this.beginPoint = beginPoint;
        this.perGrade = perGrade;
        this.limitedTime = limitedTime;
    }

    /**
     * 获取行数
     * @return 行数
     */
    public int getRowNums() {
        return rowNums;
    }

    /**
     * 获取列数
     * @return 列数
     */
    public int getColumnNums() {
        return columnNums;
    }

    /**
     * 获取起始点横坐标
     * @return X起始点
     */
    public int getBeginPieceX() {
        return beginPoint.getX();
    }

    /**
     * 获取起始点纵坐标
     * @return Y起始点
     */
    public int getBeginPieceY() {
        return beginPoint.getY();
    }

    /**
     * 获取起始点，点对象
     * @return 起始点，点对象
     */
    public Point getBeginPoint() {
        return beginPoint;
    }

    /**
     * 获取等级
     * @return 等级
     */
    public long getPerGrade() {
        return perGrade;
    }

    /**
     * 获取剩余时间
     * @return 剩余时间
     */
    public long getLimitedTime() {
        return limitedTime;
    }
}
