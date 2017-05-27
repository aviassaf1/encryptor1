package encryptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Cmd {

	public static void main(String[] args) {
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line;
		String path="";
		boolean dec=false;
		boolean enc=false;
		boolean pathExist=false;
		boolean done=false;
		while(!done){
			try {
				System.out.println("hello, please choose enryption(E) or decription(D)");
				while(!enc&!dec){
					line=buffer.readLine();
					if(line.equals("E"))
						enc=true;
					else if(line.equals("D"))
						dec=true;
					else{
						System.out.println("please choose only one of the options enryption(E) or decription(D)");
					}
				}
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
				done=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String ans="";
		if(dec){
			ans=dec1(path);
		}
		else if(enc){
			ans=enc1(path);
		}
		System.out.println(ans);
	}

	public static String enc1(String path) {
		List<String> lines=new LinkedList<String>();
		try {
			lines = Files.readAllLines(Paths.get(path),StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String ans="enc:";
		for(int i=0;i<lines.size();i++){
			ans=ans+lines.get(i)+"\n";
		}
		return ans;
	}
	public static String dec1(String path) {
		List<String> lines=new LinkedList<String>();
		try {
			lines = Files.readAllLines(Paths.get(path),StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String ans="dec:";
		for(int i=0;i<lines.size();i++){
			ans=ans+lines.get(i)+"\n";
		}
		return ans;
	}
}
