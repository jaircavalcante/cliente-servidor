package br.com.clienteservidor.cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author jaircavalcante
 * @version 1.0
 */

public class Cliente extends Thread {
	
	private Socket conexao;
	
    public static void main(String[] args) {

        try {

            Socket conexao = new Socket("localhost", 1212);
            System.out.println("Conexão realizada");

            Thread thread = new Cliente(conexao);
            thread.start();

        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }
    
   
    public Cliente(Socket conexaoSocket) {
        conexao = conexaoSocket;
    }

    public void run() {
        
    	String palavraDigitada,significadoCapturado;

        try {
            System.out.print("Digite a palavra:");
            
            BufferedReader cliente = new BufferedReader(new InputStreamReader(System.in));
            
            BufferedReader servidor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
           
            palavraDigitada = cliente.readLine();
            
            DataOutputStream saidaServidor = new DataOutputStream(conexao.getOutputStream());
            
            saidaServidor.writeBytes(palavraDigitada + "\n");

            significadoCapturado = servidor.readLine();
            
            System.out.println("O significado é : " + significadoCapturado);

        } catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        } finally {
            try {
                conexao.close();
            } catch (IOException e) { 
                e.printStackTrace();
            }
        }
    }
}
