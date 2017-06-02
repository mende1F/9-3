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
		System.out.println("以下の5体のモンスターの中から3体を選択してください。");
		Start.list();
		Start.select();
		int mysel[] = new int[3];
		mysel = Start.check();
		System.out.println("あなたの選んだモンスター:");
		Monster[] mymons = new Monster[3];
		for(int i=0; i<3; i++){
		    mymons[i] = new Monster(mysel[i]-1);
		    System.out.println(mymons[i].name);
		}
		System.out.println("相手の選んだモンスター");
		Monster[] oppmons = new Monster[3];
		for(int i=0; i<3; i++){
		    int oppsel = Integer.parseInt(in.readLine());
		    oppmons[i] = new Monster(oppsel-1);		    
		    System.out.println(oppmons[i].name);
		}
		String str = in.readLine();
		if(str.equals("END")) out.println(str);
		System.out.println("対戦を開始します。");
	    }finally{
		System.out.println("対戦を終了します。");
		socket.close();
	    }
	}finally{
	    s.close();
	}
    }
}
						       
	