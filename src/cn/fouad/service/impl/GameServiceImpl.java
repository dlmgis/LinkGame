package cn.fouad.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.fouad.commons.GameConfiguration;
import cn.fouad.commons.LinkInfo;
import cn.fouad.commons.Piece;
import cn.fouad.commons.Point;
import cn.fouad.service.GameModel;
import cn.fouad.service.GameService;
import org.jetbrains.annotations.Nullable;

/**
 * 使用模板模式
 * 游戏业务逻辑的具体实现
 */
public class GameServiceImpl implements GameService {
    private Piece[][] pieces = null;
    private GameConfiguration config;
    private GameModel gameModel;
    private long grade = 0;

    /**
     * 通过配置信息创建游戏业务逻辑
     * @param config 配置信息
     */
    public GameServiceImpl(GameConfiguration config) {
        this.config = config;
    }

    /**
     * 启动 获得游戏模式
     */
    @Override
    public void start() {
        this.gameModel = createGameModel(config);
        assert gameModel != null;
        this.pieces = gameModel.createRealPieces(config);
    }

    /**
     * 从配置对象中读取信息随机生成游戏模式
     * @param config 配置对象
     * @return 游戏模式
     */
    private GameModel createGameModel(GameConfiguration config) {
        Random random = new Random();
        switch (random.nextInt(config.MODELNUM)) {
            case 0:
                return new SimpleGameModelA();
            case 1:
                return new SimpleGameModelB();
            case 2:
                return new SimpleGameModelC();
            case 3:
                return new SimpleGameModelD();
            case 4:
                return new TraditionalModel();
            default:
                return null;
        }
    }

    /**
     * 获取游戏图片
     * @return 游戏图片数组
     */
    @Override
    public Piece[][] getPieces() {
        return this.pieces;
    }

    /**
     * 游戏核心
     * 判断两个图片是否能连上，返回连接信息
     * @param aPiece 一幅图片
     * @param bPiece 一幅图片
     * @return 连接信息
     */
    @Override
    public LinkInfo link(Piece aPiece, Piece bPiece) {
        Point aPieceBeginPoint = aPiece.getBeginPoint();
        Point bPieceBeginPoint = bPiece.getBeginPoint();
        if (aPieceBeginPoint.equals(bPieceBeginPoint)) {
            return null;
        }
        if (!aPiece.HasSameImage(bPiece)) {
            return null;
        }
        if (aPieceBeginPoint.getX() > bPieceBeginPoint.getX())
            return link(bPiece, aPiece);

        int commonImageWidth = gameModel.getCommonImageWidth();
        int commonImageHeight = gameModel.getCommonImageHeight();

        if (aPieceBeginPoint.inSameXLine(bPieceBeginPoint)) {
            if (!isXBlock(aPiece, bPiece, commonImageWidth)) {
                return new LinkInfo(aPiece, bPiece);
            }
        }

        if (aPieceBeginPoint.inSameYLine(bPieceBeginPoint)) {
            if (!isYBlock(aPiece, bPiece, commonImageHeight)) {
                return new LinkInfo(aPiece, bPiece);
            }
        }

        Piece cornerPiece = getCornerPiece(aPiece, bPiece, commonImageWidth,
                commonImageHeight);
        if (cornerPiece != null) {
            return new LinkInfo(aPiece, cornerPiece, bPiece);
        }
        Map<Piece, Piece> ret = getLinkPieces(aPiece, bPiece, commonImageWidth,
                commonImageHeight);
        if (ret.size() > 0)
            return getShortCut(aPiece, bPiece, ret, getDistance(aPiece, bPiece));
        return null;
    }

