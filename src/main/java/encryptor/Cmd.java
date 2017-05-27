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
		boolean done=false;
		while(!done){
			try {
				System.out.println("hello, please choose enryption(E) or decription(D)");
				while(!chooseEncOrDec){
					line=buffer.readLine();
					if(line.equals("E")){
						chooseEncOrDec=true;
						path=getFilePath();
						int key=getNewKey();
						Ceasar.enc1(path,key);
						done=true;
					}
					else if(line.equals("D")){
						chooseEncOrDec=true;
						path=getFilePath();
						int key=getKeyFomUser();
						Ceasar.dec1(path,key);
						done=true;
					}
					else{
						System.out.println("please choose only one of the options enryption(E) or decription(D)");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
