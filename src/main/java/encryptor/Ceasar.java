package encryptor;

import java.io.IOException;
import java.util.List;

public class Ceasar extends Algorithm {
	private int key;
	@Override
	public byte[] encImpl(byte[] plaintext) {
		long startTime = System.nanoTime();
		notifyObservers("start Ceasar encryption");
		byte[] ans=plaintext;
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]+key);
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime); 
		notifyObservers("finish Ceasar encryption and took: " +duration);
		return ans;
	}
	
	@Override
	public byte[] decImpl( byte[] plaintext) {
		long startTime = System.nanoTime();
		notifyObservers("start Ceasar decryption");
		byte[] ans;
		ans = plaintext;
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]-key);
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("finish Ceasar decryption and took: " +duration);
		return ans;
	}

	@Override
	public void beforeEnc(String keysPath)throws IOException{
		key=getNewKey();
		FileEncryptor.saveKeyToFile(keysPath, key);
	}

	@Override
	public void beforeDec(List<Integer> keys) throws IlegalKeyException {
		//key=getKeyFromUser();
		key=keys.get(0);
		if(key%2==0){
			throw new IlegalKeyException("The key in file is ilegal");
		}
		keys.remove(0);
	}
	
	
	
}
