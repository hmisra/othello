import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
//TODO GameGameState setevaluation (player) -> getevaluation


public class Game {
	public static Output4 getOutputobject4() {
		return outputobject4;
	}
	public static void setOutputobject4(Output4 outputobject4) {
		Game.outputobject4 = outputobject4;
	}
	public static Output4 outputobject4=new Output4();
	public static Output4 abpruningAlgorithm(GameState s, int depth, int cutOffDepth, boolean b, int playerColor, int alpha, int beta)
	{
		abpruning(s, depth, cutOffDepth, b, playerColor, alpha, beta);
		return outputobject4;
	}
	public static int abpruning(GameState s, int depth, int cutOffDepth,
			boolean b, int playerColor, int alpha, int beta) {

		int pc=0;
		if(b)
		{
			pc=playerColor;
		}
		else
		{
			if(playerColor==1)
			{
				pc=2;
			}
			else
			{
				pc=1;
			}
		}
		int value;
		int pct=0;

		if(pc==1)
		{
			pct=2;
		}
		else 
		{
			pct=1;
		}
		ArrayList <GameState> childNodes=getValidGameStates(getValidMoves(s,pc, playerColor), s, playerColor);
		ArrayList <GameState> tempNodes=getValidGameStates(getValidMoves(s,pct, playerColor), s, playerColor);
		if(cutOffDepth==0||(childNodes.size()<=0 && tempNodes.size()<=0))
		{
			s.setEvaluation(playerColor);
			return s.getEvaluation();
		}

		if(b)
		{
			value=-1000;
			if(childNodes.size()<=0)
			{
				GameState sq=s;
				sq.setNewPosition(new Position(10, 10));
				sq.setBlackPosition(new ArrayList<Position>());
				sq.setWhitePosition(new ArrayList<Position>());
				sq.getPositions();
				value=Math.max(value, abpruning(sq, depth, cutOffDepth-1, false, playerColor, alpha, beta));
				if(value>=beta)
				{
					if(cutOffDepth==depth)
					{
						ResultState rs=new ResultState();
						rs.setMaxvalue(alpha);
						rs.setBoard(sq.getBoard());
						Position p=sq.getNewPosition();
						String colname="";
						if(p.getI()==10 && p.getJ()==10)
						{

							colname="pass";
						}
						else
						{
							String rowname="abcdefgh";
							colname=String.valueOf(rowname.charAt(p.getJ()))+(p.getI()+1);
						}rs.setNewMove(colname);
						outputobject4.setQueue(rs);
					}
					return value;
				}
				alpha=Math.max(alpha,value);
				
				if(cutOffDepth==depth)
				{
					ResultState rs=new ResultState();
					rs.setMaxvalue(alpha);
					rs.setBoard(sq.getBoard());
					Position p=sq.getNewPosition();
					String colname="";
					if(p.getI()==10 && p.getJ()==10)
					{

						colname="pass";
					}
					else
					{
						String rowname="abcdefgh";
						colname=String.valueOf(rowname.charAt(p.getJ()))+(p.getI()+1);
					}rs.setNewMove(colname);
					outputobject4.setQueue(rs);
					
				}
				return value;
			}
			else
			{
			
				while(childNodes.size()>0)
				{
					GameState sq=childNodes.remove(0);
					sq.setBlackPosition(new ArrayList<Position>());
					sq.setWhitePosition(new ArrayList<Position>());
					sq.getPositions();
					value=Math.max(value, abpruning(sq, depth, cutOffDepth-1, false, playerColor, alpha, beta));
					if(value>=beta) 
					{
						if(cutOffDepth==depth)
						{
							ResultState rs=new ResultState();
							rs.setMaxvalue(alpha);
							rs.setBoard(sq.getBoard());
							Position p=sq.getNewPosition();
							String colname="";
							if(p.getI()==10 && p.getJ()==10)
							{

								colname="pass";
							}
							else
							{
								String rowname="abcdefgh";
								colname=String.valueOf(rowname.charAt(p.getJ()))+(p.getI()+1);
							}rs.setNewMove(colname);
							outputobject4.setQueue(rs);
						}
						return value;
					}
					alpha=Math.max(value,alpha);

					if(cutOffDepth==depth)
				
					{
						ResultState rs=new ResultState();
						rs.setMaxvalue(alpha);
						rs.setBoard(sq.getBoard());
						Position p=sq.getNewPosition();
						String colname="";
						if(p.getI()==10 && p.getJ()==10)
						{

							colname="pass";
						}
						else
						{
							String rowname="abcdefgh";
							colname=String.valueOf(rowname.charAt(p.getJ()))+(p.getI()+1);
						}
						rs.setNewMove(colname);
						outputobject4.setQueue(rs);
					}
				}
				return value;
			}
		}
		else
		{
			value=1000;
			if(childNodes.size()<=0)
			{
				GameState sq=s;
				sq.setNewPosition(new Position(10,10));
				sq.setBlackPosition(new ArrayList<Position>());
				sq.setWhitePosition(new ArrayList<Position>());
				sq.getPositions();
				value=Math.min(value, abpruning(sq, depth, cutOffDepth-1, true, playerColor, alpha, beta));
				if(value<=alpha) 
				{
					return value;
				}
				beta=Math.min(value, beta);
				return value;
			}
			else
			{

				while(childNodes.size()>0)
				{
					GameState sq=childNodes.remove(0);
					
					sq.setBlackPosition(new ArrayList<Position>());
					sq.setWhitePosition(new ArrayList<Position>());
					sq.getPositions();
					value=Math.min(value, abpruning(sq, depth, cutOffDepth-1, true, playerColor, alpha, beta));
					if(value<=alpha) 
					{
						return value;
					}
					beta=Math.min(beta,value);
				}
				return value;
			}
		}
	}
	public static OpeningBook getOpeningBook(){
		BlackMoves bm = null;
		WhiteMoves wm=null;
		try
		{
			FileInputStream fileIn = new FileInputStream("whiteMoves.dat");
			FileInputStream fileIn1 = new FileInputStream("blackMoves.dat");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			wm = (WhiteMoves) in.readObject();
			in.close();
			fileIn.close();

			in=new ObjectInputStream(fileIn1);
			bm=(BlackMoves)in.readObject();
			in.close();
			fileIn1.close();
			
			

		}catch(IOException i)
		{
			i.printStackTrace();
		}catch(ClassNotFoundException c)
		{
			System.out.println("Class not found");
			c.printStackTrace();
		}
		return new OpeningBook(bm, wm);
	}
	public static ArrayList<Position> getValidMoves(GameState s, int i, int player) {
		switch(i)
		{
		case 1: ArrayList <Position> wp=s.getBlackPosition();
		ArrayList<Position> validPositions=new ArrayList<Position>();
		while(wp.size()>0)
		{
			Position x=wp.remove(0);
			int p=x.getI();
			int q=x.getJ();
			boolean a=false,b=false,c=false,d=false,e=false,f=false,g=false,h=false;
			if(p-1>=0 && q-1>=0 && s.getBoard()[p-1][q-1]==2)a=true;
			if(p<=7 && q-1>=0 && s.getBoard()[p][q-1]==2)b=true;
			if(p+1<=7 && q-1>=0 && s.getBoard()[p+1][q-1]==2) c=true;
			if(p-1>=0 && q>=0 && s.getBoard()[p-1][q]==2) d=true;
			if(p+1<=7 && q>=0 && s.getBoard()[p+1][q]==2) e=true;
			if(p-1>=0 && q+1<=7 && s.getBoard()[p-1][q+1]==2)f=true;
			if(p<=7 && q+1<=7 && s.getBoard()[p][q+1]==2)g=true;
			if(p+1<=7 && q+1<=7 && s.getBoard()[p+1][q+1]==2) h=true;
			if(a)
			{
				Position position=findblank(p-1,q-1,s.getBoard(),"a",1);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("a");
					validPositions.add(position);
				}
			}
			if(b)
			{
				Position position=findblank(p,q-1,s.getBoard(),"b",1);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("b");
					validPositions.add(position);
				}
			}
			if(c)
			{
				Position position=findblank(p+1,q-1,s.getBoard(),"c",1);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("c");
					validPositions.add(position);
				}
			}
			if(d)
			{
				Position position=findblank(p-1,q,s.getBoard(),"d",1);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("d");
					validPositions.add(position);
				}
			}
			if(e)
			{
				Position position=findblank(p+1,q,s.getBoard(),"e",1);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("e");
					validPositions.add(position);
				}
			}
			if(f)
			{
				Position position=findblank(p-1,q+1,s.getBoard(),"f",1);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("f");
					validPositions.add(position);
				}
			}
			if(g)
			{
				Position position=findblank(p,q+1,s.getBoard(),"g",1);
				if((position!=null))
				{position.setParentPosition(new Position(p, q));
				position.setDirection("g");
				validPositions.add(position);
				}
			}
			if(h)
			{
				Position position=findblank(p+1,q+1,s.getBoard(),"h",1);
				if((position!=null))
				{position.setParentPosition(new Position(p, q));
				position.setDirection("h");
				validPositions.add(position);
				}
			}
		}
		for(int k=0;k<validPositions.size();k++)
		{
			Position pos=validPositions.get(k);
			if(k+1<validPositions.size())
			{
				for(int l=k+1;l<validPositions.size();l++)
				{
					Position temp=validPositions.get(l);
					if(pos.getI()==temp.getI() && pos.getJ()==temp.getJ())
					{
						pos.setParentPosition(temp.getParentPosition().get(0));
						pos.setDirection(temp.getDirection().get(0));
						validPositions.remove(l);
					}
				}
			}
		}

		return validPositions;

		case 2: ArrayList <Position> wp1= s.getWhitePosition();
		ArrayList<Position> validPositions1=new ArrayList<Position>();
		while(wp1.size()>0)
		{
			Position x=wp1.remove(0);
			int p=x.getI();
			int q=x.getJ();
			boolean a=false,b=false,c=false,d=false,e=false,f=false,g=false,h=false;
			if(p-1>=0 && q-1>=0 && s.getBoard()[p-1][q-1]==1)a=true;
			if(p<=7 && q-1>=0 && s.getBoard()[p][q-1]==1)b=true;
			if(p+1<=7 && q-1>=0 && s.getBoard()[p+1][q-1]==1) c=true;
			if(p-1>=0 && q>=0 && s.getBoard()[p-1][q]==1) d=true;
			if(p+1<=7 && q>=0 && s.getBoard()[p+1][q]==1) e=true;
			if(p-1>=0 && q+1<=7 && s.getBoard()[p-1][q+1]==1)f=true;
			if(p<=7 && q+1<=7 && s.getBoard()[p][q+1]==1)g=true;
			if(p+1<=7 && q+1<=7 && s.getBoard()[p+1][q+1]==1) h=true;			
			if(a)
			{
				Position position=findblank(p-1,q-1,s.getBoard(),"a",2);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("a");
					validPositions1.add(position);
				}
			}
			if(b)
			{
				Position position=findblank(p,q-1,s.getBoard(),"b",2);

				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("b");
					validPositions1.add(position);
				}
			}
			if(c)
			{
				Position position=findblank(p+1,q-1,s.getBoard(),"c",2);
				if((position!=null))
				{

					position.setParentPosition(new Position(p, q));
					position.setDirection("c");
					validPositions1.add(position);
				}
			}
			if(d)
			{
				Position position=findblank(p-1,q,s.getBoard(),"d",2);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("d");
					validPositions1.add(position);
				}
			}
			if(e)
			{
				Position position=findblank(p+1,q,s.getBoard(),"e",2);

				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("e");
					validPositions1.add(position);
				}
			}
			if(f)
			{
				Position position=findblank(p-1,q+1,s.getBoard(),"f",2);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("f");
					validPositions1.add(position);
				}
			}
			if(g)
			{
				Position position=findblank(p,q+1,s.getBoard(),"g",2);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("g");
					validPositions1.add(position);
				}
			}
			if(h)
			{
				Position position=findblank(p+1,q+1,s.getBoard(),"h",2);
				if((position!=null))
				{
					position.setParentPosition(new Position(p, q));
					position.setDirection("h");
					validPositions1.add(position);
				}
			}
		}
		for(int k=0;k<validPositions1.size();k++)
		{
			Position pos=validPositions1.get(k);
			if(k+1<validPositions1.size())
			{
				for(int l=k+1;l<validPositions1.size();l++)
				{
					Position temp=validPositions1.get(l);
					if(pos.getI()==temp.getI() && pos.getJ()==temp.getJ())
					{
						pos.setParentPosition(temp.getParentPosition().get(0));
						pos.setDirection(temp.getDirection().get(0));
						validPositions1.remove(l);
					}
				}
			}
		}


		return validPositions1;
		}
		return null;
	}
	public static ArrayList<Position> findFlipPositions(int i, int j, Position position,
			String string) {
		ArrayList<Position> flipPositions=new ArrayList<Position>();
		if(string=="a")
		{
			int a=i+1;int b=j+1;
			while(a!=position.getI() && b!=position.getJ())
			{
				flipPositions.add(new Position(a, b));
				a=a+1;
				b=b+1;
			}
			return flipPositions;


		}
		if(string=="b")
		{
			int a=i;int b=j+1;
			while(b!=position.getJ())
			{
				flipPositions.add(new Position(a, b));
				b=b+1;
			}
			return flipPositions;
		}
		if(string=="c")
		{
			int a=i-1;int b=j+1;
			while(a!=position.getI() && b!=position.getJ())
			{
				flipPositions.add(new Position(a, b));
				a=a-1;
				b=b+1;
			}
			return flipPositions;
		}
		if(string=="d")
		{
			int a=i+1;int b=j;
			while(a!=position.getI() )
			{
				flipPositions.add(new Position(a, b));
				a=a+1;
			}
			return flipPositions;
		}
		if(string=="e")
		{
			int a=i-1;int b=j;
			while(a!=position.getI())
			{
				flipPositions.add(new Position(a, b));
				a=a-1;

			}
			return flipPositions;
		}
		if(string=="f")
		{
			int a=i+1;int b=j-1;
			while(a!=position.getI() && b!=position.getJ())
			{
				flipPositions.add(new Position(a, b));
				a=a+1;
				b=b-1;
			}
			return flipPositions;
		}
		if(string=="g")
		{
			int a=i;int b=j-1;
			while(b!=position.getJ())
			{
				flipPositions.add(new Position(a, b));

				b=b-1;
			}
			return flipPositions;
		}
		if(string=="h")
		{
			int a=i-1;int b=j-1;
			while(a!=position.getI() && b!=position.getJ())
			{
				flipPositions.add(new Position(a, b));
				a=a-1;
				b=b-1;
			}
			return flipPositions;
		}
		return null;
	}
	public static Position findblank(int i, int j, int[][] ks, String tile, int player) {
		if(tile=="a" && player==1)
		{
			if(i-1>=0 && j-1>=0 && ks[i-1][j-1]==0)
				return new Position(i-1,j-1);
			else if (i-1>=0 && j-1>=0 && ks[i-1][j-1]==2)
				return findblank(i-1,j-1,ks,tile,player);
			else return null;
		}
		else if(tile=="a" && player==2)
		{
			if(i-1>=0 && j-1>=0 && ks[i-1][j-1]==0)
				return new Position(i-1,j-1);
			else if (i-1>=0 && j-1>=0 && ks[i-1][j-1]==1)
				return findblank(i-1,j-1,ks,tile,player);
			else return null;
		}

		if(tile=="b" && player==1)
		{
			if(i<=7 && j-1>=0 && ks[i][j-1]==0)
			{
				return new Position(i,j-1);
			}
			else if(i<=7 && j-1>=0 && ks[i][j-1]==2)
			{
				return findblank(i,j-1,ks,tile,player);
			}
			else return null;
		}
		else if(tile=="b" && player==2)
		{
			if(i>=0 && j-1>=0 && ks[i][j-1]==0)
			{
				return new Position(i,j-1);
			}
			else if(i>=0 && j-1>=0 && ks[i][j-1]==1)
			{
				return findblank(i,j-1,ks,tile,player);
			}
			else return null;
		}

		if(tile=="c" && player==1)
		{

			if(i+1<=7 && j-1>=0 && ks[i+1][j-1]==0)
			{
				return new Position(i+1,j-1);
			}
			else if (i+1<=7 && j-1>=0 && ks[i+1][j-1]==2)
			{
				return findblank(i+1,j-1,ks,tile,player);
			}
			else return null;

		}

		else if(tile=="c" && player==2)
		{

			if(i+1<=7 && j-1>=0 && ks[i+1][j-1]==0)
			{
				return new Position(i+1,j-1);
			}
			else if (i+1<=7 && j-1>=0 && ks[i+1][j-1]==1)
			{
				return findblank(i+1,j-1,ks,tile,player);
			}
			else return null;

		}

		if(tile=="d" && player==1)
		{

			if(i-1>=0 && j>=0 && ks[i-1][j]==0)
			{
				return new Position(i-1,j);
			}
			else if (i-1>=0 && j>=0 && ks[i-1][j]==2)
			{
				return findblank(i-1,j,ks,tile,player);
			}
			else return null;
		}
		if(tile=="d" && player==2)
		{

			if(i-1>=0 && j>=0 && ks[i-1][j]==0)
			{
				return new Position(i-1,j);
			}
			else if (i-1>=0 && j>=0 && ks[i-1][j]==1)
			{
				return findblank(i-1,j,ks,tile,player);
			}
			else return null;
		}

		if(tile=="e" && player==1)
		{
			if(i+1<=7 && j>=0 && ks[i+1][j]==0)
			{
				return new Position(i+1,j);
			}
			else if (i+1<=7 && j>=0 && ks[i+1][j]==2)
			{
				return findblank(i+1,j,ks,tile,player);
			}
			else return null;
		}
		else if(tile=="e" && player==2)
		{
			if(i+1<=7 && j>=0 && ks[i+1][j]==0)
			{
				return new Position(i+1,j);
			}
			else if (i+1<=7 && j>=0 && ks[i+1][j]==1)
			{
				return findblank(i+1,j,ks,tile,player);
			}
			else return null;
		}

		if(tile=="f" && player==1)
		{
			if(i-1>=0 && j+1<=7 && ks[i-1][j+1]==0)
			{
				return new Position(i-1,j+1);
			}
			else if(i-1>=0 && j+1<=7 && ks[i-1][j+1]==2)
			{
				return findblank(i-1,j+1,ks,tile,player);
			}
			else return null;
		}
		else if(tile=="f" && player==2)
		{
			if(i-1>=0 && j+1<=7 && ks[i-1][j+1]==0)
			{
				return new Position(i-1,j+1);
			}
			else if(i-1>=0 && j+1<=7 && ks[i-1][j+1]==1)
			{
				return findblank(i-1,j+1,ks,tile,player);
			}
			else return null;
		}

		if(tile=="g" && player==1)
		{
			if(i<=7 && j+1<=7 && ks[i][j+1]==0)
			{
				return new Position(i,j+1);
			}
			else if(i<=7 && j+1<=7 && ks[i][j+1]==2)
			{
				return findblank(i,j+1,ks,tile,player);
			}
			else return null;
		}
		else if(tile=="g" && player==2)
		{
			if(i>=0 && j+1<=7 && ks[i][j+1]==0)
			{
				return new Position(i,j+1);
			}
			else if(i>=0 && j+1<=7 && ks[i][j+1]==1)
			{
				return findblank(i,j+1,ks,tile,player);
			}
			else return null;
		}

		if(tile=="h" && player==1)
		{
			if(i+1<=7 && j+1<=7 && ks[i+1][j+1]==0)
			{
				return new Position(i+1,j+1);
			}
			else if (i+1<=7 && j+1<=7 && ks[i+1][j+1]==2)
			{
				return findblank(i+1,j+1,ks,tile,player);
			}
			else return null;
		}
		else if(tile=="h" && player==2)
		{
			if(i+1<=7 && j+1<=7 && ks[i+1][j+1]==0)
			{
				return new Position(i+1,j+1);
			}
			else if (i+1<=7 && j+1<=7 && ks[i+1][j+1]==1)
			{
				return findblank(i+1,j+1,ks,tile,player);
			}
			else return null;
		}
		return null;
	}
	public static ArrayList<GameState> getValidGameStates(ArrayList<Position> validPositions1, GameState s, int player)
	{
		ArrayList<GameState> validMoves1=new ArrayList<GameState>();
		for(int k=0;k<validPositions1.size();k++)
		{

			Position pos=validPositions1.get(k);
			int [][] newBoard=new int[8][8];
			for(int z = 0; z < 8; z++)
				newBoard[z] = s.getBoard()[z].clone();
			ArrayList<Position> whitePosition=s.getWhitePosition();
			ArrayList<Position> blackPosition=s.getBlackPosition();
			for(int t=0;t<pos.getParentPosition().size();t++)
			{
				ArrayList<Position> tempflip=findFlipPositions(pos.getI(),pos.getJ(),pos.getParentPosition().get(t),pos.getDirection().get(t));
				for (Position q:tempflip)
				{
					newBoard[q.getI()][q.getJ()]=2;
					whitePosition.add(q);
					for(int c=0;c<blackPosition.size();c++)
					{
						if(q.getI()==blackPosition.get(c).getI() && q.getJ()==blackPosition.get(c).getJ())
						{
							blackPosition.remove(c);
						}
					}
				}
			}
			newBoard[pos.getI()][pos.getJ()]=2;
			whitePosition.add(pos);		
			GameState temp=new GameState(newBoard, player);
			temp.setBlackPosition(blackPosition);
			temp.setWhitePosition(whitePosition);
			temp.setNewPosition(pos);
			validMoves1.add(temp);
		}
		return validMoves1;
	}
	public static void serGameHistory(GameHistory gh) throws IOException
	{
		ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream("GameHistory.dat")); 
		out1.writeObject(gh);
		out1.close();
	}
	public static GameHistory deserGameHistory() {
		GameHistory gh=null;
		try
		{
			FileInputStream fileIn = new FileInputStream("GameHistory.dat");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			gh = (GameHistory) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i)
		{
			i.printStackTrace();
		}catch(ClassNotFoundException c)
		{
			System.out.println("Class not found");
			c.printStackTrace();
		}
		return gh;
	}
	public static GameState getGameStateFromInput(Input inp) {
		GameState s=new GameState(inp.getInitialBoard(),inp.getPlayerColor());
		return s;
	}

}
