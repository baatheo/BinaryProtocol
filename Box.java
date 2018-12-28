package lab;

import static java.lang.Math.*;

import java.io.UnsupportedEncodingException;

public class Box {
	final int rozmiar_liczb = 3;
	// final long rozmiar = (long) pow(2,63);
	final long rozmiar = 8;
	public char operation;
	public int status;
	public long pierwsza;
	public long druga;
	public int nrs;
	boolean rozszerzenie;
	
	public Box() {
		this.operation='0';
		this.status=0;
		this.nrs=0;
		this.rozszerzenie=false;
		this.pierwsza=0;
		this.druga=0;	
	}
	public Box(char operation, int status, int nrs, boolean rozszerzenie, long pierwsza) {
		this.operation=operation;
		this.status=status;
		this.nrs=nrs;
		this.rozszerzenie=rozszerzenie;
		this.pierwsza=pierwsza;
		this.druga=0;
	}
	
	public Box(char operation, int status, int nrs, boolean rozszerzenie, long pierwsza, long druga) {
		this.operation=operation;
		this.status=status;
		this.nrs=nrs;
		this.rozszerzenie=rozszerzenie;
		this.pierwsza=pierwsza;
		this.druga=druga;
	}
	public void wyswietl() {
		System.out.println("operacja: "+this.operation);
		System.out.println("status: "+this.status);
		System.out.println("numer sesji: "+this.nrs);
		System.out.println("rozszerzenie: "+this.rozszerzenie);
		System.out.println("pierwsza liczba: "+this.pierwsza);
		System.out.println("druga liczba: "+this.druga);
		System.out.println("-------------\n");
	}
	
	public boolean equals(int x) {
		if(this.nrs==x) return true;
		else return false;
	}
	
	public long silnia(long x) {
		long y=1;
		for(int i=2; i<x+1; i++) {
			y=y*i;
		}
		if(y>rozmiar) {
			System.out.println("za duza liczba");
			return 0;
		}
		else return y;
	}

	public static String IntToBin(long l,int x) {
		String temp="";
		temp=Long.toBinaryString(l);
		x=x-temp.length();
		String p="";
		for(int i=0; i<x; i++) {
			p=p+'0';
		}
		p=p+temp;		
		return p;
	}
	public static int BinToInt(String p) {
		//System.out.println("BinToInt:\np=\""+p+"\"");
		int x=Integer.parseInt(p,2);
		return x;
	}
	
	public byte[] pakuj() {
		String temp="";
		String pakiet;
		pakiet="";		
		if(operation=='*') {temp="00"; pakiet=pakiet+temp;}
		else if (operation=='/') {temp="01"; pakiet=pakiet+temp;}
		else if (operation=='+') {temp="10"; pakiet=pakiet+temp;}
		else if (operation=='-') {temp="11"; pakiet=pakiet+temp;}
		else if (operation=='!') {temp="11"; pakiet=pakiet+temp;}
			
		pakiet=pakiet+IntToBin(status,3);
		pakiet=pakiet+IntToBin(nrs,7);
		if(rozszerzenie) {pakiet=pakiet+'1';}
		else {pakiet=pakiet+'0';} 
		pakiet=pakiet+IntToBin(pierwsza,rozmiar_liczb);
		
		if(rozszerzenie==true) {
			pakiet=pakiet+IntToBin(druga,rozmiar_liczb);
		}	
		byte[] bytes = pakiet.getBytes();
		//System.out.println("pakiet: " + pakiet);
		return bytes;
	}
	
	public Box rozpakuj(byte[] bytes) throws UnsupportedEncodingException {
		Box pakiet=new Box();
		String p = new String(bytes);
		String temp;
		temp="";
		for(int i=0; i<2; i++) {
			temp=temp+p.charAt(i);
		}
		if(temp.equals("00")) {pakiet.operation='*';}
		else if (temp.equals("01")) {pakiet.operation='/';}
		else if (temp.equals("10")) {pakiet.operation='+';}
		else if (temp.equals("11")) {pakiet.operation='-';}
		temp="";
		for(int i=2; i<5; i++) {
			temp=temp+p.charAt(i);
		}
		pakiet.status=BinToInt(temp);
		temp="";
		for(int i=5; i<12; i++) {
			temp=temp+p.charAt(i);
		}
		pakiet.nrs=BinToInt(temp);
		if(p.charAt(12)=='1') {pakiet.rozszerzenie=true;}
		else {pakiet.rozszerzenie=false;}
		temp="";
		for(int i=13; i<(13+rozmiar_liczb);i++) {
			temp=temp+p.charAt(i);
		}
		pakiet.pierwsza=BinToInt(temp);
		temp="";
		if(pakiet.rozszerzenie == true) {
			for(int i=(13+rozmiar_liczb); i<(13+2*rozmiar_liczb); i++) {
				temp=temp+p.charAt(i);
			}
			pakiet.druga=BinToInt(temp);
		}

		//System.out.println("p: " + p);
		return pakiet;
	}
}
