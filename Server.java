package g9_3;

import java.io.*;
import java.net.*;

public class Server{
    public static final int PORT = 8080;
    public static void main(String[] args)
	throws IOException{
	ServerSocket s = new ServerSocket(PORT);
	System.out.println("相手プレイヤーの接続を待っています...");
	try{
	    Socket socket = s.accept();
	    try{
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		System.out.println("接続されました。");
		Start.list();//モンスターの表示
		Start.select();//モンスターの選択
		Start.check(1);//選択モンスターの確認
		int[] Ssel = new int[3];
		int[] Csel = new int[3];
		Ssel = Start.getMonsters(1);
		for(int i=0; i<3; i++) Start.Csel[i] = Integer.parseInt(in.readLine());
		for(int i=0; i<3; i++) out.println(String.valueOf(Ssel[i]));
		Start.show1();
		Start.show2();
		System.out.println("");
		String str = in.readLine();
		if(str.equals("DONE")) out.println(str);
		Battle.begin();
		int Scom,Ccom;
		int opcur;
		int i=1;
		int flag;
		int randA;
		while(true){
		    System.out.println("------ターン"+i+"------");
		    Scom = Battle.command(1);
		    Ccom = Integer.parseInt(in.readLine());
		    out.println(String.valueOf(Scom));
		    randA = (int)Math.random()*100; //同じspd同士の攻撃順序決定に使用
		    out.println(String.valueOf(randA));
		    flag = Battle.battle(Scom,Ccom,1,randA);
		    if(flag == -1){ //相手モンスターを倒した場合
			str = in.readLine(); //相手の選択したモンスターの番号を受信
			if(str.equals("END")){ //相手の控えが0
			    System.out.println("あなたの勝ちです！！\n");
			    break;
			}else{
			    Battle.Ccur = Integer.parseInt(str);
			    Battle.showCcur();
			}
		    }else if(flag>=0 && flag<=2){ //交換する場合相手にモンスター番号を送信
			out.println(String.valueOf(flag));
			Battle.showScur();
		    }else if(flag==-10){
			System.out.println("あなたの負けです...\n");
			out.println("END");
			break;
		    }
		    i++;
		    System.out.println("");
		}
	    }finally{
		System.out.println("対戦を終了します。");
		socket.close();
	    }
	}finally{
	    s.close();
	}
    }
}
						       
	