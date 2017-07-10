package g9_3;

import java.io.*;
import java.net.*;

public class Client{
    public static void main(String[] args) throws IOException{
	InetAddress addr = InetAddress.getByName("localhost");
	System.out.println("接続中です...");
	Socket socket = new Socket(addr,Server.PORT);
	try{
       	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	    System.out.println("接続されました。");
	    Start.list();//モンスターの表示
	    Start.select();//モンスターの選択
	    Start.check(2);//選択モンスターの確認
	    int[] Ssel = new int[3];
	    int[] Csel = new int[3];
	    Csel = Start.getMonsters(2);
	    for(int i=0; i<3; i++) out.println(String.valueOf(Csel[i]));
	    for(int i=0; i<3; i++) Start.Ssel[i] = Integer.parseInt(in.readLine());
	    Start.show2();
	    Start.show1();
	    System.out.println("");
	    out.println("DONE");
	    String str = in.readLine();
	    Battle.begin();
	    int Scom,Ccom;
	    int opcur;
	    int i=1;
	    int flag;
	    int randA;
	    while(true){
		System.out.println("------ターン"+i+"------");
		Ccom = Battle.command(2);
		out.println(String.valueOf(Ccom));
		Scom = Integer.parseInt(in.readLine());
		randA = Integer.parseInt(in.readLine());
		flag = Battle.battle(Scom,Ccom,2,randA);
		if(flag == -1){
		    str = in.readLine();
		    if(str.equals("END")){
			System.out.println("あなたの勝ちです！！\n");
			break;
		    }else{
			Battle.Scur = Integer.parseInt(str);
			Battle.showScur();
		    }
		}else if(flag>=0 && flag<=2){
		    out.println(String.valueOf(flag));
		    Battle.showCcur();
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
    }
}
       