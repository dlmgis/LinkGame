package cn.fouad.service;

import java.awt.image.BufferedImage;
import java.util.List;

import cn.fouad.commons.GameConfiguration;
import cn.fouad.commons.Piece;
import cn.fouad.commons.Point;
import cn.fouad.exception.GameException;
import cn.fouad.utils.ImageUtils;

/**
 * 抽象游戏模型
 */
public abstract class GameModel {
    private int commonImageWidth;
    private int commonImageHeight;

    /**
     * 读取游戏配置创建游戏棋盘
     * @param config 游戏配置
     * @return 游戏图片数组
     */
    public Piece[][] createRealPieces(GameConfiguration config) {
        // 从配置信息读取游戏棋盘的行数和列数
        Piece[][] result = new Piece[config.getRowNums()][config
                .getColumnNums()];
        // 校验图片数组生成列表
        List<Piece> logicPieces = createLogicPieces(config, result);
        // 从合法的棋盘中获得图片
        List<BufferedImage> pieceImages = getPieceImages(logicPieces.size());
        // 从图片中读取图片的宽度和高度
        this.commonImageWidth = pieceImages.get(0).getWidth();
        this.commonImageHeight = pieceImages.get(0).getHeight();
        // 从配置文件获取起始点坐标
        int beginX = config.getBeginPoint().getX();
        int beginY = config.getBeginPoint().getY();
        // 遍历图片集合，填充索引点，显示的图片
        for (int i = 0; i < logicPieces.size(); i++) {
            Point indexPoint = logicPieces.get(i).getIndexPoint();

            result[indexPoint.getX()][indexPoint.getY()] = new Piece(new Point(
                    indexPoint.getX() * this.commonImageWidth + beginX,
                    indexPoint.getY() * this.commonImageHeight + beginY),
                    indexPoint, pieceImages.get(i));
        }
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                if (result[i][j].getBeginPoint() == null) {
                    result[i][j] = new Piece(new Point(result[i][j]
                            .getIndexPoint().getX()
                            * this.commonImageWidth
                            + beginX, result[i][j].getIndexPoint().getY()
                            * this.commonImageHeight + beginY),
                            new Point(i, j), this.commonImageWidth,
                            this.commonImageHeight);
                }
            }
        }
        return result;
    }

    /**
     * 通过校验生成合法可玩的游戏图片排布
     * @param config 配置文件
     * @param pieces 游戏图片数组
     * @return 图片列表
     */
    abstract public List<Piece> createLogicPieces(GameConfiguration config,
                                                  Piece[][] pieces);

    /**
     * 根据棋盘需要的图片数量用工具类读取图片
     * @param num 图片数量
     * @return 图片列表
     */
    private List<BufferedImage> getPieceImages(int num) {
        try {
            return ImageUtils.getPieceImages(num);
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取组件图片宽度
     * @return 组件图片宽度
     */
    public int getCommonImageWidth() {
        return commonImageWidth;
    }

    /**
     * 获取组件图片的高度
     * @return 组件图片的高度
     */
    public int getCommonImageHeight() {
        return commonImageHeight;
    }
}
