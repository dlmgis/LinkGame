package cn.fouad.service;

import cn.fouad.commons.LinkInfo;
import cn.fouad.commons.Piece;

/**
 * 游戏业务逻辑的接口
 * 使用模型设计模式
 */
public interface GameService {

    void start();

    Piece[][] getPieces();

    Piece findPiece(int x, int y);

    LinkInfo link(Piece aPiece, Piece bPiece);

    boolean empty();

}
