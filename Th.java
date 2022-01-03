package test;

public class Th implements Runnable {
	
	private Thread th;
	private String routerName;
	private int paketsayisi; 
	public Th(String name,int x) {
		routerName = name;
		paketsayisi = x;
		System.out.println("Kimden: "+routerName);
	}
	
	@Override
	public void run() {
		try {
			
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i<paketsayisi; i++) {
			if (routerName.equals("Receiver")) {
				System.out.println(i+". paket : " + routerName+ " dan alindi. ");
			}
			else {
				System.out.println(i+". paket : " + routerName+ " den gonderildi. ");
			}
		}
	}
	
	public void start() {
		System.out.println("Thread basladi "+ routerName + "icin");
		if (th == null) {
			th = new Thread(this, routerName);
			th.start();
		} 
		
		
	}
	
}
