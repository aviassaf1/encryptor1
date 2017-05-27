package encryptor;

public class Xor extends Algorithm {

	@Override
	public void enc(String path, int key) {
		long startTime = System.nanoTime();
		notifyObservers("start xor encryption");
		byte[] ans;
		try {
			ans = FileEncryptor.getFileBytes(path);
			for (int i = 0; i < ans.length; i++) {
				ans[i]=(byte) (ans[i]^key);
			}	
			FileEncryptor.saveEncFile(path,ans);
		} catch (Exception e) {
			System.err.println("there was a problem to get "+path+" please try another path");
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("finish xor encryption and took: " +duration);
	}

	@Override
	public void dec(String path, int key) {
		long startTime = System.nanoTime();
		notifyObservers("start xor decryption");
		byte[] ans;
		try {
			ans = FileEncryptor.getFileBytes(path);
			for (int i = 0; i < ans.length; i++) {
				ans[i]=(byte) (ans[i]^key);
			}	
			FileEncryptor.saveDecFile(path,ans);
		} catch (Exception e) {
			System.err.println("there was a problem to get "+path+" please try another path");
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("stop xor decryption and took: " +duration);
	}

}
