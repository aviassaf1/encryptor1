package encryptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Cmd {

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
		if(enc){
			if(algoNum==1){
				int key=getNewKey();
				new Ceasar().enc(path,key);
			}
			else if(algoNum==2){
				int key=getNewKey();
				new Xor().dec(path,key);
			}
			else if(algoNum==3){}
		}
		else{
			if(algoNum==1){
				int key=getKeyFomUser();
				new Ceasar().dec(path,key);
			}
			else if(algoNum==2){
				int key=getKeyFomUser();
				new Xor().dec(path,key);
			}
			else if(algoNum==3){}
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

	private static int getKeyFomUser() {
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
	
}
