package test;

import java.io.*;
import java.net.*;
import java.util.*;

public class Router2 implements Runnable {
	private String routerName;
	private ServerSocket routerSocket;
	private InetAddress host;
	private static final int port = 77777; //Bu port numarasi olmas�n�n nedeni 6**** sonlanmasi rezerve edilen portlarin.
	private static Socket link2 = null;
	public Router2(String name) {
		routerName = name;
		System.out.println("Kimden geldi: "+routerName);
	}
	
	@Override
	public void run() {
		try {
			host = InetAddress.getLocalHost();
			routerSocket = new ServerSocket(port);
			link2 = new Socket(host,port);
			link2.setTcpNoDelay(true);
		}
		catch (UnknownHostException e) {
		e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			
			handleClient();
			Thread.sleep(1);
		}
		catch (InterruptedException e) { 
			e.printStackTrace();
		}
		finally {
			try {
				link2.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
	}
	
	

	private void handleClient() {
		Socket link = null;
		String str2 = null;
		try {
			
			link = routerSocket.accept();
			link.setTcpNoDelay(true);
			Scanner input = new Scanner(link.getInputStream());
			PrintWriter output = new PrintWriter(link.getOutputStream(),true);
			String message = input.nextLine();
			Scanner input2 = new Scanner(link2.getInputStream());
			PrintWriter output2 = new PrintWriter(link.getOutputStream(),true);
			//String[] metin = message.split("[\\s]");
			
			while(!message.equals("***CLOSE***")) {
				Random rndgen = new Random();
				int randint = rndgen.nextInt(100);
				if (randint > -1) { //-1 yapinca tum paketler sorunsuz iletiyor.Deger arttikca tekrar paket isteniyor.
					output2.println(message);
					String str = input2.nextLine();
					output.println(str);
				}
				else {
					output.println(str2);
				}
				message = input.nextLine();
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				link.close();
				link2.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		
		
	}
	public static void main(String[] args) {
		//Scanner sc = new Scanner(System.in);
		//int sayi = sc.nextInt();
		Th th = new Th("Router2",10);
		th.start();
	}
}

