package org.atividade8;

import java.io.IOException;
import java.util.Scanner;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;

public class main {

    public static void main(String[] args) throws OllamaBaseException, IOException, InterruptedException {
        String host = "http://127.0.0.1:11434/";
        String modelName = "qwen3:1.7b";

        // Scanner para entrada do usuário
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite sua pergunta: ");
        String userInput = scanner.nextLine();

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(60);

        OllamaResult result = ollamaAPI.generate(modelName, userInput, null);

        System.out.println("Resposta do modelo:");
        System.out.println(result.getResponse());

        scanner.close();
    }
}
