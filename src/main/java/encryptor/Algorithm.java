package encryptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Random;

public abstract class Algorithm extends Observable {
	public abstract byte[] enc(byte[] plaintext)throws IlegalKeyException ;
	public abstract byte[] dec(byte[] plaintext)throws IlegalKeyException ;
	
	protected static int getKeyFromUser() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("please enter your key:");
		boolean enteredNum=false;
		int num=0;
		while(!enteredNum){
			try {
				num = Integer.parseInt(br.readLine());
				enteredNum=true;
			} catch (Exception e) {
				System.out.println("please enter your key (key should be a number):");
			}
		}
		return num;
	}
	
	protected static int getKeyMulFromUser() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("please enter your key:");
		boolean enteredNum=false;
		int num=0;
		while(!enteredNum){
			try {
				num = Integer.parseInt(br.readLine());
				if(num%2==0){
					System.out.println("please enter your key (key should be an odd number):");
				}
				else{
					enteredNum=true;
				}
			} catch (Exception e) {
				System.out.println("please enter your key (key should be an odd number):");
			}
		}
		return num;
	}
	
	protected static int getNewKey() {
		Random rnd=new Random();
		int key=rnd.nextInt(255);
		System.out.println("The key is: "+key );
		return key;
	}
	protected static int getNewMulKey() {
		Random rnd=new Random();
		int key=0;
		while(key%2==0||key==0){//may not need ==0
			key=rnd.nextInt(255);
		}
		System.out.println("The key is: "+key );
		return key;
	}
}