    /**
     * @param aPiece 一个图片
     * @param bPiece 一个图片
     * @param xStep X轴路径长度
     * @param yStep Y轴路径长度
     * @return 图片
     */
    @Nullable
    private Piece getCornerPiece(Piece aPiece, Piece bPiece, int xStep, int yStep) {
        if (aPiece.getBeginX() > bPiece.getBeginX()) {
            return getCornerPiece(bPiece, aPiece, xStep, yStep);
        }
        List<Piece> aPieceUpChannel = null;
        List<Piece> aPieceDownChannel = null;
        List<Piece> bPieceUpChannel = null;
        List<Piece> bPieceDownChannel = null;
        List<Piece> aPieceRightChannel = getRightChannel(aPiece,
                bPiece.getBeginX(), xStep);
        List<Piece> bPieceLeftChannel = getLeftChannel(bPiece,
                aPiece.getBeginX(), xStep);
        Piece result = null;
        if (bPiece.getBeginPoint().isRightUp(aPiece.getBeginPoint())) {
            aPieceUpChannel = getUpChannel(aPiece, bPiece.getBeginY(), yStep);
            bPieceDownChannel = getDownChannel(bPiece, aPiece.getBeginY(),
                    yStep);
            result = getCrossPoint(aPieceUpChannel, bPieceLeftChannel);
            return result == null ? getCrossPoint(aPieceRightChannel,
                    bPieceDownChannel) : result;
        }
        if (bPiece.getBeginPoint().isRightDown(aPiece.getBeginPoint())) {
            aPieceDownChannel = getDownChannel(aPiece, bPiece.getBeginY(),
                    yStep);
            bPieceUpChannel = getUpChannel(bPiece, aPiece.getBeginY(), yStep);
            result = getCrossPoint(aPieceRightChannel, bPieceUpChannel);
            return result == null ? getCrossPoint(aPieceDownChannel,
                    bPieceLeftChannel) : result;
        }
        return null;
    }

    private LinkInfo getShortCut(Piece aPiece, Piece bPiece,
                                 Map<Piece, Piece> linkPieces, int distance) {
        List<LinkInfo> infos = new ArrayList<>();
        for (Object key : linkPieces.keySet()) {
            infos.add(new LinkInfo(aPiece, (Piece) key, linkPieces
                    .get(key), bPiece));
        }
        return getShortCut(infos, distance);
    }

    private LinkInfo getShortCut(List<LinkInfo> infos, int distance) {
        LinkInfo result = null;
        int tempDistance = 0;
        for (int i = 0; i < infos.size(); i++) {
            int aDistance = totalDistance(infos.get(i).getPieces());
            LinkInfo info = infos.get(i);
            ;
            if (i == 0) {
                result = info;
                tempDistance = aDistance - distance;
            }
            if (aDistance - distance < tempDistance) {
                result = info;
                tempDistance = aDistance - distance;
            }
        }
        return result;
    }

    private int totalDistance(List<Piece> pieces) {
        int result = 0;
        for (int i = 0; i < pieces.size() - 1; i++) {
            result += getDistance(pieces.get(i), pieces.get(i + 1));
        }
        return result;
    }

    private int getDistance(Piece aPiece, Piece bPiece) {
        return Math.abs(aPiece.getBeginX() - bPiece.getBeginX())
                + Math.abs(aPiece.getBeginY() - bPiece.getBeginY());
    }

