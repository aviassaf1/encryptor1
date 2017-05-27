package encryptor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileEncryptor {
	
	protected static byte[] getFileBytes(String path) {
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
	protected static void saveDecFile(String oldPath, byte[] decBytes) {
		FileOutputStream out;
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static void saveEncFile(String oldPath,byte[] encBytes) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(oldPath+".encrypted");
			out.write(encBytes);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
