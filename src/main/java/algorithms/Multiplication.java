package algorithms;

import java.io.IOException;
import java.util.List;

import algorithms.exceptions.IlegalKeyException;
import encryptor.FileEncryptor;

public class Multiplication extends Algorithm {

	private int key;
	@Override
	protected byte[]  encImpl(byte[] plaintext) {
		long startTime = System.nanoTime();
		setChanged();
		notifyObservers("start Multiplication encryption");
		byte[] ans;
		ans = plaintext;
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]*key);
		}	
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		setChanged();
		notifyObservers("finish Multiplication encryption and took: " +seconds+" seconds");
		return ans;
	}

	@Override
	protected byte[] decImpl(byte[] plaintext) throws IlegalKeyException {
		boolean goodKey=false;
		while(!goodKey)
		{
				long startTime = System.nanoTime();
				setChanged();
				notifyObservers("start Multiplication decryption");
				byte reversKey=findreversKey(key);
				byte[] ans;
				ans = plaintext;
				for (int i = 0; i < ans.length; i++) {
					ans[i]=(byte) (ans[i]*reversKey);
				}	
				long endTime = System.nanoTime();
				long duration = (endTime - startTime);
				double seconds = (double)duration / 1000000000.0;
				setChanged();
				notifyObservers("finish Multiplication decryption and took: " +seconds+" seconds");
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
	public void beforeEnc(String keysPath) throws IOException{
		key=getNewMulKey();
		FileEncryptor.saveKeyToFile(keysPath,key);
	}

	@Override
	public void beforeDec(List<Integer> keys)throws IlegalKeyException  {
		//key=getKeyMulFromUser();
		key=keys.get(0);
		if(key%2==0){
			throw new IlegalKeyException("The key in file is ilegal");
		}
		keys.remove(0);
	}
}
