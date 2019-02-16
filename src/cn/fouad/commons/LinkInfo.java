package cn.fouad.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkInfo {

	private List<Piece> pieces = new ArrayList<Piece>();

	public LinkInfo(Piece... aPieces) {
		this.pieces.addAll(Arrays.asList(aPieces));
	}

	public List<Piece> getPieces() {
		return pieces;
	}

}
