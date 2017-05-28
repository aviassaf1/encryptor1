package algorithms;

import java.io.IOException;
import java.util.List;

import algorithms.exceptions.IlegalKeyException;

public class Reverse extends Algorithm {

	Algorithm alg;
	public Reverse(Algorithm alg) {
		this.alg=alg;
	}
	@Override
	protected byte[] encImpl(byte[] plaintext)throws IlegalKeyException {
		long startTime = System.nanoTime();
		setChanged();
		notifyObservers("start Reverse encryption");
		byte[] ans= alg.decImpl(plaintext);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		setChanged();
		notifyObservers("stop Reverse encryption and took: " +seconds+" seconds");
		return ans;
	}

	@Override
	protected byte[] decImpl(byte[] plaintext)throws IlegalKeyException {
		long startTime = System.nanoTime();
		setChanged();
		notifyObservers("start Reverse decryption");
		byte[] ans= alg.encImpl(plaintext);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		setChanged();
		notifyObservers("stop Reverse decryption and took: " +seconds+" seconds");
		return ans;
	}
	@Override
	public void beforeEnc(String keysPath)throws IOException{
		alg.beforeEnc(keysPath);
	}

	@Override
	public void beforeDec(List<Integer> keys)throws IlegalKeyException {
		alg.beforeDec(keys);
	}

}
