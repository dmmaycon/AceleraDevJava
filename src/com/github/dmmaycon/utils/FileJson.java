package com.github.dmmaycon.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class FileJson {
	
	private String name = "answer.json"; // default name
	
	private int numero_casas;
	private String token;
	private String cifrado;
	private String decifrado;
	private String resumo_criptografico;
	
	public int getNumero_casas() {
		return numero_casas;
	}
	public void setNumero_casas(int numero_casas) {
		this.numero_casas = numero_casas;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCifrado() {
		return cifrado;
	}
	public void setCifrado(String cifrado) {
		this.cifrado = cifrado;
	}
	public String getDecifrado() {
		return decifrado;
	}
	public void setDecifrado(String decifrado) {
		this.decifrado = decifrado;
	}
	public String getResumo_criptografico() {
		return resumo_criptografico;
	}
	public void setResumo_criptografico(String resumo_criptografico) {
		this.resumo_criptografico = resumo_criptografico;
	}
	
	
	public void save() throws IOException {
		Gson gson = new Gson();
		FileWriter writter = new FileWriter(name);
		try(BufferedWriter buffer = new BufferedWriter(writter)) {
			  buffer.write(gson.toJson(this));
		}
	}
	
}
