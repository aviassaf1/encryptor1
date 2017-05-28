package encryptor;

import java.util.List;
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
				path=getFilePath(false);
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
		Algorithm alg=getAlgorithmFromNum(algoNum);
		alg.addObserver(cmd);
		Boolean done=false;
		if(enc){
			try {
				String keyPath=getFilePath(true);
				alg.enc(plaintext,keyPath);
				done=true;
			} catch (IlegalKeyException e) {
				System.err.println("There was problem with the key");
			}catch (IOException e) {
				System.err.println("There was problem with the file path");
			}
		}
		else{
			while(!done){
				try {
					try {
						String keyPath=getFilePath(false);
						List<Integer> keys=FileEncryptor.getKeys(keyPath);
						alg.dec(plaintext,keys);
						done=true;
					} catch (IOException e) {
						System.err.println("there was a problem with keys file, please try again");
					}
				} catch (IlegalKeyException e) {
					System.err.println("The key is illegal, please try agains");
				}
			}
		}
		return ans;
	}

	private static Algorithm getAlgorithmFromNum(int algoNum) {
		switch (algoNum) {
		case 1:
			return new Ceasar();
		case 2:
			return new Xor();
		case 3:
			return new Multiplication();
		case 4:
			System.out.println("now choose the first ans second algorithm");
			System.out.println("choosing the first algorithm:");
			Algorithm alg1=getAlgorithmFromNum(chooseAlgorithm());
			System.out.println("choosing the second algorithm:");
			Algorithm alg2=getAlgorithmFromNum(chooseAlgorithm());
			return new Double(alg1, alg2);
		case 5:
			System.out.println("choosing the reverse algorithm:");
			Algorithm alg=getAlgorithmFromNum(chooseAlgorithm());
			return new Reverse(alg);
		case 6:
			System.out.println("now choose the first ans second algorithm");
			System.out.println("choosing the first algorithm:");
			Algorithm alg3=getAlgorithmFromNum(chooseAlgorithm());
			System.out.println("choosing the second algorithm:");
			Algorithm alg4=getAlgorithmFromNum(chooseAlgorithm());
			return new Split(alg3, alg4);
		default:
			return new Ceasar();
		}
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
	
	

	private static String getFilePath(Boolean isDirectory) {
		boolean pathExist=false;
		String path="";
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("plese enter file path here:");
		while(!pathExist){
			try {
				path=buffer.readLine();
				File f = new File(path);
				if(f.exists() && (f.isDirectory()==isDirectory)) { 
				   pathExist=true;
				}
				else{
					if(isDirectory){
						System.out.println("the path you enterd is not a correct folder path, please try again:");
					}
					else{
						System.out.println("the path you enterd is not a correct file path, please try again:");
					}
				}
			} catch (IOException e) {
				if(isDirectory){
					System.out.println("the path you enterd is not a correct folder path, please try again:");
				}
				else{
					System.out.println("the path you enterd is not a correct file path, please try again:");
				}
			}
		}
		return path;
	}

	public void update(Observable o, Object arg) {
		System.out.println(arg);
	}
	
}
