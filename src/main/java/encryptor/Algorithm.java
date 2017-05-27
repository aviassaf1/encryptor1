package encryptor;

import java.util.Observable;

public abstract class Algorithm extends Observable {
	public abstract byte[] enc(String path,int key, byte[] plaintext)throws IlegalKeyException ;
	public abstract byte[] dec(String path,int key, byte[] plaintext)throws IlegalKeyException ;
}
