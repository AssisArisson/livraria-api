package br.com.alura.livrariaonlineapi.controller;

import br.com.alura.livrariaonlineapi.dto.AtualizarLivroInDTO;
import br.com.alura.livrariaonlineapi.dto.LivroInDTO;
import br.com.alura.livrariaonlineapi.dto.LivroOutDTO;
import br.com.alura.livrariaonlineapi.service.LivroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/livros")
@Api(tags = "Livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    @ApiOperation("Listar Livros")
    public Page<LivroOutDTO> listar(@PageableDefault(size =10) Pageable paginacao){
        return livroService.listar(paginacao);
    }

    @PostMapping
    @ApiOperation("Cadastrar novo livro")
    public ResponseEntity<LivroOutDTO> cadastrar(@RequestBody @Valid LivroInDTO livroInDTO,
              UriComponentsBuilder uriBuilder){

        LivroOutDTO livroOutDTO = livroService.cadastrar(livroInDTO);

        URI uri = uriBuilder.path("/livros/{id}")
                .buildAndExpand(livroOutDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(livroOutDTO);

    }

    @PutMapping
    @ApiOperation("Atualizar Livros")
    public ResponseEntity<LivroOutDTO> atualizar(@RequestBody @Valid AtualizarLivroInDTO atualizarLivroInDTO){

        LivroOutDTO atualizado = livroService.atualizar(atualizarLivroInDTO);

        return ResponseEntity.ok(atualizado);

    }

    @DeleteMapping("/{id}")
    @ApiOperation("Remover Livros")
    public ResponseEntity<LivroOutDTO> remover(@PathVariable @NotNull Long id){

        livroService.remover(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Detalhar Livros")
    public ResponseEntity<LivroOutDTO> detalhar(@PathVariable @NotNull Long id){
      LivroOutDTO livroOutDTO  = livroService.detalhar(id);

      return ResponseEntity.ok(livroOutDTO);
    }

}
