package encryptor;

public class Multiplication extends Algorithm {

	@Override
	public void enc(String path, int key) {
		long startTime = System.nanoTime();
		notifyObservers("start Multiplication encryption");
		byte[] ans;
		try {
			ans = FileEncryptor.getFileBytes(path);
			for (int i = 0; i < ans.length; i++) {
				ans[i]=(byte) (ans[i]*key);
			}	
			FileEncryptor.saveEncFile(path,ans);
		} catch (Exception e) {
			System.err.println("there was a problem to get "+path+" please try another path");
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("finish Multiplication encryption and took: " +duration);
	}

	@Override
	public void dec(String path, int key) throws IlegalKeyException {
		long startTime = System.nanoTime();
		notifyObservers("start Multiplication decryption");
		byte reversKey=findreversKey(key);
		byte[] ans;
		try {
			ans = FileEncryptor.getFileBytes(path);
			for (int i = 0; i < ans.length; i++) {
				ans[i]=(byte) (ans[i]*reversKey);
			}	
			FileEncryptor.saveDecFile(path,ans);
		} catch (Exception e) {
			System.err.println("there was a problem to get "+path+" please try another path");
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		notifyObservers("finish Multiplication decryption and took: " +duration);
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
