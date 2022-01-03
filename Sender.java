package test;

import java.io.*;
import java.net.*;
import java.util.*;




public class Sender extends Thread {
	
	
	private static InetAddress host;
	
	private static final int PORT = 7777;
	

	public static void main(String[] args) {
		
		//Hata bloðu kullanarak oluþabilecek hatalara karþý uyarý sistemi oluþturuyoruz.
		try {
			//InetAddres kütüphanesindeki getlocalhost methodunu kullanarak yerel að adresimizi deðiþkene atýyoruz.
			host = InetAddress.getLocalHost();
			System.out.println("Baðlantý Saðlandý");
					
			

		} catch (Exception e) {
			System.out.println("Host ID bulunamadý !");
			System.exit(1); 
		}
		//Server baðlanýtýsý için fonksiyonu çaðýrýyoruz.
		accessServer();
	}
	
	private static void accessServer() {
		
		Socket socket = null;
		
		int attempt = 0;
		int counter = 0;
		int number;
		String message,str2,response,str;

		
		try {
			
			socket =  new Socket(host,PORT); //Socket sýnýfýnýn yapýcý methodunu kullanarak baðlantý oluþturuyoruz.
			
			Scanner in = new Scanner(socket.getInputStream());
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			
			System.out.println("How many packets? ");
			
			Scanner userEntry = new Scanner(System.in); //Kullanýcýdan kaç paket yollanacaðý bilgisini alýyoruz
			
			response = userEntry.nextLine(); // Response deðiþkeninin içine atýyoruz
			number = Integer.parseInt(response); //Burada da response deðiþkenine tür dönüþümü yaparak int ifadeye çeviriyoruz.
			
			long startTime = System.nanoTime();
			
			do { 
				
				message = "PCK";
				out.println(message + counter);
				//Her yapýlan iþlemde attempt deðerini arttýrýyoruz bu deðer bizim toplam yaptýðýmýz istek sayýsýdýr
				attempt++;
				
				
				str = in.nextLine();
				str2 = str.substring(0,3);
				
				while(!str2.equals("ACK")) { //
					
					System.out.println(message + counter + "Resending");
					out.println(message+counter);
					attempt++;
					str = in.nextLine();
					str2 = str.substring(0, 3);
					
				} 
				System.out.println(str + " received from receiver successfully");
	            counter++;
			}while(counter < number);
			
			long endTime = System.nanoTime();
			
			System.out.println("Total number of try: " + attempt);
			
			System.out.println("Time taken to send all packets: "+ (endTime - startTime) + " nano seconds.");
			
			out.println("***CLOSE***"); //Servera data yolluyoruz bu yollanan datanýn diðer classlarda da ayný olmasý gerekiyor
		} 
		catch (IOException ioEx) {
			ioEx.printStackTrace();
		} 
		finally {
			
			try {
				System.out.println("\n* Closing connections (Sender side)*");
				socket.close();
			} catch (IOException e) {
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
					
		}	
	}
		
}	

