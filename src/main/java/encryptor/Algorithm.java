package encryptor;

import java.util.Observable;

public abstract class Algorithm extends Observable {
	public abstract void enc(String path,int key)throws IlegalKeyException ;
	public abstract void dec(String path,int key)throws IlegalKeyException ;
}
