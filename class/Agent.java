import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


public class Agent {

	static Output2 outputobject2=new Output2();
	static Output3 outputobject3=new Output3();
	public static void main(String[] args) throws IOException {
		Input inp=read("input.txt");
		switch (inp.getAlgorithm())
		{
		case 1: State s=greedyGamePlay(inp);
		write(s.getBoard());
		break;

		case 2: 
			
			Output2 o=minimaxAlgorithm(inp);
			write(o.getLog(),o.getQueue().getBoard(),2);
			outputobject2=null;

			break;

		case 3: Output3 o1=alphaBetaPruningAlgorithm(inp);
			write(o1.getLog(),o1.getQueue().getBoard(),3);
			outputobject3=null;
			break;

		case 4:
			String o4=competition(inp);
			BufferedWriter w = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(new File("output.txt"))));
			w.write(o4);
			w.close();
			break;

		}
	}
	private static State greedyGamePlay(Input inp) {
		State s=getStateFromInput(inp);
		s.getPositions();
		PriorityQueue<State> q =new PriorityQueue<State>(10, new Comparator<State>() {

			public int compare(State o1, State o2) {
				if(o1.getEvaluation()<o2.getEvaluation())
				{
					return 1;
				}
				else if(o1.getEvaluation()>o2.getEvaluation())
				{
					return -1;
				}
				else
				{
					if(o1.getNewPosition().getI() < o2.getNewPosition().getI())
					{
						return -1;
					}
					else if (o1.getNewPosition().getI()>o2.getNewPosition().getI())
					{
						return 1;
					}
					else
					{
						if(o1.getNewPosition().getJ()<o2.getNewPosition().getJ())
						{
							return -1;
						}
						else if(o1.getNewPosition().getJ()>o2.getNewPosition().getJ())
						{
							return 1;
						}
						else return 0;
					}
				}
			}
		});
		List<State> validMoves=getValidMoves(s,inp.getPlayerColor(), inp.getPlayerColor());
		for(int k=0;k<validMoves.size();k++)
		{
			q.add(validMoves.get(k));	
		}
		return q.peek();
	}
	private static Output2 minimaxAlgorithm(Input inp)
	{
		State s=getStateFromInput(inp);
		s.getPositions();
		minimax(s, inp.getCutOffDepth(), inp.getCutOffDepth(), true, inp.getPlayerColor());
		return outputobject2;
	}
	private static int minimax(State s, int depth, int cutOffDepth, boolean b, int playerColor) {
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
		int bestValue;
		int val;
		int pct=0;

		if(pc==1)
		{
			pct=2;
		}
		else 
		{
			pct=1;
		}
		ArrayList <State> childNodes=getValidMoves(s,pc, playerColor);
		ArrayList <State> tempNodes=getValidMoves(s,pct, playerColor);

		if(cutOffDepth==0||(childNodes.size()<=0 && tempNodes.size()<=0))
		{
			String colname="";
			if(cutOffDepth==depth)
			{
				colname="root";
				ResultState rs=new ResultState();
				rs.setBoard(s.getBoard());
				rs.setMaxvalue(0);
				outputobject2.setQueue(rs);
			}
			else
			{
				Position p=s.getNewPosition();
				String rowname="abcdefgh";
				colname=String.valueOf(rowname.charAt(p.getJ()))+(p.getI()+1);
			}
			Log l=new Log();
			l.setDepth(depth-cutOffDepth);
			l.setNodeName(colname);
			l.setValue(s.getEvaluation());
			outputobject2.setLog(l);
			return s.getEvaluation();
		}

		PriorityQueue<State> q =new PriorityQueue<State>(10, new Comparator<State>() {

			public int compare(State o1, State o2) {
				//				if(o1.getEvaluation()<o2.getEvaluation())
				//				{
				//					return 1;
				//				}
				//				else if(o1.getEvaluation()>o2.getEvaluation())
				//				{
				//					return -1;
				//				}
				//				else
				//				{
				if(o1.getNewPosition().getI() < o2.getNewPosition().getI())
				{
					return -1;
				}
				else if (o1.getNewPosition().getI()>o2.getNewPosition().getI())
				{
					return 1;
				}
				else
				{
					if(o1.getNewPosition().getJ()<o2.getNewPosition().getJ())
					{
						return -1;
					}
					else if(o1.getNewPosition().getJ()>o2.getNewPosition().getJ())
					{
						return 1;
					}
					else return 0;
				}
			}
			//			}
		});

		for(State sq : childNodes)
		{
			q.add(sq);
		}

		if(b)
		{
			if(childNodes.size()<=0)
			{
				bestValue=-1000;
				if(cutOffDepth!=depth)
				{
					String colname="pass";
					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName(colname);
					l.setValue(bestValue);
					outputobject2.setLog(l);
				}
				else
				{
					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName("root");
					l.setValue(bestValue);
					outputobject2.setLog(l);
				}
				State sq=s;
				sq.setBlackPosition(new ArrayList<Position>());
				sq.setWhitePosition(new ArrayList<Position>());
				sq.getPositions();
				sq.setNewPosition(new Position(10, 10));
				val=minimax(sq,depth, cutOffDepth-1, false , playerColor);
				bestValue=Math.max(bestValue,val);
				if(cutOffDepth!=depth)
				{
					String colname="pass";
					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName(colname);
					l.setValue(bestValue);
					outputobject2.setLog(l);
				}
				else
				{
					ResultState rs=new ResultState();
					rs.setMaxvalue(bestValue);
					rs.setBoard(sq.getBoard());
					outputobject2.setQueue(rs);
					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName("root");
					l.setValue(bestValue);
					outputobject2.setLog(l);
				}
				return bestValue;
			}
			else
			{
				bestValue=-1000;
				if(cutOffDepth!=depth)
				{
					Position p=s.getNewPosition();
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
					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName(colname);
					l.setValue(bestValue);
					outputobject2.setLog(l);
				}
				else
				{

					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName("root");
					l.setValue(bestValue);
					outputobject2.setLog(l);
				}
				while (q.size()>0)
				{
					State sq=q.remove();
					sq.setBlackPosition(new ArrayList<Position>());
					sq.setWhitePosition(new ArrayList<Position>());
					sq.getPositions();
					Position p=s.getNewPosition();

					val=minimax(sq,depth, cutOffDepth-1, false , playerColor);

					bestValue=Math.max(bestValue,val);
					if(cutOffDepth!=depth)
					{
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
						Log l=new Log();
						l.setDepth(depth-cutOffDepth);
						l.setNodeName(colname);
						l.setValue(bestValue);
						outputobject2.setLog(l);
					}
					else
					{
						ResultState rs=new ResultState();
						rs.setMaxvalue(bestValue);
						rs.setBoard(sq.getBoard());
						outputobject2.setQueue(rs);
						Log l=new Log();
						l.setDepth(depth-cutOffDepth);
						l.setNodeName("root");
						l.setValue(bestValue);
						outputobject2.setLog(l);
					}
				}
				return bestValue;
			}
		}
		else
		{
			if(childNodes.size()<=0)
			{
				bestValue=1000;
				Position p=s.getNewPosition();
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
				Log l=new Log();
				l.setDepth(depth-cutOffDepth);
				l.setNodeName(colname);
				l.setValue(bestValue);
				outputobject2.setLog(l);

				State sq=s;
				sq.setNewPosition(new Position(10, 10));
				sq.setBlackPosition(new ArrayList<Position>());
				sq.setWhitePosition(new ArrayList<Position>());
				sq.getPositions();
				sq.setNewPosition(new Position(10, 10));
				val=minimax(sq,depth, cutOffDepth-1,true, playerColor);
				bestValue=Math.min(bestValue, val);
				l=new Log();
				l.setDepth(depth-cutOffDepth);
				l.setNodeName(colname);
				l.setValue(bestValue);
				outputobject2.setLog(l);
				return bestValue;
			}
			else
			{
				bestValue=1000;
				Position p=s.getNewPosition();
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
				Log l=new Log();
				l.setDepth(depth-cutOffDepth);
				l.setNodeName(colname);
				l.setValue(bestValue);
				outputobject2.setLog(l);
				while(q.size()>0)
				{
					State sq=q.remove();
					sq.setBlackPosition(new ArrayList<Position>());
					sq.setWhitePosition(new ArrayList<Position>());
					sq.getPositions();
					val=minimax(sq,depth, cutOffDepth-1,true, playerColor);

					bestValue=Math.min(bestValue, val);
					l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName(colname);
					l.setValue(bestValue);
					outputobject2.setLog(l);
				}
				return bestValue;
			}
		}
	}
	private static Output3 alphaBetaPruningAlgorithm(Input inp) {
		State s=getStateFromInput(inp);
		s.getPositions();
		int a=-1000;
		int b=1000;
		abpruning(s, inp.getCutOffDepth(), inp.getCutOffDepth(), true, inp.getPlayerColor(), a , b);
		ArrayList <State> childNodes=getValidMoves(s,inp.getPlayerColor(), inp.getPlayerColor());
		PriorityQueue<State> q =new PriorityQueue<State>(30, new Comparator<State>() {

			public int compare(State o1, State o2) {

				if(o1.getNewPosition().getI() < o2.getNewPosition().getI())
				{
					return -1;
				}
				else if (o1.getNewPosition().getI()>o2.getNewPosition().getI())
				{
					return 1;
				}
				else
				{
					if(o1.getNewPosition().getJ()<o2.getNewPosition().getJ())
					{
						return -1;
					}
					else if(o1.getNewPosition().getJ()>o2.getNewPosition().getJ())
					{
						return 1;
					}
					else return 0;
				}
			}
			//			}
		});
		for(State sq : childNodes)
		{
			q.add(sq);
		}
		return outputobject3;
	}
	private static int abpruning(State s, int depth, int cutOffDepth,
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
		ArrayList <State> childNodes=getValidMoves(s,pc, playerColor);
		ArrayList <State> tempNodes=getValidMoves(s,pct, playerColor);

		if(cutOffDepth==0||(childNodes.size()<=0 && tempNodes.size()<=0))
		{
			String colname="";
			if(cutOffDepth==depth)
			{
				colname="root";
				ResultState rs=new ResultState();
				rs.setBoard(s.getBoard());
				rs.setMaxvalue(0);
				outputobject3.setQueue(rs);
			}
			else
			{
				Position p=s.getNewPosition();
				colname="";
				if(p.getI()==10 && p.getJ()==10)
				{

					colname="pass";
				}
				else
				{
					String rowname="abcdefgh";
					colname=String.valueOf(rowname.charAt(p.getJ()))+(p.getI()+1);
				}
			}
			Log l=new Log();
			l.setDepth(depth-cutOffDepth);
			l.setNodeName(colname);
			l.setValue(s.getEvaluation());
			l.setAlpha(alpha);
			l.setBeta(beta);
			outputobject3.setLog(l);
			return s.getEvaluation();
		}


		//For getting childs in order
		PriorityQueue<State> q =new PriorityQueue<State>(10, new Comparator<State>() {

			public int compare(State o1, State o2) {

				if(o1.getNewPosition().getI() < o2.getNewPosition().getI())
				{
					return -1;
				}
				else if (o1.getNewPosition().getI()>o2.getNewPosition().getI())
				{
					return 1;
				}
				else
				{
					if(o1.getNewPosition().getJ()<o2.getNewPosition().getJ())
					{
						return -1;
					}
					else if(o1.getNewPosition().getJ()>o2.getNewPosition().getJ())
					{
						return 1;
					}
					else return 0;
				}
			}

		});

		for(State sq : childNodes)
		{
			q.add(sq);

		}


		if(b)
		{
			value=-1000;
			if(childNodes.size()<=0)
			{

				if(cutOffDepth!=depth)
				{
					String colname="pass";
					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName(colname);
					l.setValue(value);
					l.setAlpha(alpha);
					l.setBeta(beta);
					outputobject3.setLog(l);
				}
				else
				{
					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName("root");
					l.setValue(value);
					l.setAlpha(alpha);
					l.setBeta(beta);
					outputobject3.setLog(l);
				}


				State sq=s;
				sq.setNewPosition(new Position(10, 10));
				sq.setBlackPosition(new ArrayList<Position>());
				sq.setWhitePosition(new ArrayList<Position>());
				sq.getPositions();
				value=Math.max(value, abpruning(sq, depth, cutOffDepth-1, false, playerColor, alpha, beta));




				if(value>=beta)
				{
					if(cutOffDepth!=depth)
					{
						Position p=s.getNewPosition();
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
						Log l=new Log();
						l.setDepth(depth-cutOffDepth);
						l.setNodeName(colname);
						l.setValue(value);
						l.setAlpha(alpha);
						l.setBeta(beta);
						outputobject3.setLog(l);
					}
					else
					{
						ResultState rs=new ResultState();
						rs.setMaxvalue(alpha);
						rs.setBoard(sq.getBoard());
						outputobject3.setQueue(rs);
						Log l=new Log();
						l.setDepth(depth-cutOffDepth);
						l.setNodeName("root");
						l.setValue(value);
						l.setAlpha(alpha);
						l.setBeta(beta);
						outputobject3.setLog(l);
					}


					return value;
				}
				alpha=Math.max(alpha,value);
				if(cutOffDepth!=depth)
				{
					Position p=s.getNewPosition();
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
					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName(colname);
					l.setValue(value);
					l.setAlpha(alpha);
					l.setBeta(beta);
					outputobject3.setLog(l);
				}
				else
				{
					ResultState rs=new ResultState();
					rs.setMaxvalue(alpha);
					rs.setBoard(sq.getBoard());
					outputobject3.setQueue(rs);
					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName("root");
					l.setValue(value);
					l.setAlpha(alpha);
					l.setBeta(beta);
					outputobject3.setLog(l);
				}

				return value;
			}
			else
			{
				if(cutOffDepth!=depth)
				{
					Position p=s.getNewPosition();
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

					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName(colname);
					l.setValue(value);
					l.setAlpha(alpha);
					l.setBeta(beta);
					outputobject3.setLog(l);
				}
				else
				{
					Log l=new Log();
					l.setDepth(depth-cutOffDepth);
					l.setNodeName("root");
					l.setValue(value);
					l.setAlpha(alpha);
					l.setBeta(beta);
					outputobject3.setLog(l);
				}

				while(q.size()>0)
				{
					State sq=q.remove();
					sq.setBlackPosition(new ArrayList<Position>());
					sq.setWhitePosition(new ArrayList<Position>());

					sq.getPositions();
					value=Math.max(value, abpruning(sq, depth, cutOffDepth-1, false, playerColor, alpha, beta));


					if(value>=beta) 
					{

						if(cutOffDepth!=depth)
						{
							Position p=s.getNewPosition();
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
							Log l=new Log();
							l.setDepth(depth-cutOffDepth);
							l.setNodeName(colname);
							l.setValue(value);
							l.setAlpha(alpha);
							l.setBeta(beta);
							outputobject3.setLog(l);
						}
						else
						{
							ResultState rs=new ResultState();
							rs.setMaxvalue(alpha);
							rs.setBoard(sq.getBoard());
							outputobject3.setQueue(rs);


							Log l=new Log();
							l.setDepth(depth-cutOffDepth);
							l.setNodeName("root");
							l.setValue(value);
							l.setAlpha(alpha);
							l.setBeta(beta);
							outputobject3.setLog(l);
						}



						return value;
					}
					alpha=Math.max(value,alpha);

					if(cutOffDepth!=depth)
					{
						Position p=s.getNewPosition();
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
						Log l=new Log();
						l.setDepth(depth-cutOffDepth);
						l.setNodeName(colname);
						l.setValue(value);
						l.setAlpha(alpha);
						l.setBeta(beta);
						outputobject3.setLog(l);
					}
					else
					{
						ResultState rs=new ResultState();
						rs.setMaxvalue(alpha);
						rs.setBoard(sq.getBoard());
						outputobject3.setQueue(rs);


						Log l=new Log();
						l.setDepth(depth-cutOffDepth);
						l.setNodeName("root");
						l.setValue(value);
						l.setAlpha(alpha);
						l.setBeta(beta);
						outputobject3.setLog(l);
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
				Position p=s.getNewPosition();
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

				Log l=new Log();
				l.setDepth(depth-cutOffDepth);
				l.setNodeName(colname);
				l.setValue(value);
				l.setAlpha(alpha);
				l.setBeta(beta);
				outputobject3.setLog(l);

				State sq=s;
				sq.setNewPosition(new Position(10,10));
				sq.setBlackPosition(new ArrayList<Position>());
				sq.setWhitePosition(new ArrayList<Position>());
				sq.getPositions();
				value=Math.min(value, abpruning(sq, depth, cutOffDepth-1, true, playerColor, alpha, beta));


				if(value<=alpha) 
				{
					Log l1=new Log();
					l1.setDepth(depth-cutOffDepth);
					l1.setNodeName(colname);
					l1.setValue(value);
					l1.setAlpha(alpha);
					l1.setBeta(beta);
					outputobject3.setLog(l1);
					return value;
				}
				beta=Math.min(value, beta);
				Log l1=new Log();
				l1.setDepth(depth-cutOffDepth);
				l1.setNodeName(colname);
				l1.setValue(value);
				l1.setAlpha(alpha);
				l1.setBeta(beta);
				outputobject3.setLog(l1);

				return value;
			}
			else
			{

				Position p=s.getNewPosition();
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

				Log l=new Log();
				l.setDepth(depth-cutOffDepth);
				l.setNodeName(colname);
				l.setValue(value);
				l.setAlpha(alpha);
				l.setBeta(beta);
				outputobject3.setLog(l); 

				//				int[][] ar1=s.getBoard();
				//				for(int i=0;i<8;i++)
				//				{
				//					for(int j=0;j<8;j++)
				//					{
				//						System.out.print(ar1[i][j]+" ");
				//					}
				//					System.out.println();
				//				}
				//				System.out.println(s.getNewPosition().getI());
				//				System.out.println(s.getNewPosition().getJ());
				//				System.out.println(s.getBlackPosition().size());
				//				System.out.println(s.getWhitePosition().size());
				//				System.out.println(s.getEvaluation());
				//				System.out.println();
				while(q.size()>0)
				{

					State sq=q.remove();

					sq.setBlackPosition(new ArrayList<Position>());
					sq.setWhitePosition(new ArrayList<Position>());
					sq.getPositions();

					value=Math.min(value, abpruning(sq, depth, cutOffDepth-1, true, playerColor, alpha, beta));


					if(value<=alpha) 
					{
						Log l1=new Log();
						l1.setDepth(depth-cutOffDepth);
						l1.setNodeName(colname);
						l1.setValue(value);
						l1.setAlpha(alpha);
						l1.setBeta(beta);
						outputobject3.setLog(l1);
						return value;
					}
					beta=Math.min(beta,value);
					Log l1=new Log();
					l1.setDepth(depth-cutOffDepth);
					l1.setNodeName(colname);
					l1.setValue(value);
					l1.setAlpha(alpha);
					l1.setBeta(beta);
					outputobject3.setLog(l1);
				}
				return value;
			}


		}

	}
	private static ArrayList<State> getValidMoves(State s, int i, int player) {
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
		ArrayList<State> validMoves=new ArrayList<State>();
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

		for(int k=0;k<validPositions.size();k++)
		{
			Position pos=validPositions.get(k);
			int [][] newBoard=new int [8][8];
			for(int z = 0; z < 8; z++)
				newBoard[z] = s.getBoard()[z].clone();
			ArrayList<Position> blackPosition=s.getBlackPosition();
			ArrayList<Position> whitePosition=s.getWhitePosition();
			for(int t=0;t<pos.getParentPosition().size();t++)
			{
				ArrayList<Position> tempflip=findFlipPositions(pos.getI(),pos.getJ(),pos.getParentPosition().get(t),pos.getDirection().get(t));
				for (Position q:tempflip)
				{
					newBoard[q.getI()][q.getJ()]=1;
					blackPosition.add(q);
					for(int c=0;c<whitePosition.size();c++)
					{
						if(q.getI()==whitePosition.get(c).getI() && q.getJ()==whitePosition.get(c).getJ())
						{
							whitePosition.remove(c);
						}
					}
				}
			}
			newBoard[pos.getI()][pos.getJ()]=1;
			blackPosition.add(pos);		
			State temp=new State(newBoard, player);
			temp.setWhitePosition(whitePosition);
			temp.setWhitePosition(blackPosition);
			temp.setNewPosition(pos);
			validMoves.add(temp);
		}
		return validMoves;
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
		ArrayList<State> validMoves1=new ArrayList<State>();
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
			State temp=new State(newBoard, player);
			temp.setBlackPosition(blackPosition);
			temp.setWhitePosition(whitePosition);
			temp.setNewPosition(pos);
			validMoves1.add(temp);
		}
		return validMoves1;
		}
		return null;
	}
	private static ArrayList<Position> findFlipPositions(int i, int j, Position position,
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
	private static Position findblank(int i, int j, int[][] ks, String tile, int player) {
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
	private static State getStateFromInput(Input inp) {
		State s=new State(inp.getInitialBoard(),inp.getPlayerColor());
		return s;
	}
	public static Input read(String path) throws NumberFormatException, IOException
	{
		Input inp=new Input();
		File f=new File(path);
		BufferedReader br=new BufferedReader(new FileReader(f));
		int algorithm=Integer.parseInt(br.readLine());

		if(algorithm!=4)
		{
			int player=0;
			if(br.readLine().compareToIgnoreCase("X")==0)
			{
				player=1;	
			}
			else
			{
				player=2;
			}

			int depth=Integer.parseInt(br.readLine());
			int [][] curBoard=new int[8][8];
			for(int i=0;i<8;i++)
			{
				String str=br.readLine();
				for (int j=0;j<8;j++)
				{
					if(str.charAt(j)=='*')
					{
						curBoard[i][j]=0;
					}
					else if(str.charAt(j)=='X')
					{
						curBoard[i][j]=1;
					}
					else
					{
						curBoard[i][j]=2;
					}
				}
			}
			inp.setCutOffDepth(depth);
			inp.setPlayerColor(player);
			inp.setInitialBoard(curBoard);
			inp.setAlgorithm(algorithm);
			//		inp.print();
			br.close();}
		else if(algorithm==4)
		{
			int player=0;
			if(br.readLine().compareToIgnoreCase("X")==0)
			{
				player=1;	
			}
			else
			{
				player=2;
			}
			int timeLeft=Integer.parseInt(br.readLine());
			int [][] curBoard=new int[8][8];
			for(int i=0;i<8;i++)
			{
				String str=br.readLine();
				for (int j=0;j<8;j++)
				{
					if(str.charAt(j)=='*')
					{
						curBoard[i][j]=0;
					}
					else if(str.charAt(j)=='X')
					{
						curBoard[i][j]=1;
					}
					else
					{
						curBoard[i][j]=2;
					}
				}
			}
			inp.setTimeLeft(timeLeft);
			inp.setPlayerColor(player);
			inp.setInitialBoard(curBoard);
			inp.setAlgorithm(algorithm);
		}
		return inp;

	}
	private static String competition(Input inp) throws IOException {


		GameHistory gh=Game.deserGameHistory();
		OpeningBook ob=Game.getOpeningBook();
		int currentMoveNumber=gh.getNumberOfMovesInThisGame();
		GameState s=Game.getGameStateFromInput(inp);
		s.setBlackPosition(new ArrayList<Position>());
		s.setWhitePosition(new ArrayList<Position>());
		s.getPositions();
		ArrayList<Position> pos=Game.getValidMoves(s, inp.getPlayerColor(), inp.getPlayerColor());
		if(currentMoveNumber<16)
		{
			
			
			if(inp.getPlayerColor()==1)
			{
				PriorityQueue<Data> pq=ob.getBm().getList().get(currentMoveNumber);
				
				while(pq.size()>0)
				{
					Data d=pq.remove();
					
					if(pos.size()==0)
					{
						gh.setNumberOfMovesInThisGame(currentMoveNumber+1);
						Game.serGameHistory(gh);
						return "pass";
					}
					else
					{
					for(Position p: pos)
					{
						String rowname="abcdefgh";
						String validMove=String.valueOf(rowname.charAt(p.getJ()))+(p.getI()+1);
						if(validMove.compareToIgnoreCase(d.getNode())==0)
						{
							gh.setNumberOfMovesInThisGame(currentMoveNumber+1);
							Game.serGameHistory(gh);
							return validMove;
							
						}
					}}
					
				}
			}
			else if(inp.getPlayerColor()==2)
			{

				
				PriorityQueue<Data> pq=ob.getWm().getList().get(currentMoveNumber);
				
				while(pq.size()>0)
				{
					Data d=pq.remove();
					if(pos.size()==0)
					{
						gh.setNumberOfMovesInThisGame(currentMoveNumber+1);
						Game.serGameHistory(gh);
						return "pass";
					}
					for(Position p: pos)
					{
						String rowname="abcdefgh";
						String validMove=String.valueOf(rowname.charAt(p.getJ()))+(p.getI()+1);
						
						if(validMove.compareToIgnoreCase(d.getNode())==0)
						{
							gh.setNumberOfMovesInThisGame(currentMoveNumber+1);
							Game.serGameHistory(gh);
							return validMove;
						}
					}
					
				}

			}
		}
		else
		{
			s.setBlackPosition(new ArrayList<Position>());
			s.setWhitePosition(new ArrayList<Position>());
			s.getPositions();
			Output4 o4=Game.abpruningAlgorithm(s, 9, 9, true, inp.getPlayerColor(), -10000, 10000);
			gh.setNumberOfMovesInThisGame(currentMoveNumber+1);
			Game.serGameHistory(gh);
			return o4.getQueue().remove().getNewMove();
			
		}
		

		return "";
	}
	private static void write(ArrayList<Log> log, int[][] board, int k) throws IOException
	{

		BufferedWriter w = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(new File("output.txt"))));
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(board[i][j]==0)
					w.write("*");
				else if (board[i][j]==1)
				{
					w.write("X");
				}
				else
				{
					w.write("O");
				}
			}
			w.write("\n");
		}
		if(k==3)
		{
			w.write("Node,Depth,Value,Alpha,Beta\n");
			int count=1;
			for(Log l:log)
			{
				String alpha;
				String beta;
				String value;
				if(l.getAlpha()==1000)alpha="Infinity";
				else if(l.getAlpha()==-1000)alpha="-Infinity";
				else alpha=String.valueOf(l.getAlpha());
				if(l.getBeta()==1000)beta="Infinity";
				else if(l.getBeta()==-1000)beta="-Infinity";
				else beta=String.valueOf(l.getBeta());
				if(l.getValue()==1000)value="Infinity";
				else if(l.getValue()==-1000)value="-Infinity";
				else value=String.valueOf(l.getValue());
				w.write(l.getNodeName()+","+l.getDepth()+","+value+","+alpha+","+beta);
				count=count+1;
				if(count<=log.size())
					w.write("\n");
			}
		}
		else if(k==2)
		{
			int count=1;
			w.write("Node,Depth,Value\n");
			for(Log l:log)
			{
				String value="";
				if(l.getValue()==1000)value="Infinity";
				else if(l.getValue()==-1000)value="-Infinity";
				else value=String.valueOf(l.getValue());
				w.write(l.getNodeName()+","+ l.getDepth()+ ","+ value);
				count=count+1;
				if(count<=log.size())
					w.write("\n");
			}

		}
		w.close();

	}
	private static void write(int[][] board) throws IOException
	{
		BufferedWriter w = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(new File("output.txt"))));
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(board[i][j]==0)
					w.write("*");
				else if (board[i][j]==1)
				{
					w.write("X");
				}
				else
				{
					w.write("O");
				}
			}
			if(i!=7)
				w.write("\n");
		}
		w.close();

	}

}
