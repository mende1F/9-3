import java.io.*;

public class Start{
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    /*5体のモンスターを表示する*/
    public static void list(){
	for(int i=0; i<5; i++){                            
	    Monster a = new Monster(i);
	    System.out.println("No."+(i+1)+" "+a.name+" TYPE:"+a.type+" HP:"+a.hp+" ATK:"+a.atk+" DEF:"+a.def+" SPD:"+a.spd+"\n");
	}
    }

    /*プレイヤーにモンスターを3体選択させる*/
    public static void select(){
	int[] sel = new int[3]; //選択したモンスターのNoを格納する配列
	for(int i=0; i<3; i++){
	    System.out.println((i+1)+"体目のNoを入力してください。");
	    outer: while(true){
		try{
		    int n = Integer.parseInt(reader.readLine());
		    sel[i] = n;
		    System.out.println("");
		    if(n > 5){
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
	System.out.println("この3体でよろしいですか？(yes/no)");
    }

    /*選択したモンスターで良いか確認する*/
    public static void check(){ //
	try{
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
	}
    }
}
