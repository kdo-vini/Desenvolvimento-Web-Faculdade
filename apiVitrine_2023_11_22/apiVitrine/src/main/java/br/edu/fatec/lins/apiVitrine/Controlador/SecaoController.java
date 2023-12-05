package br.edu.fatec.lins.apiVitrine.Controlador;

import br.edu.fatec.lins.apiVitrine.dto.ProdutoDTO;
import br.edu.fatec.lins.apiVitrine.dto.SecaoDTO;
import br.edu.fatec.lins.apiVitrine.modelo.loja.Secao;
import br.edu.fatec.lins.apiVitrine.repositorio.SecaoRepositorio;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class SecaoController {
    @Autowired
    SecaoRepositorio repositorio;

    @PostMapping("/secoes")
    public ResponseEntity<Secao> salvarProduto(@RequestBody SecaoDTO secaoDTO){
        var secaoModelo = new Secao();
        BeanUtils.copyProperties(secaoDTO, secaoModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(repositorio.save(secaoModelo));
    }
    @GetMapping("/secoes")
    public ResponseEntity<List<Secao>> getAllSecao(){
        return ResponseEntity.status(HttpStatus.OK).body(repositorio.findAll());
    }

    @GetMapping("/secoes/{id}")
    public ResponseEntity<Object> getSecoesPorId(@PathVariable(value="id") UUID id) {
        Optional<Secao> secao = repositorio.findById(id);
        if(secao.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seção não encontrada!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(secao.get());
    }

    @PutMapping("/secoes/{id}")
    public ResponseEntity<Object> updateProduto(@PathVariable(value="id") UUID id,
                                                @RequestBody ProdutoDTO prodDTO){
        Optional<Secao> Secao = repositorio.findById(id);
        if(Secao.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Secao não encontrada!");
        }
        BeanUtils.copyProperties(prodDTO, Secao.get());
        return ResponseEntity.status(HttpStatus.OK).body(repositorio.save(Secao.get()));
    }

    @DeleteMapping("/secoes/{id}")
    public ResponseEntity<Object> deleteSecao(@PathVariable(value="id") UUID id){
        Optional<Secao> Secao = repositorio.findById(id);
        if(Secao.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Secao não encontrada!");
        }
        repositorio.delete(Secao.get());
        return ResponseEntity.status(HttpStatus.OK).body("Secao removida com sucesso!");
    }

}