    private Map<Piece, Piece> getLinkPieces(Piece aPiece, Piece bPiece, int xStep,
                                            int yStep) {
        Map<Piece, Piece> result = new HashMap<Piece, Piece>();
        int maxWidth = gameModel.getCommonImageWidth()
                * (config.getRowNums() + 2) + config.getBeginPieceX();
        int maxHeight = gameModel.getCommonImageHeight()
                * (config.getColumnNums() + 2) + config.getBeginPieceY();

        List<Piece> aPieceUpChannel = getUpChannel(aPiece,
                config.getBeginPieceY() - 2 * gameModel.getCommonImageHeight(),
                yStep);
        List<Piece> aPieceDownChannel = getDownChannel(aPiece, maxHeight, yStep);
        List<Piece> aPieceLeftChannel = getLeftChannel(aPiece, 0, xStep);
        List<Piece> aPieceRightChannel = getRightChannel(aPiece, maxWidth,
                xStep);
        List<Piece> bPieceUpChannel = getUpChannel(bPiece,
                config.getBeginPieceY() - 2 * gameModel.getCommonImageHeight(),
                yStep);
        List<Piece> bPieceDownChannel = getDownChannel(bPiece, maxHeight, yStep);
        List<Piece> bPieceLeftChannel = getLeftChannel(bPiece, 0, xStep);
        List<Piece> bPieceRightChannel = getRightChannel(bPiece, maxWidth,
                xStep);
        if (bPiece.getBeginPoint().isLeftUp(aPiece.getBeginPoint())
                || bPiece.getBeginPoint().isLeftDown(aPiece.getBeginPoint())) {
            return getLinkPieces(bPiece, aPiece, xStep, yStep);
        }

        if (bPiece.getBeginPoint().inSameXLine(bPiece.getBeginPoint())) {
            result.putAll(getXLinkPieces(aPieceUpChannel, bPieceUpChannel,
                    yStep));
            result.putAll(getXLinkPieces(aPieceDownChannel, bPieceDownChannel,
                    yStep));
        }

        if (bPiece.getBeginPoint().inSameYLine(bPiece.getBeginPoint())) {
            result.putAll(getYLinkPieces(aPieceLeftChannel, bPieceLeftChannel,
                    xStep));
            result.putAll(getYLinkPieces(aPieceRightChannel,
                    bPieceRightChannel, xStep));
        }
        if (bPiece.getBeginPoint().isRightUp(aPiece.getBeginPoint())) {
            result.putAll(getXLinkPieces(aPieceUpChannel, bPieceDownChannel,
                    xStep));
            result.putAll(getYLinkPieces(aPieceRightChannel, bPieceLeftChannel,
                    yStep));
            result.putAll(getXLinkPieces(aPieceUpChannel, bPieceUpChannel,
                    xStep));
            result.putAll(getXLinkPieces(aPieceDownChannel, bPieceDownChannel,
                    xStep));
            result.putAll(getYLinkPieces(aPieceLeftChannel, bPieceLeftChannel,
                    yStep));
            result.putAll(getYLinkPieces(aPieceRightChannel,
                    bPieceRightChannel, yStep));
        }
        if (bPiece.getBeginPoint().isRightDown(aPiece.getBeginPoint())) {
            result.putAll(getXLinkPieces(aPieceDownChannel, bPieceUpChannel,
                    xStep));
            result.putAll(getYLinkPieces(aPieceRightChannel, bPieceLeftChannel,
                    yStep));
            result.putAll(getXLinkPieces(aPieceUpChannel, bPieceUpChannel,
                    xStep));
            result.putAll(getXLinkPieces(aPieceDownChannel, bPieceDownChannel,
                    xStep));
            result.putAll(getYLinkPieces(aPieceLeftChannel, bPieceLeftChannel,
                    yStep));
            result.putAll(getYLinkPieces(aPieceRightChannel,
                    bPieceRightChannel, yStep));
        }
        return result;
    }

    private Map<Piece, Piece> getXLinkPieces(List<Piece> aChannel,
                                             List<Piece> bChannel, int step) {
        Map<Piece, Piece> result = new HashMap<Piece, Piece>();
        for (Piece pieceOfaChannel : aChannel) {
            for (Piece pieceOfbChannel : bChannel) {
                if (pieceOfaChannel.getBeginY() == pieceOfbChannel.getBeginY()
                        && !isXBlock(pieceOfaChannel, pieceOfbChannel, step)) {
                    result.put(pieceOfaChannel, pieceOfbChannel);
                }
            }
        }
        return result;
    }

    private Map<Piece, Piece> getYLinkPieces(List<Piece> aChannel,
                                             List<Piece> bChannel, int step) {
        Map<Piece, Piece> result = new HashMap<Piece, Piece>();
        for (Piece pieceOfaChannel : aChannel) {
            for (Piece pieceOfbChannel : bChannel) {
                if (pieceOfaChannel.getBeginX() == pieceOfbChannel.getBeginX()
                        && !isYBlock(pieceOfaChannel, pieceOfbChannel, step)) {
                    result.put(pieceOfaChannel, pieceOfbChannel);
                }
            }
        }
        return result;
    }

    private Piece getCrossPoint(List<Piece> aChannel, List<Piece> bChannel) {
        for (Piece pieceOfaChannel : aChannel) {
            for (Piece pieceOfbChannel : bChannel) {
                if (pieceOfaChannel.getBeginPoint().equals(
                        pieceOfbChannel.getBeginPoint())) {
                    return pieceOfaChannel;
                }
            }
        }
        return null;
    }

    private boolean isXBlock(Piece aPiece, Piece bPiece, int step) {
        if (bPiece.getBeginPoint().isLeft(aPiece.getBeginPoint()))
            return isXBlock(bPiece, aPiece, step);
        for (int i = aPiece.getBeginX() + step; i < bPiece.getBeginX(); i += step) {
            if (this.hasImage(i, bPiece.getBeginY())) {
                return true;
            }
        }
        return false;
    }

