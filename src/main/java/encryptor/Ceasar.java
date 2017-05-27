package encryptor;


public class Ceasar extends Algorithm {

	@Override
	public byte[] enc(String path,int key,  byte[] plaintext) {
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
	public byte[] dec(String path,int key, byte[] plaintext) {
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
	
	
	
}
