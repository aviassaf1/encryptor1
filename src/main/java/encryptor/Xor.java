package encryptor;

public class Xor extends Algorithm {

	@Override
	public void enc(String path, int key) {
		byte[] ans=FileEncryptor.getFileBytes(path);
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]^key);
		}	
		FileEncryptor.saveEncFile(path,ans);	
	}

	@Override
	public void dec(String path, int key) {
		byte[] ans=FileEncryptor.getFileBytes(path);
		for (int i = 0; i < ans.length; i++) {
			ans[i]=(byte) (ans[i]^key);
		}	
		FileEncryptor.saveDecFile(path,ans);
	}

}
