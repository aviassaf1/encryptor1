package algorithms;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import algorithms.exceptions.IlegalKeyException;

public abstract class Algorithm extends Observable {

	protected abstract byte[] encImpl(byte[] plaintext) throws IlegalKeyException;
	protected abstract byte[] decImpl(byte[] plaintext)throws IlegalKeyException ;
	public abstract void beforeEnc(String keysPath)throws IOException ;
	public abstract void beforeDec(List<Integer> keys)throws IlegalKeyException ;
	
	public byte[] enc(byte[] plaintext/*,String keysPath*/)throws IlegalKeyException,IOException {
		//beforeEnc(keysPath);
		return encImpl(plaintext);
	}
	public byte[] dec(byte[] plaintext/*, List<Integer> keys*/)throws IlegalKeyException {
		//beforeDec(keys);
		return decImpl(plaintext);
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
	
	
//	protected static int getKeyFromUser() {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("please enter your key:");
//		boolean enteredNum=false;
//		int num=0;
//		while(!enteredNum){
//			try {
//				num = Integer.parseInt(br.readLine());
//				enteredNum=true;
//			} catch (Exception e) {
//				System.out.println("please enter your key (key should be a number):");
//			}
//		}
//		return num;
//	}
//	
//	protected static int getKeyMulFromUser() {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("please enter your key:");
//		boolean enteredNum=false;
//		int num=0;
//		while(!enteredNum){
//			try {
//				num = Integer.parseInt(br.readLine());
//				if(num%2==0){
//					System.out.println("please enter your key (key should be an odd number):");
//				}
//				else{
//					enteredNum=true;
//				}
//			} catch (Exception e) {
//				System.out.println("please enter your key (key should be an odd number):");
//			}
//		}
//		return num;
//	}
}
