package br.com.alura.livrariaonlineapi.service;

import br.com.alura.livrariaonlineapi.dto.UsuarioInDTO;
import br.com.alura.livrariaonlineapi.dto.UsuarioOutDTO;
import br.com.alura.livrariaonlineapi.infra.EnviadorDeEmail;
import br.com.alura.livrariaonlineapi.modelo.Perfil;
import br.com.alura.livrariaonlineapi.modelo.Usuario;
import br.com.alura.livrariaonlineapi.repository.PerfilRepository;
import br.com.alura.livrariaonlineapi.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EnviadorDeEmail enviadorDeEmail;

    public Page<UsuarioOutDTO> listar(Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
        return usuarios
                .map(u -> modelMapper.map(u, UsuarioOutDTO.class));
    }

    @Transactional
    public UsuarioOutDTO cadastrar(UsuarioInDTO usuarioInDTO) {

        Usuario usuario = modelMapper.map(usuarioInDTO, Usuario.class);

        Perfil perfil = perfilRepository.getById(usuarioInDTO.getPerfilId());
        usuario.adicionarPerfil(perfil);

        String senha = new Random().nextInt(999999) + "";
        usuario.setSenha(bCryptPasswordEncoder.encode(senha));

        usuarioRepository.save(usuario);

        String destinatario = usuario.getEmail();
        String assunto = "Livraria Online - Bem vindo(a)";
        String mensagem = String.format("Ola %s!\n\n" +
                "Segue seus dados de acesso ao sistema Livraria:" +
                "\nLogin:%s\nSenha:%s", usuario.getNome(), usuario.getLogin(), senha);

        enviadorDeEmail.enviarEmail(destinatario, assunto, mensagem);

        return modelMapper.map(usuario, UsuarioOutDTO.class);
    }
}
