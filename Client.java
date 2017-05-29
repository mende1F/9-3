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
	    System.out.println("以下の5体のモンスターの中から3体を選択してください。");
	    for(int i=0; i<5; i++){ //配列に格納した5体のモンスターを表示
		Monster a = new Monster(i);
		System.out.println("No."+(i+1)+" "+a.name+" TYPE:"+a.type+" HP:"+a.hp+" ATK:"+a.atk+" DEF:"+a.def+" SPD:"+a.spd+"\n");
	    }
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    Game.select(); //モンスターNoの入力及び選択モンスターの確認を行う関数
	    Game.check();
	    /*	    try{
		String line = reader.readLine();
		System.out.println("");
		while(true){
		    if(!((line.equals("yes"))||(line.equals("no")))){
			System.out.println("yesかnoで答えてください。");
		    }else if(line.equals("no")){
			System.out.println("もう一度選択し直してください。");
			Start.select();
		    }else if(line.equals("yes")){
			System.out.println("相手の選択を待っています。");
			break;
		    }		   
		    line = reader.readLine();
		    System.out.println("");
		}
	    }catch(IOException e){
		System.out.println(e);
	    }	*/		
	    System.out.println("対戦を終了します。");
	    out.println("END");
	}finally{
	    socket.close();
	}
    }
}
       