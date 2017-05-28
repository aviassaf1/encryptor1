package algorithms;

import java.io.IOException;
import java.util.List;

import encryptor.FileEncryptor;

public class Xor extends Algorithm {
	private int key;
	@Override
	protected byte[] encImpl(byte[] plaintext) {
		long startTime = System.nanoTime();
		setChanged();
		notifyObservers("start xor encryption");
		byte[] ans;
		ans = plaintext;
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]^key);
		}	
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		setChanged();
		notifyObservers("finish xor encryption and took: " +seconds+" seconds");
		return ans;
	}

	@Override
	protected byte[] decImpl(byte[] plaintext) {
		long startTime = System.nanoTime();
		setChanged();
		notifyObservers("start xor decryption");
		byte[] ans;
		ans = plaintext;
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]^key);
		}	
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		setChanged();
		notifyObservers("stop xor decryption and took: " +seconds+" seconds");
		return ans;
	}
	
	@Override
	public void beforeEnc(String keysPath) throws IOException{
		key=getNewKey();
		FileEncryptor.saveKeyToFile(keysPath,key);
	}

	@Override
	public void beforeDec(List<Integer> keys) {
		//key=getKeyFromUser();
		key=keys.get(0);
		keys.remove(0);
	}

}
