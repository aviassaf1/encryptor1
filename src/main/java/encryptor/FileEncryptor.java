package encryptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import algorithms.exceptions.IlegalKeyException;

public class FileEncryptor {
	
	protected static byte[] getFileBytes(String path) throws IOException {
		byte[] byts=new byte[0];
		byts = Files.readAllBytes(Paths.get(path));
		//System.err.println("there was a problem to get "+path+" please try another path");
		byte[] ans=new byte[byts.length];
		for(int i=0;i<byts.length;i++){
			ans[i]=byts[i];
		}
		return ans;
	}
	protected static void saveDecFile(String oldPath, byte[] decBytes) throws IOException {
		FileOutputStream out;
		if(oldPath.contains(".encrypted")){
			oldPath=oldPath.substring(0, oldPath.indexOf(".encrypted"));
		}
		String[] pathParts=oldPath.split("\\.");
		String newPath="";
		for(int i=0;i<pathParts.length-1;i++){
			newPath=pathParts[i]+".";
		}
		newPath=newPath.substring(0, newPath.length()-1)+"_decrypted."+pathParts[pathParts.length-1];
		out = new FileOutputStream(newPath);
		out.write(decBytes);
		out.close();
	}
	
	protected static void saveEncFile(String oldPath,byte[] encBytes) throws IOException {
		FileOutputStream out;
		out = new FileOutputStream(oldPath+".encrypted");
		out.write(encBytes);
		out.close();
	}
	protected static List<Integer> getKeys(String keyPath) throws IOException, IlegalKeyException {
		List<Integer> keys=new LinkedList<Integer>();
		BufferedReader in = new BufferedReader(new FileReader(keyPath));
        String line;
        while ((line = in.readLine()) != null) {
            int key;
			try {
				key = Integer.parseInt(line);
			} catch (Exception e) {
				in.close();
				throw new IlegalKeyException("one of the keys wasnt valid key");
			}
            keys.add(key);
        }
        in.close();
        return keys;
	}
	public static void saveKeyToFile(String keysPath, int key) throws IOException {
		PrintWriter out = new PrintWriter(new FileOutputStream(keysPath+"\\key.bin",true));
		out.println(""+key);
		out.close();
	}
	public static void saveEncFiles(File f, byte[] enc) throws IOException {
		FileOutputStream out;
		out = new FileOutputStream(f.getParent()+"\\encrypted\\"+f.getName());
		out.write(enc);
		out.close();
	}
	public static void saveDecFiles(File f, byte[] dec) throws IOException {
		FileOutputStream out;
		out = new FileOutputStream(f.getParent()+"\\decrypted\\"+f.getName());
		out.write(dec);
		out.close();
	}
}
