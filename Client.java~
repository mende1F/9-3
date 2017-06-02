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
	    Game.list();
	    Game.select();
	    Game.check();
	    out.println("END");
	    String str = in.readLine();
	    System.out.println("対戦を開始します。");
	}finally{
	    System.out.println("対戦を終了します。");
	    socket.close();
	}
    }
}
       