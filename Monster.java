public class Monster{
    String name,type;
    int hp,atk,def,spd;
    String[] nameArray = {"mons1","mons2","mons3","mons4","mons5"};
    String[] typeArray = {"water","wood","fire","earth","metal"};
    int[] hpArray = {300,400,200,250,250};
    int[] atkArray = {100,120,150,100,180};
    int[] defArray = {150,80,60,100,150};
    int[] spdArray = {50,30,80,20,40};
    Monster(int i){
	name = nameArray[i];
	type = typeArray[i];
	hp = hpArray[i];
	atk = atkArray[i];
	def = defArray[i];
	spd = spdArray[i];
    }
}
