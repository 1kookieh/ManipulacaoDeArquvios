package atividade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Principal {

    private final String nomeDoArquivo;

    public Principal(String nomeArquivo) {
        this.nomeDoArquivo = nomeArquivo;
    }

    public void inserirDados(String registro) {
        File fArquivo = new File(this.nomeDoArquivo);
        try (FileWriter fwArquivo = new FileWriter(fArquivo, true);
             BufferedWriter bw = new BufferedWriter(fwArquivo)) {
            bw.write(registro + System.lineSeparator());
            System.out.println("Registro adicionado com sucesso...");
        } catch (IOException e) {
            System.err.println("Erro ao inserir linhas no arquivo: " + fArquivo + " -> " + e.getMessage());
        }
    }

    public void listarDados() {
        File arquivo = new File(this.nomeDoArquivo);
        try (Scanner lendoArquivo = new Scanner(arquivo)) {
            while (lendoArquivo.hasNextLine()) {
                this.processandoLinha(lendoArquivo.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro: arquivo nao existe. " + arquivo.getPath());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao listar: " + e.getMessage());
        }
    }

    public void buscarDados(String nomeBusca) {
        File arquivo = new File(this.nomeDoArquivo);
        boolean encontrado = false;
        try (Scanner lendoArquivo = new Scanner(arquivo)) {
            while (lendoArquivo.hasNextLine()) {
                String linha = lendoArquivo.nextLine();
                if (linha != null && !linha.trim().isEmpty()) {
                    String[] campos = linha.split(":");
                    if (campos.length >= 2) {
                        String nome = campos[0].trim();
                        String telefone = campos[1].trim();
                        if (nome.equalsIgnoreCase(nomeBusca.trim())) {
                            Contato c = new Contato(nome, telefone);
                            System.out.println(c);
                            encontrado = true;
                        }
                    } else {
                        System.err.println("[ERRO] Linha com formato inválido no arquivo: " + linha);
                    }
                }
            }
            if (!encontrado) {
                System.out.println("Contato não encontrado: " + nomeBusca);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro: arquivo nao existe. " + arquivo.getPath());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao buscar: " + e.getMessage());
        }
    }

    private void processandoLinha(String linha) {
        if (linha != null && !linha.trim().isEmpty()) {
            try {
                String[] campos = linha.split(":");
                if (campos.length >= 2) {
                    Contato c = new Contato(campos[0].trim(), campos[1].trim());
                    System.out.println(c);
                    System.out.println("--------------------------------------");
                } else {
                    System.err.println("[ERRO] Linha com formato inválido no arquivo: " + linha);
                }
            } catch (Exception e) {
                System.err.println("[ERRO INESPERADO] Falha ao processar linha: " + linha);
            }
        }
    }

    public void menu() {
        Scanner teclado = new Scanner(System.in);
        int op = 0;
        do {
            System.out.println("..:: Trabalhando com Arquivos Texto ::..");
            System.out.println("1 - Inserir linha");
            System.out.println("2 - Listar todo arquivo");
            System.out.println("3 - Sair");
            System.out.println("4 - Buscar por Nome");
            System.out.print("Entre com uma opcao: ");
            try {
                op = Integer.parseInt(teclado.nextLine().trim());
            } catch (Exception e) {
                op = -1;
            }
            switch (op) {
                case 1: {
                    System.out.println("Entre com os dados:");
                    System.out.print("Nome: ");
                    String nome = teclado.nextLine();
                    System.out.print("Fone: ");
                    String telefone = teclado.nextLine();
                    this.inserirDados(nome + ":" + telefone);
                    break;
                }
                case 2:
                    this.listarDados();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                case 4: {
                    System.out.print("Nome para buscar: ");
                    String nomeBusca = teclado.nextLine();
                    this.buscarDados(nomeBusca);
                    break;
                }
                default:
                    System.out.println("Opção inválida!");
            }
            System.out.println();
        } while (op != 3);
        teclado.close();
    }

    public static void main(String[] args) {
        Principal p = new Principal("agenda.txt");
        p.menu();
    }
}
