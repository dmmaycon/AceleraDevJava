package com.github.dmmaycon.main;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.ResourceBundle;

import com.github.dmmaycon.classes.CaesarCipher;
import com.github.dmmaycon.utils.FileJson;
import com.github.dmmaycon.utils.MyClient;
import com.google.gson.Gson;

/**
 * @author dmmaycon
 * @email mayconmoraes.dm@gmail.com
 */
public class Main {
	
	public static void main(String[] args) {		
		try {
			ResourceBundle rs = ResourceBundle.getBundle("secrets");
			String token = rs.getString("token");
			
			Gson gson = new Gson();
			
			System.out.println("Request para a API");
			String json = MyClient.getRequest("https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=" + token);
			
			System.out.println("Transformando o retorno em FileJson na memoria");
			FileJson file = gson.fromJson(json, FileJson.class); 
			
			System.out.println("Decifrando a mensagem e adcionando no FileJson");
			CaesarCipher cc  = new CaesarCipher(file.getCifrado());
			file.setDecifrado(cc.decrypt(file.getNumero_casas()));
			
			System.out.println("Mensagem decifrada: " + file.getDecifrado());
			
			System.out.println("Gerando o hash sha1 da frase decifrada");
			MessageDigest sec = MessageDigest.getInstance("SHA1");
			StringBuilder resumoStr = new StringBuilder();
			// byte to hex
			for (byte b : sec.digest(file.getDecifrado().getBytes("UTF-8"))) {
				resumoStr.append(String.format("%02X", 0xff & b));
			}
			
			file.setResumo_criptografico(resumoStr.toString().toLowerCase());			
			System.out.println(file.getResumo_criptografico());
			
			System.out.println("Salvando o arquivo final");
			file.save();
			
			System.out.println("Enviando o arquivo via multipar/data");
			File fileFinal = new File("answer.json");
			String end = MyClient.sendRequest("https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=" + token, fileFinal);
			
			System.out.println("Retorno final: " + end);

			
		} catch (IOException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
