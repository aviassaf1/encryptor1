package encryptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

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
				try{
					byte[]plaintext = FileEncryptor.getFileBytes(path);
					byte[] ans=activateAlgo(plaintext, enc,algoNum);
					if(enc){
						FileEncryptor.saveEncFile(path,ans);
					}
					else{
						FileEncryptor.saveDecFile(path,ans);
					}
				} catch (IOException e) {
					System.err.println("there was a problem to get "+path+" please try another path");
				}
				done=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static byte[] activateAlgo(byte[] plaintext, boolean enc, int algoNum) {
		Cmd cmd=new Cmd(); 
		if(algoNum<1||algoNum>6)
			return null;
		byte[] ans = null;
		if(enc){
			if(algoNum==1){
				Ceasar ceasar= new Ceasar();
				ceasar.addObserver(cmd);
				ans=ceasar.enc(plaintext);
			}
			else if(algoNum==2){
				Xor xor= new Xor();
				xor.addObserver(cmd);
				ans=xor.enc(plaintext);
			}
			else if(algoNum==3){
				Multiplication multiplication= new Multiplication();
				multiplication.addObserver(cmd);
				ans=multiplication.enc(plaintext);
			}
			else if (algoNum==4) {
				System.out.println("now choose the first ans second algorithm");
				System.out.println("choosing the first algorithm:");
				int alg1Num=chooseAlgorithm();
				System.out.println("Choosing the second algorithm:");
				int alg2Num=chooseAlgorithm();
				ans=activateAlgo(activateAlgo(plaintext, enc, alg1Num), enc, alg2Num);
			}
			else if(algoNum==5){
				System.out.println("Choosing the revers algorithm:");
				int algNum=chooseAlgorithm();
				ans=activateAlgo(plaintext, !enc, algNum);
			}
			else if(algoNum==6){
				System.out.println("Choosing the split algorithm:");
				int algNum=chooseAlgorithm();
				System.out.println("The first split key:");
				byte[] ans1=activateAlgo(plaintext, enc, algNum);
				System.out.println("The second split key:");
				byte[] ans2=activateAlgo(plaintext, enc, algNum);
				ans=new byte[ans2.length];
				for (int i = 0; i < ans.length; i++) {
					if(i%2!=0){
						ans[i]=ans1[i];
					}
					else{
						ans[i]=ans2[i];
					}
				}
			}
		}
		else{
			if(algoNum==1){
				Ceasar ceasar= new Ceasar();
				ceasar.addObserver(cmd);
				ans=ceasar.dec(plaintext);
			}
			else if(algoNum==2){
				Xor xor= new Xor();
				xor.addObserver(cmd);
				ans=xor.dec(plaintext);
			}
			else if(algoNum==3){
				Multiplication multiplication= new Multiplication();
				multiplication.addObserver(cmd);
				ans=multiplication.dec(plaintext);
			}
			else if (algoNum==4) {
				System.out.println("now choose the first ans second algorithm");
				System.out.println("choosing the first algorithm:");
				int alg1Num=chooseAlgorithm();
				System.out.println("choosing the second algorithm:");
				int alg2Num=chooseAlgorithm();
				ans=activateAlgo(activateAlgo(plaintext, enc, alg2Num), enc, alg1Num);
			}
			else if(algoNum==5){
				System.out.println("choosing the revers algorithm:");
				int algNum=chooseAlgorithm();
				ans=activateAlgo(plaintext, !enc, algNum);
			}
			else if(algoNum==6){
				System.out.println("Choosing the split algorithm:");
				int algNum=chooseAlgorithm();
				System.out.println("Choosing the first split key:");
				byte[] ans1=activateAlgo(plaintext, enc, algNum);
				System.out.println("Choosing the second split key:");
				byte[] ans2=activateAlgo(plaintext, enc, algNum);
				ans=new byte[ans2.length];
				for (int i = 0; i < ans.length; i++) {
					if(i%2!=0){
						ans[i]=ans1[i];
					}
					else{
						ans[i]=ans2[i];
					}
				}
			}
		}
		return ans;
	}

	private static int chooseAlgorithm() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("please choose the encyption  algorithm:");	
		System.out.println("(1) Ceaser, (2) Xor, (3) Multiplicayion, (4) Double, (5) Reverse, (6) Split");
		boolean enteredNum=false;
		int num=0;
		while(!enteredNum){
			try {
				num = Integer.parseInt(br.readLine());
				if(num<1||num>6){
					System.out.println("please enter number (1-6):");
				}
				else{
					enteredNum=true;
				}
			} catch (Exception e) {
				System.out.println("please enter number (1-6):");
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

	

	public void update(Observable o, Object arg) {
		System.out.println(arg);
	}
	
}
