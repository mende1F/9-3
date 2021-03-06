package g9_3;
import java.io.*;

class Start{
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int[] sel = new int[3];  //選択したモンスターのNoを格納する配列
    static int[] Ssel = new int[3];
    static int[] Csel = new int[3];
    /*5体のモンスターを表示する*/
    static void list(){
	System.out.println("以下の5体のモンスターの中から3体を選択してください。");
	for(int i=0; i<5; i++){                            
	    Monster.showMonsters(i);
	}
    }

    /*プレイヤーにモンスターを3体選択させる*/
    static void select(){
	for(int i=0; i<3; i++){
	    System.out.println((i+1)+"体目のNoを入力してください。");
	    outer: while(true){
		try{
		    int n = Integer.parseInt(reader.readLine());
		    sel[i] = n;
		    System.out.println("");
		    if(n < 1 || n > 5){
			System.out.println("Noは1〜5の間で入力してください。");
			continue;
		    }
		    for(int j=0; j<i; j++){
			if(n == sel[j]){
			    System.out.println("同じモンスターは1体までしか選択できません。\nもう一度入力してください。");
			    continue outer;
			}
		    }
		    break;
		}catch(IOException e){
		    System.out.println(e);
		}catch(NumberFormatException e){
		    System.out.println("Noを数字で正しく入力してください。");
		}
	    }
	}
	for(int i=0; i<3; i++){
	    Monster a = new Monster(sel[i]-1);
	    System.out.println("No."+(sel[i])+" "+a.name+" TYPE:"+a.type+" HP:"+a.hp+" ATK:"+a.atk+" DEF:"+a.def+" SPD:"+a.spd+"\n");
	}
    }

    /*選択したモンスターで良いか確認する*/
    static void check(int player){
	String line;
	try{
	    System.out.println("");
	    while(true){
		System.out.println("この3体でよろしいですか？(y/n)");
		line = reader.readLine();
		if(!((line.equals("y"))||(line.equals("n")))){
		    System.out.println("yかnで答えてください。");
		}else if(line.equals("n")){
		    System.out.println("もう一度選択し直してください。");
		    Start.select();
		}else if(line.equals("y")){
		    System.out.println("相手の選択を待っています。");
		    break;
		}
		System.out.println("");
	    }
	}catch(IOException e){
	    System.out.println(e);
	}
	if(player == 1){
	    for(int i=0; i<3; i++) Ssel[i] = sel[i];
	}else if(player == 2){
	    for(int i=0; i<3; i++) Csel[i] = sel[i];
	}
    }
    
    /*引数が0ならServer側、1ならClient側のモンスター一覧配列を返す*/
    static int[] getMonsters(int player){
	if(player == 1){
	    return Ssel;
	}else{
	    return Csel;
	}
    }
    
    /*プレイヤー(Server)の選択したモンスター番号を表示する*/
    static void show1(){
	System.out.println("プレイヤー1の選択したモンスター");
	for(int i=0; i<3; i++) System.out.print(Ssel[i]+"  ");
	System.out.println("");
    }
   
    /*プレイヤー2(Client)の選択したモンスター番号を表示する*/
    static void show2(){
	System.out.println("プレイヤー2の選択したモンスター");
	for(int i=0; i<3; i++) System.out.print(Csel[i]+"  ");
	System.out.println("");
    }
}
