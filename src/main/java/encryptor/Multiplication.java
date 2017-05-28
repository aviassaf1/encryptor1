package encryptor;

import java.io.IOException;
import java.util.List;

public class Multiplication extends Algorithm {

	private int key;
	@Override
	protected byte[]  encImpl(byte[] plaintext) {
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
	protected byte[] decImpl(byte[] plaintext) throws IlegalKeyException {
		boolean goodKey=false;
		while(!goodKey)
		{
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
		return null;
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
	@Override
	protected void beforeEnc(String keysPath) throws IOException{
		key=getNewMulKey();
		FileEncryptor.saveKeyToFile(keysPath,key);
	}

	@Override
	protected void beforeDec(List<Integer> keys) {
		//key=getKeyMulFromUser();
		key=keys.get(0);
		keys.remove(0);
	}
}
