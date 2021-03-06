package g9_3;

import java.io.*;

class Battle{
    static int[] Ssel = new int[3];
    static int[] Csel = new int[3];
    static Monster[] Smons = new Monster[3];
    static Monster[] Cmons = new Monster[3];
    static int Scur,Ccur; //戦闘中のモンスターのSsel,Csel配列における番号（添字）
    static int Srest,Crest; //残りモンスター数
    static void begin(){
	Ssel = Start.getMonsters(1);
	for(int i=0; i<3; i++){
	    Smons[i] = new Monster(Ssel[i]-1);
	}
	Csel = Start.getMonsters(2);
	for(int i=0; i<3; i++){
	    Cmons[i] = new Monster(Csel[i]-1);
	}
	Scur = 0; Ccur = 0;
	Srest = 3; Crest = 3; 
	System.out.println("対戦を開始します。");
    }

    static int command(int p){
	show_battle(p);
	System.out.println("プレイヤー"+p+"はどうする？");
	if(p==1 && Smons[Scur].spatk==false){
	    System.out.println("1.通常攻撃　3.交換");
	}else if(p==2 && Cmons[Ccur].spatk==false){
	    System.out.println("1.通常攻撃　3.交換");
	}else{
	    System.out.println("1.通常攻撃　2.特殊攻撃　3.交換");
	}
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	int com=0;
	while(true){
	    try{
		com = Integer.parseInt(reader.readLine());
		/*battle関数において、引数が10以上の時に交換であると判別できる。一の位が交換後のモンスターの番号*/
		if(com==3) com = exchange(p)+10;
		break;
	    }catch(IOException e){
		System.out.println(e);
	    }catch(NumberFormatException e){
		System.out.println("数字で正しく入力してください。");
	    }
	}
	return com;
    }
    
    /*共通する操作をまとめて記述するために、Scomは-1、Ccomは+1して引数に渡す。
      つまり、Scomの1,2→comの0,1、Ccomの1,2→comの2,3となる。
      これにより、2で割った商でServerとClientを区別できる。*/
    static int calc_damage(Monster atkr,Monster defr,int com,int p){
	int dmg=0;
	double dmgRate = calc_dmgRate(atkr.type,defr.type);
	switch(com){
	case 0:
	    System.out.println("プレイヤー1の"+atkr.name+"の通常攻撃！");
	    break;
	case 1:
	    System.out.println("プレイヤー1の"+atkr.name+"の特殊攻撃！");
	    Smons[Scur].spatk = false;
	    break;
	case 2:
	    System.out.println("プレイヤー2の"+atkr.name+"の通常攻撃！");	    
	    break;
	case 3:
	    System.out.println("プレイヤー2の"+atkr.name+"の特殊攻撃！");
	    Cmons[Ccur].spatk = false;
	    break;
	default: break;
	}
	if(com%2==0){ //通常攻撃
	    dmg = (int)(atkr.atk*dmgRate)-defr.def;
	}else{ //特殊攻撃
	    dmg = atkr.atk*4-defr.def;
	}
	int patk = com/2+1; //攻撃側のプレイヤー
	int pdef = 2/patk; //守備側のプレイヤー
	if(dmg>0){		
	    defr.hp -= dmg;
	    System.out.println("プレイヤー"+pdef+"の"+defr.name+"に"+dmg+"のダメージ！\n");
	    if(defr.hp>0){
		show_battle(p);
	    }else{
		/*どちらかのモンスターが倒れたときの交換は通信を伴うので、状況毎に異なる戻り値を返す。
		  プレイヤーは自分のモンスターが倒れたら交換し、そのモンスター番号を返す。
		  相手のモンスターが倒れた場合は-1を返し、main関数で相手からのデータを受信する。
		  どちらでもない時は-2を返す。
		 */
		if(pdef==1){
		    System.out.println("プレイヤー"+pdef+"の"+Smons[Scur].name+"は倒れた！");  	 
		    Srest--;
		    if(p==pdef){
			Scur = exchange(p);
			return Scur;
		    }
		    System.out.println("相手がモンスターを選択中です。");
		    return -1;
		}else if(pdef==2){
		    System.out.println("プレイヤー"+pdef+"の"+Cmons[Ccur].name+"は倒れた！");
		    Crest--;
		    if(p==pdef){
			Ccur = exchange(p);
			return Ccur;
		    }
		    System.out.println("相手がモンスターを選択中です。");
		    return -1;
		}
	    }
	}else if(dmg<0){
	    atkr.hp += dmg;
	    System.out.println("プレイヤー"+patk+"の"+atkr.name+"に"+(-dmg)+"のダメージ！");
	    if(atkr.hp>0){
		show_battle(p);
	    }else{
		System.out.println(0);
		if(patk==1){
		    System.out.println("プレイヤー"+patk+"の"+Smons[Scur].name+"は倒れた！");  	 
		    Srest--;
		    if(p==patk){
			Scur = exchange(p);
			return Scur;
		    }
		    System.out.println("相手がモンスターを選択中です。");
		    return -1;
		}else if(patk==2){
		    System.out.println("プレイヤー"+patk+"の"+Cmons[Ccur].name+"は倒れた！");  	 
		    Crest--;
		    if(p==patk){
			Ccur = exchange(p);
			return Ccur;
		    }
		    System.out.println("相手がモンスターを選択中です。");
		    return -1;
		}
	    }
	}
	return 3;
    }

