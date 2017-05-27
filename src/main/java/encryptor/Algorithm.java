package encryptor;

public abstract class Algorithm {
	public abstract void enc(String path,int key) ;
	public abstract void dec(String path,int key) ;
}
