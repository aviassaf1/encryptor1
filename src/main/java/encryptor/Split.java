package encryptor;

public class Split extends Algorithm {

	Algorithm alg1;
	Algorithm alg2;
	public Split(Algorithm alg1,Algorithm alg2) {
		this.alg1=alg1;
		this.alg2=alg2;
	}
	@Override
	protected byte[] encImpl(byte[] plaintext) throws IlegalKeyException {
		long startTime = System.nanoTime();
		byte[] ans1= alg1.encImpl(plaintext);
		byte[] ans2= alg2.encImpl(plaintext);//may be problem oop (copy plain text)
		byte[] ans=new byte[ans2.length];
		for (int i = 0; i < ans.length; i++) {
			if(i%2!=0){
				ans[i]=ans1[i];
			}
			else{
				ans[i]=ans2[i];
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("stop Split encryption and took: " +duration);
		return ans;
	}

	@Override
	protected byte[] decImpl(byte[] plaintext) throws IlegalKeyException {
		long startTime = System.nanoTime();
		byte[] ans1= alg1.decImpl(plaintext);
		byte[] ans2= alg2.decImpl(plaintext);//may be problem oop (copy plain text)
		byte[] ans=new byte[ans2.length];
		for (int i = 0; i < ans.length; i++) {
			if(i%2!=0){
				ans[i]=ans1[i];
			}
			else{
				ans[i]=ans2[i];
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("stop Split decryption and took: " +duration);
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
