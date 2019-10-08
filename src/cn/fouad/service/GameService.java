package cn.fouad.service;

import cn.fouad.commons.LinkInfo;
import cn.fouad.commons.Piece;

/**
 * 游戏业务逻辑的接口
 */
public interface GameService {

    /**
     * 启动，初始化
     */
    void start();

    Piece[][] getPieces();

    /**
     * @param x x
     * @param y y
     * @return 游戏图片
     */
    Piece findPiece(int x, int y);

    /**
     * @param aPiece 一个图片
     * @param bPiece 一个图片
     * @return 连接信息
     */
    LinkInfo link(Piece aPiece, Piece bPiece);

    /**
     * 判断还有没有游戏图片了
     * @return 布尔
     */
    boolean empty();

}
