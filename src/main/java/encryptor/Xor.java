package encryptor;

public class Xor extends Algorithm {

	@Override
	public byte[] enc(String path, int key,byte[] plaintext) {
		long startTime = System.nanoTime();
		notifyObservers("start xor encryption");
		byte[] ans;
		ans = plaintext;
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]^key);
		}	
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("finish xor encryption and took: " +duration);
		return ans;
	}

	@Override
	public byte[] dec(String path, int key,byte[] plaintext) {
		long startTime = System.nanoTime();
		notifyObservers("start xor decryption");
		byte[] ans;
		ans = plaintext;
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]^key);
		}	
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("stop xor decryption and took: " +duration);
		return ans;
	}

}
