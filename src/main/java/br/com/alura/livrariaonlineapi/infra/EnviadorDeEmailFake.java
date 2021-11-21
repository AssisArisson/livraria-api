package br.com.alura.livrariaonlineapi.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "test"})
public class EnviadorDeEmailFake implements EnviadorDeEmail{


    @Async
    public void enviarEmail(String destinatario, String assunto, String mensagem){

        System.out.println("ENVIANDO EMAIL TESTE DESENVOLVIMENTO");
        System.out.println("Destinatario: " +destinatario);
        System.out.println("Assunto: " +assunto);
        System.out.println("Mensagem: " +mensagem);


    }
}
