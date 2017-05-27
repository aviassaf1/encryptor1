package encryptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Cmd implements Observer{

	public static void main(String[] args) {
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line;
		String path="";
		boolean chooseEncOrDec=false;
		boolean enc=false;
		boolean done=false;
		while(!done){
			try {
				System.out.println("hello, please choose enryption(E) or decription(D)");
				while(!chooseEncOrDec){
					line=buffer.readLine();
					if(line.equals("E")){
						chooseEncOrDec=true;
						enc=true;
					}
					else if(line.equals("D")){
						chooseEncOrDec=true;
					}
					else{
						System.out.println("please choose only one of the options enryption(E) or decription(D)");
					}
				}
				path=getFilePath();
				int algoNum=chooseAlgorithm();
				activateAlgo(enc,algoNum,path);
				done=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void activateAlgo(boolean enc, int algoNum,String path) {
		Cmd cmd=new Cmd(); 
		if(enc){
			if(algoNum==1){
				int key=getNewKey();
				Ceasar ceasar= new Ceasar();
				ceasar.addObserver(cmd);
				ceasar.enc(path,key);
			}
			else if(algoNum==2){
				int key=getNewKey();
				Xor xor= new Xor();
				xor.addObserver(cmd);
				xor.enc(path,key);
			}
			else if(algoNum==3){
				int key=getNewMulKey();
				Multiplication multiplication= new Multiplication();
				multiplication.addObserver(cmd);
				multiplication.enc(path,key);
			}
		}
		else{
			if(algoNum==1){
				int key=getKeyFromUser();
				Ceasar ceasar= new Ceasar();
				ceasar.addObserver(cmd);
				ceasar.dec(path,key);
			}
			else if(algoNum==2){
				int key=getKeyFromUser();
				Xor xor= new Xor();
				xor.addObserver(cmd);
				xor.dec(path,key);
			}
			else if(algoNum==3){
				boolean goodKey=false;
				while(!goodKey)
				{
					int key=getKeyMulFromUser();
					try {
						Multiplication multiplication= new Multiplication();
						multiplication.addObserver(cmd);
						multiplication.dec(path,key);
						goodKey=true;
					} catch (IlegalKeyException e) {
						System.err.println("there was a problem with the key, please try another key");
					}
				}
			}
		}
	}

	private static int chooseAlgorithm() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("please choose the encyption  algorithm:");	
		System.out.println("(1) Ceaser, (2) Xor, (3) Multiplicayion");
		boolean enteredNum=false;
		int num=0;
		while(!enteredNum){
			try {
				num = Integer.parseInt(br.readLine());
				if(num<1||num>3){
					System.out.println("please enter number (1-3):");
				}
				else{
					enteredNum=true;
				}
			} catch (Exception e) {
				System.out.println("please enter number (1-3):");
			}
		}
		return num;
	}

	private static int getKeyFromUser() {
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
	
	private static int getKeyMulFromUser() {
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

	private static String getFilePath() throws IOException {
		boolean pathExist=false;
		String path="";
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("plese enter file path here:");
		while(!pathExist){
			path=buffer.readLine();
			File f = new File(path);
			if(f.exists() && !f.isDirectory()) { 
			   pathExist=true;
			}
			else{
				System.out.println("the path you enterd is not a correct file path, please try again:");
			}
		}
		return path;
	}

	private static int getNewKey() {
		Random rnd=new Random();
		int key=rnd.nextInt(255);
		System.out.println("The key is: "+key );
		return key;
	}
	private static int getNewMulKey() {
		Random rnd=new Random();
		int key=0;
		while(key%2==0||key==0){//may not need ==0
			key=rnd.nextInt(255);
		}
		System.out.println("The key is: "+key );
		return key;
	}

	public void update(Observable o, Object arg) {
		System.out.println(arg);
	}
	
}
