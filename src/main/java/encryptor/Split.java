package encryptor;

import java.io.IOException;
import java.util.List;

public class Split extends Algorithm {

	Algorithm alg1;
	Algorithm alg2;
	public Split(Algorithm alg1,Algorithm alg2) {
		this.alg1=alg1;
		this.alg2=alg2;
	}
	@Override
	protected byte[] encImpl(byte[] plaintext) throws IlegalKeyException{
		long startTime = System.nanoTime();
		notifyObservers("start Split encryption");
		byte[] plaintext2=new byte[plaintext.length];
		for (int i = 0; i < plaintext.length; i++) {
			plaintext2[i]=plaintext[i];
		}
		byte[] ans= alg1.encImpl(plaintext);
		byte[] ans2= alg2.encImpl(plaintext2);
		for (int i = 0; i < ans.length; i++) {
			if(i%2==0){
				ans[i]=ans2[i];
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("stop Split encryption and took: " +duration);
		return ans;
	}

	@Override
	protected byte[] decImpl(byte[] plaintext)throws IlegalKeyException {
		long startTime = System.nanoTime();
		notifyObservers("start Split decryption");
		byte[] plaintext2=new byte[plaintext.length];
		for (int i = 0; i < plaintext.length; i++) {
			plaintext2[i]=plaintext[i];
		}
		byte[] ans= alg1.decImpl(plaintext);
		byte[] ans2= alg2.decImpl(plaintext2);
		for (int i = 0; i < ans.length; i++) {
			if(i%2==0){
				ans[i]=ans2[i];
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("stop Split decryption and took: " +duration);
		return ans;
	}

	@Override
	protected void beforeEnc(String keysPath)throws IOException {
		alg1.beforeEnc(keysPath);
		alg2.beforeEnc(keysPath);
	}

	@Override
	protected void beforeDec(List<Integer> keys)throws IlegalKeyException {
		alg1.beforeDec(keys);
		alg2.beforeDec(keys);
	}
}
