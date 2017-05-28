package algorithms;

import java.io.IOException;
import java.util.List;

import algorithms.exceptions.IlegalKeyException;

public class Double extends Algorithm {

	Algorithm alg1;
	Algorithm alg2;
	public Double(Algorithm alg1,Algorithm alg2) {
		this.alg1=alg1;
		this.alg2=alg2;
	}
	@Override
	protected byte[] encImpl(byte[] plaintext) throws IlegalKeyException{
		long startTime = System.nanoTime();
		setChanged();
		notifyObservers("start Double encryption");
		byte[] ans1= alg1.encImpl(plaintext);
		byte[] ans= alg2.encImpl(ans1);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		setChanged();
		notifyObservers("stop Double encryption and took: " +seconds+" seconds");
		return ans;
	}

	@Override
	protected byte[] decImpl(byte[] plaintext)throws IlegalKeyException {
		long startTime = System.nanoTime();
		setChanged();
		notifyObservers("start Double decryption");
		byte[] ans1= alg2.decImpl(plaintext);
		byte[] ans= alg1.decImpl(ans1);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		setChanged();
		notifyObservers("stop Double decryption and took: " +seconds+" seconds");
		return ans;
	}

	@Override
	public void beforeEnc(String keysPath)throws IOException {
		alg1.beforeEnc(keysPath);
		alg2.beforeEnc(keysPath);
	}

	@Override
	public void beforeDec(List<Integer> keys) throws IlegalKeyException{
		alg1.beforeDec(keys);
		alg2.beforeDec(keys);
	}

}
