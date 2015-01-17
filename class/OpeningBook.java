
public class OpeningBook {
	BlackMoves bm;
	WhiteMoves wm;
	public OpeningBook(BlackMoves bm, WhiteMoves wm) {
		this.bm=bm;
		this.wm=wm;
	}
	public BlackMoves getBm() {
		return bm;
	}
	public void setBm(BlackMoves bm) {
		this.bm = bm;
	}
	public WhiteMoves getWm() {
		return wm;
	}
	public void setWm(WhiteMoves wm) {
		this.wm = wm;
	}
	
}
