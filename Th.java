package test;

public class Th implements Runnable {

	private Thread th;
	private int paketsayisi; 
	public Th(int x) {
		paketsayisi = x;
	}
	
	@Override
	public void run() {
		try {
			
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i<paketsayisi; i++) {
			System.out.println(i+". paket ");	
		}
	}
	
	public void start() {
		System.out.println("Thread basladi.");
		if (th == null) {
			th = new Thread(this);
			th.start();
		} 
		
		
	}
	
}
