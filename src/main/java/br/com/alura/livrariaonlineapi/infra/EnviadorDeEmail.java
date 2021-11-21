package br.com.alura.livrariaonlineapi.infra;

public interface EnviadorDeEmail {

    void enviarEmail(String destinatario, String assunto, String mensagem);

}
