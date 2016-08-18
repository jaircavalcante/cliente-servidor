package br.com.clienteservidor.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jaircavalcante
 * @version 1.0
 */

public class Servidor extends Thread {

	private Socket conexao;

	public static void main(String[] args) throws IOException {

		ServerSocket socket = null;

		try {
			System.out.println("Aguardando Resposta do Cliente...");
			socket = new ServerSocket(1212);

			while (true) {

				Socket conexao = socket.accept();
				System.out.println("Conexão realizada!");
				Thread thread = new Servidor(conexao);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
	}

	public Servidor(Socket conexaoSocket) {
		conexao = conexaoSocket;
	}

	public void run() {

		String palavraDigitada, significado;

		Map<String, String> dicionario = new HashMap<String, String>();

		dicionario.put("monoprogramado", "um programa em execução");
		dicionario.put("processo", "programa em execução");
		dicionario.put("sistema operacional", "gerencia os recursos da máquina");
		dicionario.put("time sharing", "tempo compartilhado");
		dicionario.put("pc", "instrução a ser executada - PROGRAM COUNTER");
		dicionario.put("sjf",
				" é um algoritmo de escalonamento que executa, dentre processos igualmente importantes, o mais curto primeiro.");

		try {

			BufferedReader cliente = new BufferedReader(new InputStreamReader(conexao.getInputStream()));

			DataOutputStream saidaServidor = new DataOutputStream(conexao.getOutputStream());

			palavraDigitada = cliente.readLine();

			if (dicionario.containsKey(palavraDigitada)) {
				significado = dicionario.get(palavraDigitada) + "\n";

				System.out.println("Seu Significado é:" + palavraDigitada + " enviado para o cliente");

				saidaServidor.writeBytes(significado);
			} else {

				System.out.println("Não existe Significado no Dicionário para esta palavra.");
			}

		} catch (IOException e) {
			System.out.println("IOException " + e);
		}
	}
}
