package encryptor;

import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cmd implements Observer{

	public static void main(String[] args) {
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line;
		boolean chooseEncOrDec=false;
		boolean enc=false;
		boolean done=false;
		while(!done){
			try {
				System.out.println("hello, please choose enryption(E) or decription(D)");
				while(!chooseEncOrDec){
					line=buffer.readLine();
					if(line.equals("E")){
						chooseEncOrDec=true;
						enc=true;
					}
					else if(line.equals("D")){
						chooseEncOrDec=true;
					}
					else{
						System.out.println("please choose only one of the options enryption(E) or decription(D)");
					}
				}
				int algoNum=chooseAlgorithm();
				
				///////////////////////////////////////////////////////////////
				Cmd cmd=new Cmd(); 
				Algorithm alg=getAlgorithmFromNum(algoNum);
				alg.addObserver(cmd);
				if(enc){
					try {
				
						enc(alg);
						
					} catch (IlegalKeyException e) {
						System.err.println("There was problem with the key");
					}catch (IOException e) {
						System.err.println("There was problem with the path");
					}
				}
				else{
						try {
							try {
								System.out.println("choosing the key file" );
								String keyPath=getFilePath(false);
								List<Integer> keys=FileEncryptor.getKeys(keyPath);
								alg.beforeDec(keys);
								
								dec(alg);
								
							} catch (IOException e) {
								System.err.println("there was a problem with the path");
							}
						} catch (IlegalKeyException e) {
							System.err.println("The key is illegal");
							
						}
				}
				done=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void dec(final Algorithm alg) throws IOException, IlegalKeyException {
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line;
		boolean chooseFileOrDirectory=false;
		boolean isDirectory=false;
		System.out.println("please choose enryption\\decription file(A) or Directory(B):");
		while(!chooseFileOrDirectory){
			line=buffer.readLine();
			if(line.equals("A")){
				chooseFileOrDirectory=true;
			}
			else if(line.equals("B")){
				chooseFileOrDirectory=true;
				isDirectory=true;
			}
			else{
				System.out.println("please choose only one of the file(A) or Directory(B)");
			}
		}
		
		System.out.println("choosing the file\\folder you would like to decrypt" );
		String path=getFilePath(isDirectory);
		if(!isDirectory){
			decOneFile(alg, path);
		}else{
			decFolder(alg, path);
		}
	}

	private static void decFolder(final Algorithm alg, String path) throws IOException {
		final List<File>GoodFiles=new LinkedList<File>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (!listOfFiles[i].isDirectory()) {
				GoodFiles.add(listOfFiles[i]);
		    }
		}
		File theDir = new File(path+"\\decrypted");
		if (!theDir.exists()){
			theDir.mkdir();
		}
		
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line;
		boolean chooseAsyncOrSync=false;
		boolean sync=false;
		System.out.println("please choose Async(A) or Sync(B):");
		while(!chooseAsyncOrSync){
			line=buffer.readLine();
			if(line.equals("A")){
				chooseAsyncOrSync=true;
			}
			else if(line.equals("B")){
				chooseAsyncOrSync=true;
				sync=true;
			}
			else{
				System.out.println("please choose only one of the Async(A) or Sync(B)");
			}
		}
		
		if(sync){
			decFolderSync(alg, GoodFiles);
		}else{
			decFolderAsync(alg, GoodFiles);
		}
	}

	private static void decFolderAsync(final Algorithm alg, final List<File> GoodFiles) {
		ExecutorService exec = Executors.newFixedThreadPool(GoodFiles.size());
		long startTime = System.nanoTime();
		for (int i = 0; i < GoodFiles.size(); i++) {
			exec.execute(new Runnable() {
				public void run() {
					try {
						FileEncryptor.saveDecFiles(GoodFiles.get((int) Thread.currentThread().getId()%GoodFiles.size()),
								alg.dec(FileEncryptor.getFileBytes(
										GoodFiles.get((int) Thread.currentThread().getId()%GoodFiles.size()).getPath())
										));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (IlegalKeyException e) {
						System.err.println("There was problem with the key");
					}
				}
			});
		}
		exec.shutdown();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		System.err.println("finish async decryption and took: " +seconds+" seconds");
	}

	private static void decFolderSync(final Algorithm alg, final List<File> GoodFiles) {
		long startTime = System.nanoTime();
		for (int i = 0; i < GoodFiles.size(); i++) {
			try {
				FileEncryptor.saveDecFiles(GoodFiles.get(i),
						alg.dec(FileEncryptor.getFileBytes(GoodFiles.get(i).getPath()))
						);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IlegalKeyException e) {
				System.err.println("There was problem with the key");
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		System.err.println("finish sync decryption and took: " +seconds+" seconds");
	}

	
	private static void decOneFile(final Algorithm alg, String path) throws IOException, IlegalKeyException {
		byte[]plaintext = FileEncryptor.getFileBytes(path);
		byte[]ans= alg.dec(plaintext);
		FileEncryptor.saveDecFile(path,ans);
	}

	private static void enc(final Algorithm alg) throws IOException, IlegalKeyException {
		
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line;
		boolean chooseFileOrDirectory=false;
		boolean isDirectory=false;
		System.out.println("please choose enryption\\decription file(A) or Directory(B):");
		while(!chooseFileOrDirectory){
			line=buffer.readLine();
			if(line.equals("A")){
				chooseFileOrDirectory=true;
			}
			else if(line.equals("B")){
				chooseFileOrDirectory=true;
				isDirectory=true;
			}
			else{
				System.out.println("please choose only one of the file(A) or Directory(B)");
			}
		}
		System.out.println("choosing the file\\folder you would like to encrypt" );
		final String path=getFilePath(isDirectory);
		if(!isDirectory){
			encOneFile(alg, path);
		}else{
			encFolder(alg, path);
		}
	}

	private static void encFolder(final Algorithm alg, final String path) throws IOException {
		final List<File>GoodFiles=new LinkedList<File>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (!listOfFiles[i].isDirectory()) {
				GoodFiles.add(listOfFiles[i]);
		    }
		}
		File theDir = new File(path+"\\encrypted");
		if (!theDir.exists()){
			theDir.mkdir();
		}
		alg.beforeEnc(path+"\\encrypted");
		
		
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line;
		boolean chooseAsyncOrSync=false;
		boolean sync=false;
		System.out.println("please choose Async(A) or Sync(B):");
		while(!chooseAsyncOrSync){
			line=buffer.readLine();
			if(line.equals("A")){
				chooseAsyncOrSync=true;
			}
			else if(line.equals("B")){
				chooseAsyncOrSync=true;
				sync=true;
			}
			else{
				System.out.println("please choose only one of the Async(A) or Sync(B)");
			}
		}
		
		if(sync){
			encFolderSync(alg, GoodFiles);
		}else{
			encFolderAsync(alg, GoodFiles);
		}
	}

	private static void encFolderAsync(final Algorithm alg, final List<File> GoodFiles) {
		ExecutorService exec = Executors.newFixedThreadPool(GoodFiles.size());
		long startTime = System.nanoTime();
		for (int i = 0; i < GoodFiles.size(); i++) {
			exec.execute(new Runnable() {
				public void run() {
					try {
						FileEncryptor.saveEncFiles(GoodFiles.get((int) Thread.currentThread().getId()%GoodFiles.size()),
								alg.enc(FileEncryptor.getFileBytes(
										GoodFiles.get((int) Thread.currentThread().getId()%GoodFiles.size()).getPath())
										));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (IlegalKeyException e) {
						System.err.println("There was problem with the key");
					}
				}
			});
		}
		exec.shutdown();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		System.err.println("finish async encryption and took: " +seconds+" seconds");
	}
	
	private static void encFolderSync(final Algorithm alg, final List<File> GoodFiles) {
		long startTime = System.nanoTime();
		for (int i = 0; i < GoodFiles.size(); i++) {
			try {
				FileEncryptor.saveEncFiles(GoodFiles.get(i),
						alg.enc(FileEncryptor.getFileBytes(GoodFiles.get(i).getPath()))
						);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IlegalKeyException e) {
				System.err.println("There was problem with the key");
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		double seconds = (double)duration / 1000000000.0;
		System.err.println("finish sync encryption and took: " +seconds+" seconds");
	}

	private static void encOneFile(final Algorithm alg, final String path) throws IOException, IlegalKeyException {
		System.out.println("choosing the key file" );
		String keyPath=getFilePath(true);
		alg.beforeEnc(keyPath);
		
		byte[]plaintext = FileEncryptor.getFileBytes(path);
		byte[]ans= alg.enc(plaintext);
		FileEncryptor.saveEncFile(path,ans);
	}

	private static Algorithm getAlgorithmFromNum(int algoNum) {
		switch (algoNum) {
		case 1:
			return new Ceasar();
		case 2:
			return new Xor();
		case 3:
			return new Multiplication();
		case 4:
			System.out.println("now choose the first ans second algorithm");
			System.out.println("choosing the first algorithm:");
			Algorithm alg1=getAlgorithmFromNum(chooseAlgorithm());
			System.out.println("choosing the second algorithm:");
			Algorithm alg2=getAlgorithmFromNum(chooseAlgorithm());
			return new Double(alg1, alg2);
		case 5:
			System.out.println("choosing the reverse algorithm:");
			Algorithm alg=getAlgorithmFromNum(chooseAlgorithm());
			return new Reverse(alg);
		case 6:
			System.out.println("now choose the first ans second algorithm");
			System.out.println("choosing the first algorithm:");
			Algorithm alg3=getAlgorithmFromNum(chooseAlgorithm());
			System.out.println("choosing the second algorithm:");
			Algorithm alg4=getAlgorithmFromNum(chooseAlgorithm());
			return new Split(alg3, alg4);
		default:
			return new Ceasar();
		}
	}

	private static int chooseAlgorithm() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("please choose the encryption  algorithm:");	
		System.out.println("(1) Ceaser, (2) Xor, (3) Multiplicayion, (4) Double, (5) Reverse, (6) Split");
		boolean enteredNum=false;
		int num=0;
		while(!enteredNum){
			try {
				num = Integer.parseInt(br.readLine());
				if(num<1||num>6){
					System.out.println("please enter number (1-6):");
				}
				else{
					enteredNum=true;
				}
			} catch (Exception e) {
				System.out.println("please enter number (1-6):");
			}
		}
		return num;
	}

	private static String getFilePath(Boolean isDirectory) {
		boolean pathExist=false;
		String path="";
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("please enter file path here:");
		while(!pathExist){
			try {
				path=buffer.readLine();
				File f = new File(path);
				if(f.exists() && (f.isDirectory()==isDirectory)) { 
				   pathExist=true;
				}
				else{
					if(isDirectory){
						System.out.println("the path you enterd is not a correct folder path, please try again:");
					}
					else{
						System.out.println("the path you enterd is not a correct file path, please try again:");
					}
				}
			} catch (IOException e) {
				if(isDirectory){
					System.out.println("the path you enterd is not a correct folder path, please try again:");
				}
				else{
					System.out.println("the path you enterd is not a correct file path, please try again:");
				}
			}
		}
		return path;
	}

	public void update(Observable o, Object arg) {
		System.out.println(arg);
	}
	
}
