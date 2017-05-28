package encryptor;

public class Reverse extends Algorithm {

	Algorithm alg;
	public Reverse(Algorithm alg) {
		this.alg=alg;
	}
	@Override
	protected byte[] encImpl(byte[] plaintext) throws IlegalKeyException {
		long startTime = System.nanoTime();
		notifyObservers("start Reverse encryption");
		byte[] ans= alg.decImpl(plaintext);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("stop Reverse encryption and took: " +duration);
		return ans;
	}

	@Override
	protected byte[] decImpl(byte[] plaintext) throws IlegalKeyException {
		long startTime = System.nanoTime();
		notifyObservers("start Reverse decryption");
		byte[] ans= alg.encImpl(plaintext);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("stop Reverse decryption and took: " +duration);
		return ans;
	}
	@Override
	public void beforeEnc(){
		alg.beforeDec();
	}

	@Override
	public void beforeDec() {
		alg.beforeEnc();
	}

}