    private boolean isYBlock(Piece aPiece, Piece bPiece, int step) {
        if (bPiece.getBeginPoint().isUp(aPiece.getBeginPoint()))
            return isYBlock(bPiece, aPiece, step);
        for (int i = aPiece.getBeginY() + step; i < bPiece.getBeginY(); i += step) {
            if (this.hasImage(bPiece.getBeginX(), i))
                return true;
        }
        return false;
    }

    private List<Piece> getUpChannel(Piece piece, int ylimit, int step) {
        List<Piece> channel = new ArrayList<Piece>();
        int x = piece.getBeginX();
        for (int i = piece.getBeginY() - step; i >= ylimit; i -= step) {
            Piece findPiece = findPiece(x, i);
            if (hasImage(x, i)) {
                return channel;
            }
            if (findPiece == null) {
                findPiece = new Piece();
                findPiece.setBeginPoint(new Point(x, i));
            }
            channel.add(findPiece);
        }
        return channel;
    }

    private List<Piece> getDownChannel(Piece piece, int ylimit, int step) {
        List<Piece> channel = new ArrayList<Piece>();
        int x = piece.getBeginX();
        for (int i = piece.getBeginY() + step; i <= ylimit; i += step) {
            Piece findPiece = findPiece(x, i);
            if (hasImage(x, i)) {
                return channel;
            }
            if (findPiece == null) {
                findPiece = new Piece();
                findPiece.setBeginPoint(new Point(x, i));
            }
            channel.add(findPiece);
        }
        return channel;
    }

    private List<Piece> getLeftChannel(Piece piece, int xlimit, int step) {
        List<Piece> channel = new ArrayList<Piece>();
        int y = piece.getBeginY();
        for (int i = piece.getBeginX() - step; i >= xlimit; i -= step) {
            Piece findPiece = findPiece(i, y);
            if (hasImage(i, y)) {
                return channel;
            }
            if (findPiece == null) {
                findPiece = new Piece();
                findPiece.setBeginPoint(new Point(i, y));
            }
            channel.add(findPiece);
        }
        return channel;
    }

    private List<Piece> getRightChannel(Piece piece, int xlimit, int step) {
        List<Piece> channel = new ArrayList<Piece>();
        int y = piece.getBeginY();
        for (int i = piece.getBeginX() + step; i <= xlimit; i += step) {
            Piece findPiece = findPiece(i, y);
            if (hasImage(i, y)) {
                return channel;
            }
            if (findPiece == null) {
                findPiece = new Piece();
                findPiece.setBeginPoint(new Point(i, y));
            }
            channel.add(findPiece);
        }
        return channel;
    }

    public boolean empty() {
        for (Piece[] piece : this.pieces) {
            for (Piece piece1 : piece) {
                if (piece1 != null
                        && piece1.getImage() != null)
                    return false;
            }
        }
        return true;
    }

    private boolean hasImage(int x, int y) {
        Piece piece = findPiece(x, y);
        if (piece == null) {
            return false;
        }

        return piece.getImage() != null;
    }

    private int getXIndex(int relativeX, int pieceWidth) {
        if (pieceWidth < 0)
            return -1;
        return relativeX / pieceWidth;
    }

    private int getYIndex(int relativeY, int pieceHeight) {
        if (pieceHeight < 0)
            return -1;
        return relativeY / pieceHeight;
    }

    public Piece findPiece(int x, int y) {
        // 避免错误
        if (this.gameModel == null) {
            return null;
        }
        int relativeX = x - this.config.getBeginPieceX();
        int relativeY = y - this.config.getBeginPieceY();
        if (relativeX < 0 || relativeY < 0) {
            return null;
        }
        int xIndex = getXIndex(relativeX, gameModel.getCommonImageWidth());
        int yIndex = getYIndex(relativeY, gameModel.getCommonImageHeight());
        if (xIndex < 0 || yIndex < 0) {
            return null;
        }
        if (xIndex > config.getRowNums() - 1
                || yIndex > config.getColumnNums() - 1) {
            return null;
        }
        return this.pieces[xIndex][yIndex];
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public long countGrade() {
        this.grade += this.config.getPerGrade();
        return this.grade;
    }

}
