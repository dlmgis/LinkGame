package cn.fouad.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import cn.fouad.commons.LinkInfo;
import cn.fouad.commons.Piece;
import cn.fouad.service.impl.GameServiceImpl;
import cn.fouad.timer.GameTimer;
import cn.fouad.utils.ImageUtils;

/**
 * 游戏面板
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GameServiceImpl gameService;

	private Piece selectPiece;
	private LinkInfo linkInfo;
	private BufferedImage gameOverImage;

	public void setLinkInfo(LinkInfo linkInfo) {
		this.linkInfo = linkInfo;
	}

	GamePanel(GameServiceImpl gameService) {
		this.setBackground(new Color(55, 77, 118));
		this.setBorder(new EtchedBorder());
		this.gameService = gameService;
	}

	public void setOverImage(BufferedImage gameOverImage) {
		this.gameOverImage = gameOverImage;
	}

	public BufferedImage getOverImage() {
		return gameOverImage;
	}

	public void setSelectPiece(Piece piece) {
		this.selectPiece = piece;
	}

	public void paint(Graphics g) {
		try {
			g.drawImage(ImageUtils.getBackgroundImageIcon().getImage(), 0, 0,
					getWidth(), getHeight(), null);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		Piece[][] pieces = gameService.getPieces();
		if (pieces != null) {
			for (Piece[] piece : pieces) {
				for (Piece piece1 : piece) {
					if (piece1 != null && piece1.getImage() != null) {
						// System.out.println(pieces[i][j].getBeginX());
						g.drawImage(piece1.getImage(),
								piece1.getBeginX(),
								piece1.getBeginY(), null);
					}
				}
			}
		}
		if (this.selectPiece != null) {
			try {
				g.drawImage(ImageUtils.getImage("images/selected.gif"),
						this.selectPiece.getBeginX(),
						this.selectPiece.getBeginY(), null);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (this.linkInfo != null) {
			drawLine(this.linkInfo, g);
			this.linkInfo = null;
		}
		if (this.gameOverImage != null) {
			g.drawImage(this.gameOverImage, 0, 0, null);
		}
	}

	private void drawLine(LinkInfo linkInfo, Graphics g) {
		List<Piece> pieces = linkInfo.getPieces();
		int xStep = this.gameService.getGameModel().getCommonImageWidth();
		int yStep = this.gameService.getGameModel().getCommonImageHeight();
		for (int i = 0; i < pieces.size() - 1; i++) {
			Piece currentPiece = pieces.get(i);
			Piece nextPiece = pieces.get(i + 1);
			Graphics2D dg = ((Graphics2D) g);
			dg.setStroke(new BasicStroke(3.0F));
			dg.setColor(Color.RED);
			dg.drawLine(currentPiece.getBeginX() + xStep / 2,
					currentPiece.getBeginY() + yStep / 2, nextPiece.getBeginX()
							+ xStep / 2, nextPiece.getBeginY() + yStep / 2);
		}
		GameTimer.setGameTime(GameTimer.getGameTime() + 5);
	}

}
