package encryptor;

public class Multiplication extends Algorithm {

	@Override
	public byte[]  enc(String path, int key,byte[] plaintext) {
		long startTime = System.nanoTime();
		notifyObservers("start Multiplication encryption");
		byte[] ans;
		ans = plaintext;
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]*key);
		}	
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("finish Multiplication encryption and took: " +duration);
		return ans;
	}

	@Override
	public byte[] dec(String path, int key,byte[] plaintext) throws IlegalKeyException {
		long startTime = System.nanoTime();
		notifyObservers("start Multiplication decryption");
		byte reversKey=findreversKey(key);
		byte[] ans;
		ans = plaintext;
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]*reversKey);
		}	
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("finish Multiplication decryption and took: " +duration);
		return ans;
	}

	private byte findreversKey(int key) throws IlegalKeyException {
		byte b=Byte.MIN_VALUE;
		boolean found=false;
		while(!found){
			if(1==(byte)(key*b)){
				found=true;
			}
			else if(b==Byte.MAX_VALUE){
				throw new IlegalKeyException("there was problem to find key, key unvalied");
			}
			else{
				b=(byte) (b+1);
			}
		}
		return b;
	}
}
