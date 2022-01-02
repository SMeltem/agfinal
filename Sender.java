package test;

import java.io.*;
import java.net.*;
import java.util.*;




public class Sender extends Thread {
	
	
	private static InetAddress host;
	
	private static final int PORT = 1241;
	

	public static void main(String[] args) {
		
		//Hata blo�u kullanarak olu�abilecek hatalara kar�� uyar� sistemi olu�turuyoruz.
		try {
			//InetAddres k�t�phanesindeki getlocalhost methodunu kullanarak yerel a� adresimizi de�i�kene at�yoruz.
			host = InetAddress.getLocalHost();
			System.out.println("Ba�lant� Sa�land�");
					
			

		} catch (Exception e) {
			System.out.println("Host ID bulunamad� !");
			System.exit(1); 
		}
		//Server ba�lan�t�s� i�in fonksiyonu �a��r�yoruz.
		accessServer();
	}
	
	private static void accessServer() {
		
		Socket socket = null;
		
		int attempt = 0;
		int counter = 0;
		int number;
		String message,str2,response,str;

		
		try {
			
			socket =  new Socket(host,PORT); //Socket s�n�f�n�n yap�c� methodunu kullanarak ba�lant� olu�turuyoruz.
			
			Scanner in = new Scanner(socket.getInputStream());
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			
			System.out.println("How many packets? ");
			
			Scanner userEntry = new Scanner(System.in); //Kullan�c�dan ka� paket yollanaca�� bilgisini al�yoruz
			
			response = userEntry.nextLine(); // Response de�i�keninin i�ine at�yoruz
			number = Integer.parseInt(response); //Burada da response de�i�kenine t�r d�n���m� yaparak int ifadeye �eviriyoruz.
			
			long startTime = System.nanoTime();
			
			do { 
				
				message = "PCK";
				out.println(message + counter);
				//Her yap�lan i�lemde attempt de�erini artt�r�yoruz bu de�er bizim toplam yapt���m�z istek say�s�d�r
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
			
			out.println("***CLOSE***"); //Servera data yolluyoruz bu yollanan datan�n di�er classlarda da ayn� olmas� gerekiyor
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

