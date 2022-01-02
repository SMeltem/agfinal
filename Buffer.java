package test;

import java.util.ArrayList;

public class Buffer {
	ArrayList<String> kelimeler = new ArrayList<String>();
	
	void ekle(String kelime) {
		kelimeler.add(kelime);
	}
	void sil() {
		kelimeler.remove(0);
	}
	String getir() {
		return kelimeler.get(0);
	}
	void goster() {
		System.out.println(kelimeler);
	}
}
