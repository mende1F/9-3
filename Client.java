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
	    Start.list();
	    Start.select();
	    int[] mysel = new int[3]; //自分の選んだモンスターの番号を格納する配列
	    int rest = 3;
	    mysel = Start.check();
	    System.out.println("あなたの選んだモンスター:");
	    Monster[] mymons = new Monster[3];
	    for(int i=0; i<3; i++){
		mymons[i] = new Monster(mysel[i]-1);
		System.out.println(mymons[i].name);
	    }
	    for(int i=0; i<3; i++){
		String s = String.valueOf(mysel[i]);
		out.println(s);
	    }
	    out.println("END");
	    String str = in.readLine();
	    System.out.println("対戦を開始します。");
	}finally{
	    System.out.println("対戦を終了します。");
	    socket.close();
	}
    }
}
       