    static double calc_dmgRate(String type1, String type2){
	if(type1.equals(type2)) return 1.0;
	switch(type1){
	case "wood":
	    if(type2.equals("earth")){return 2.0;}
	    else if(type2.equals("fire")){return 0.5;};
	    break;
	case "fire":
	    if(type2.equals("metal")){return 2.0;}
	    else if(type2.equals("earth")){return 0.5;};
	    break;
	case "earth":
	    if(type2.equals("water")){return 2.0;}
	    else if(type2.equals("metal")){return 0.5;};
	    break;
	case "metal":
	    if(type2.equals("wood")){return 2.0;}
	    else if(type2.equals("water")){return 0.5;};
	    break;
	case "water":
	    if(type2.equals("fire")){return 2.0;}
	    else if(type2.equals("wood")){return 0.5;};
	    break;
	default:
	    break;
	}
	return 1.0;
    }
    
    static int battle(int Scom, int Ccom,int p, double randA){
	int flag=3; //ダメージ計算結果をmain関数に返し、その後の処理を分岐させる
	/*プレイヤー1が交換する場合*/
	if(Scom>=10){
	    System.out.println("プレイヤー1は"+Smons[Scur].name+"を引っ込めて、"+Smons[Scom-10].name+"を繰り出した！");
	    Scur = Scom-10;
	    show_battle(p);
	    /*プレイヤー2も交換する場合*/
	    if(Ccom>=10){
		System.out.println("プレイヤー2は"+Cmons[Ccur].name+"を引っ込めて、"+Cmons[Ccom-10].name+"を繰り出した！");
		Ccur = Ccom-10;
		show_battle(p);
	    }
	    /*プレイヤー2は攻撃する場合*/
	    else{
		flag = calc_damage(Cmons[Ccur],Smons[Scur],Ccom+1,p);
	    }
	}
	/*プレイヤー1が攻撃する場合*/
	else{ 
	    /*プレイヤー2は交換する場合*/
	    if(Ccom>=10){
		System.out.println("プレイヤー2は"+Cmons[Ccur].name+"を引っ込めて、"+Cmons[Ccom-10].name+"を繰り出した！");
		Ccur = Ccom-10;
		show_battle(p);
		flag = calc_damage(Smons[Scur],Cmons[Ccur],Scom-1,p);
	    }
	    /*プレイヤー2も攻撃する場合、お互いのスピードを比較して攻撃順序を決定*/
	    else{
		if(Smons[Scur].spd > Cmons[Ccur].spd){
		    flag = calc_damage(Smons[Scur],Cmons[Ccur],Scom-1,p);
		    /*片方が倒れたら交換を行い、その戦闘は中断*/
		    if(flag<3) return flag;
		    flag = calc_damage(Cmons[Ccur],Smons[Scur],Ccom+1,p);
		}else if(Smons[Scur].spd < Cmons[Ccur].spd){
		    flag = calc_damage(Cmons[Ccur],Smons[Scur],Ccom+1,p);
		    if(flag<3) return flag;		    
		    flag = calc_damage(Smons[Scur],Cmons[Ccur],Scom-1,p);
		}else{
		    if(randA<50){
			flag = calc_damage(Smons[Scur],Cmons[Ccur],Scom-1,p);
			if(flag<3) return flag;			
			flag = calc_damage(Cmons[Ccur],Smons[Scur],Ccom+1,p);
		    }else{
			flag = calc_damage(Cmons[Ccur],Smons[Scur],Ccom+1,p);
			if(flag<3) return flag;
			flag = calc_damage(Smons[Scur],Cmons[Ccur],Scom-1,p);
		    }
		}
	    }
	}
	System.out.println("");
	return flag;
    }

    static int exchange(int p){
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	int[] rest = new int[2];
	int j = 0;
	if(p==1){
	    if(Srest==0) return -10;
	    System.out.println("交換したいモンスターを選択してください。");
	    for(int i=0; i<3; i++){
		if(i!=Scur && Smons[i].hp>0){
		    System.out.println(i+". "+Smons[i].name+" TYPE:"+Smons[i].type+" HP:"+Smons[i].hp+" ATK:"+Smons[i].atk+" DEF:"+Smons[i].def+" SPD:"+Smons[i].spd+"\n");
		    rest[j] = i;
		    j++;
		}
	    }
	}else if(p==2){
	    if(Crest==0) return -10;
	    System.out.println("交換したいモンスターを選択してください。");
	    for(int i=0; i<3; i++){
		if(i!=Ccur && Cmons[i].hp>0){
		System.out.println(i+". "+Cmons[i].name+" TYPE:"+Cmons[i].type+" HP:"+Cmons[i].hp+" ATK:"+Cmons[i].atk+" DEF:"+Cmons[i].def+" SPD:"+Cmons[i].spd+"\n");
		rest[j] = i;
		j++;
		}
	    }
	}
	int next=0;
	while(true){
	    try{
		next = Integer.parseInt(reader.readLine());
		if(!(next == rest[0] || next == rest[1])){
		    System.out.println("正しく入力してください。");
		    continue;
		}
		break;
	    }catch(IOException e){
		System.out.println(e);
	    }catch(NumberFormatException e){
		System.out.println("数字で正しく入力してください。");
	    }
	}
	return next;
    }
    
    static void showScur(){
	System.out.println("プレイヤー1は"+Smons[Scur].name+"を繰り出した！");
	System.out.println("");
    }
    static void showCcur(){
	System.out.println("プレイヤー2は"+Cmons[Ccur].name+"を繰り出した！");
	System.out.println("");
    }
    static void show_battle(int p){
	if(p == 1){
	    System.out.println("       "+Cmons[Ccur].name+" HP:"+Cmons[Ccur].hp);
	    System.out.println("        VS");
	    System.out.println(Smons[Scur].name+" HP:"+Smons[Scur].hp);
	}else{
	    System.out.println("       "+Smons[Scur].name+" HP:"+Smons[Scur].hp);
	    System.out.println("        VS");
	    System.out.println(Cmons[Ccur].name+" HP:"+Cmons[Ccur].hp);
	}
    }
}
	