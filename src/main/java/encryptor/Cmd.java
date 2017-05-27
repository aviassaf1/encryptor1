package encryptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cmd {

	public static void main(String[] args) {
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line;
		String path="";
		boolean chooseEncOrDec=false;
		boolean done=false;
		byte[] ans=new byte[0];
		while(!done){
			try {
				System.out.println("hello, please choose enryption(E) or decription(D)");
				while(!chooseEncOrDec){
					line=buffer.readLine();
					if(line.equals("E")){
						chooseEncOrDec=true;
						path=getFilePath();
						ans=enc1(path);
						done=true;
					}
					else if(line.equals("D")){
						chooseEncOrDec=true;
						path=getFilePath();
						ans=dec1(path);
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
		for(int i=0;i<ans.length;i++){
			System.out.println(ans[i]);
		}
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

	private static byte[] enc1(String path) {
		return getFileBytes(path);
	}
	private static byte[] dec1(String path) {
		return getFileBytes(path);
	}
	private static byte[] getFileBytes(String path) {
		byte[] byts=new byte[0];
		try {
			byts = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] ans=new byte[byts.length];
		for(int i=0;i<byts.length;i++){
			ans[i]=byts[i];
		}
		return ans;
	}
}
