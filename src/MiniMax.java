import java.util.ArrayList;

public abstract class MiniMax {
	public Type type;
	public ArrayList<MiniMax> children = new ArrayList<MiniMax>(9);
	public int parentMove;
	public int score = 0;
	public int depthMax, depth;
	
	public int getBestMove() {
		if (depth < depthMax) {
			createChildren();
			
			if (children.size() > 0) {
				int index = 0;
				for (int i = 0; i < children.size(); i++) {
					if (type == Type.MAX && children.get(i).getBestMove() > children.get(index).getBestMove()) {
						index = i;
					}
					else if (type == Type.MIN && children.get(i).getBestMove() < children.get(index).getBestMove()) {
						index = i;
					}
				}
				
				if (depth == 1) return(children.get(index).parentMove);
				else if (depth < depthMax) return(children.get(index).score);
				return(children.get(index).parentMove);
			}
			else {
				return score;
			}
		}
		else {
			return score;
		}
		
	}
	
	public abstract void createChildren();

	public abstract void evaluateScore();
}
