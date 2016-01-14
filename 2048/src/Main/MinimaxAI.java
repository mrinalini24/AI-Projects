package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinimaxAI implements Agent{
	
	static class State{
		GameBoard board;
		State[] children;
		boolean player;
		
		public State(GameBoard b, boolean p){
			board=b;
			player=p;
		}
		
		public void genChildren(){
			ArrayList<State> child=new ArrayList<>();
			if(player){
				List<Integer> moves=board.getPossibleMoves();
				for(int m:moves){
					GameBoard b=new GameBoard(board);
					b.makeMove(m);
					child.add(new State(b, false));
				}
			}else{
				board.getSpareSpace();
				for(int i=0;i<board.xl.size();i++){
					int x=board.xl.get(i);
					int y=board.yl.get(i);
					GameBoard b=new GameBoard(board);
					b.putTile(x, y, 2);
					child.add(new State(b, true));
				}
			}
			children=child.toArray(new State[child.size()]);
		}
		
		public int eval(){
			if(children==null){
				board.getSpareSpace();
				int freeSpace=board.xl.size();
				int max=MoreArrays.max(board.board);
				return freeSpace+max;
			}else if(children.length==0){
				return Integer.MIN_VALUE;
			}else{
				if(player){
					int max=Integer.MIN_VALUE;
					for(State s:children){
						int v=s.eval();
						if(v>max){
							max=v;
						}
					}
					return max;
				}else{
					int min=Integer.MAX_VALUE;
					for(State s:children){
						int v=s.eval();
						if(v<min){
							min=v;
						}
					}
					return min;
				}
			}
		}
		
		public void genDepth(int depth){
			if(depth==0)return;
			genChildren();
			for(State s:children){
				s.genDepth(depth-1);
			}
		}
		
		
	}

	Random rand=new Random();
	
	int roundPlayed=0;
	int searchDepth=100;
	int simulation=1024;
	
	@Override
	public int makeMove(GameBoard b) {
		List<Integer> moves=b.getPossibleMoves();
		int best=-1;
		int bestScore=-1;
		for(int i:moves){
			int s=evalMove(b, i);
			if(s>bestScore){
				bestScore=s;
				best=i;
			}
		}
		roundPlayed++;
		searchDepth=roundPlayed/10+100;
		simulation=roundPlayed/16+1024;
		return best;
	}

	private int evalMove(GameBoard b, int move) {
		GameBoard board=new GameBoard(b);
		board.makeMove(move);
		State s=new State(board, false);
		s.genDepth(3);
		return s.eval();
	}

}
