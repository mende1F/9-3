package g9_3;

class Monster{
    String name,type;
    int hp,atk,def,spd;
    boolean spatk;
    private static String[] nameArray = {"ドルフィーナ","ウッドマン","ファイアード","モグーン","メタルン"};
    private static String[] typeArray = {"water","wood","fire","earth","metal"};
    private static int[] hpArray = {102,120,72,149,81};
    private static int[] atkArray = {152,174,206,168,238};
    private static int[] defArray = {95,113,67,123,111};
    private static int[] spdArray = {54,38,92,24,48};
    Monster(int i){
	name = nameArray[i];
	type = typeArray[i];
	hp = hpArray[i];
	atk = atkArray[i];
	def = defArray[i];
	spd = spdArray[i];
	spatk = true;
    }
    static void showMonsters(int i){
	System.out.println("No."+(i+1)+"  "+nameArray[i]+"  TYPE:"+typeArray[i]);
	System.out.print("HP ");
	for(int j=0; j<(hpArray[i]/30); j++){
	    System.out.print("◆");
	}
	System.out.println("");
	System.out.print("ATK ");
	for(int j=0; j<(atkArray[i]/50); j++){
	    System.out.print("◆");
	}
	System.out.println("");
	System.out.print("DEF ");
	for(int j=0; j<(defArray[i]/25); j++){
	    System.out.print("◆");
	}
	System.out.println("");
	System.out.print("SPD ");
	for(int j=0; j<(spdArray[i]/20); j++){
	    System.out.print("◆");
	}
	System.out.println("");
    }
}
