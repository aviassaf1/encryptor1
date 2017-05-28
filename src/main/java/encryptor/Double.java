package encryptor;

public class Double extends Algorithm {

	Algorithm alg1;
	Algorithm alg2;
	public Double(Algorithm alg1,Algorithm alg2) {
		this.alg1=alg1;
		this.alg2=alg2;
	}
	@Override
	protected byte[] encImpl(byte[] plaintext) throws IlegalKeyException {
		long startTime = System.nanoTime();
		byte[] ans1= alg1.encImpl(plaintext);
		byte[] ans= alg2.encImpl(ans1);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("stop Double encryption and took: " +duration);
		return ans;
	}

	@Override
	protected byte[] decImpl(byte[] plaintext) throws IlegalKeyException {
		long startTime = System.nanoTime();
		byte[] ans1= alg2.decImpl(plaintext);
		byte[] ans= alg1.decImpl(ans1);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("stop Double decryption and took: " +duration);
		return ans;
	}

	@Override
	protected void beforeEnc() {
		alg1.beforeEnc();
		alg2.beforeEnc();
	}

	@Override
	protected void beforeDec() {
		alg1.beforeDec();
		alg2.beforeDec();
	}

}
