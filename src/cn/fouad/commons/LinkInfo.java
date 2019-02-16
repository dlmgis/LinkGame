package cn.fouad.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 连接信息
 */
public class LinkInfo {

	private List<Piece> pieces = new ArrayList<>();

	/**
	 * @param aPieces 图片列表
	 */
	public LinkInfo(Piece... aPieces) {
		this.pieces.addAll(Arrays.asList(aPieces));
	}

	/**
	 * 获取游戏图片列表
	 * @return 游戏图片列表
	 */
	public List<Piece> getPieces() {
		return pieces;
	}

}
