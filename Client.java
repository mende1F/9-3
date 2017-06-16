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
	    int rest = 3;
	    Start.check(2);
	    int[] Ssel = new int[3];
	    int[] Csel = new int[3];
	    Csel = Start.getMonsters(2);
	    for(int i=0; i<3; i++){
		String mysel = String.valueOf(Csel[i]);
		out.println(mysel);
	    }
	    for(int i=0; i<3; i++){
		Start.Ssel[i] = Integer.parseInt(in.readLine());
	    }
	    System.out.println("あなたの選んだモンスター");
	    Start.show2();
	    System.out.println("相手の選んだモンスター");
	    Start.show1();
	    System.out.println("");
	    out.println("END");
	    String str = in.readLine();
	    System.out.println("対戦を開始します。");
	}finally{
	    System.out.println("対戦を終了します。");
	    socket.close();
	}
    }
}
       