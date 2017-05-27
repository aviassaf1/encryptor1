package encryptor;

public class Multiplicayion extends Algorithm {

	@Override
	public void enc(String path, int key) {
		byte[] ans=FileEncryptor.getFileBytes(path);
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]*key);
		}	
		FileEncryptor.saveEncFile(path,ans);	
	}

	@Override
	public void dec(String path, int key) {
		byte reversKey=findreversKey(key);
		byte[] ans=FileEncryptor.getFileBytes(path);
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]^reversKey);
		}	
		FileEncryptor.saveDecFile(path,ans);
	}

	private byte findreversKey(int key) {
		byte b=Byte.MIN_VALUE;
		boolean found=false;
		while(!found){
			if(1==(key^b)){
				found=true;
			}
			else if(b==Byte.MAX_VALUE){
				found=true;
				System.err.println("there was problem to find key");
			}
			else{
				b=(byte) (b+1);
			}
		}
		return b;
	}
}
