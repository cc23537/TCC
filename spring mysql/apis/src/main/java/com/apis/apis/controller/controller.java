package com.apis.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apis.apis.models.Alimento;

import com.apis.apis.models.Cliente;
import com.apis.apis.models.Compras;
import com.apis.apis.repository.AlimentoRepository;
import com.apis.apis.repository.ClienteRepository;
import com.apis.apis.repository.ComprasRepository;





@RestController
public class controller {
    
    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComprasRepository comprasRepository;

    //Rotas De cliente
    @GetMapping("/cliente/{email}/{senha}")
    public Cliente acharLogin(@PathVariable String email,  @PathVariable String senha){
        Cliente verify =  clienteRepository.findByEmail(email);
        if(verify.getSenha()== senha){
            return verify;
        }
        else{
            return null;
        }
    }

    @GetMapping("/cliente")
    public List<Cliente> retornaTodos(){
        return clienteRepository.findAll();
    }

    @PostMapping("/cliente")
    public Cliente registroCliente(@RequestBody Cliente c){
        return clienteRepository.save(c);
    }

    @DeleteMapping("/cliente/{codigo}")
    public void remover(@PathVariable int codigo){
        Cliente obj = clienteRepository.findByIdCliente(codigo);
        clienteRepository.delete(obj);
    }





    //Alimentos 
    
    @GetMapping("/alimentos")
    public List<Alimento> retornaTodosAlimentos(){
        return alimentoRepository.findAll();
    }
    @GetMapping("/alimentos/{validade}")
    public List<Alimento> pelaData(@PathVariable String validade){
       return alimentoRepository.findByValidade(validade);
    }
   
    
    @PostMapping("/alimentos")
    public Alimento cadastrar(@RequestBody Alimento a){
        return alimentoRepository.save(a);
    }
    @PutMapping("alimentos/{codigo}")
    public Alimento atualizarAlimento(@RequestBody Alimento a,@PathVariable int codigo) {
        Alimento obj = alimentoRepository.findByIdAlimento(codigo);
        alimentoRepository.delete(obj);

        
       return alimentoRepository.save(a);
    }
    @DeleteMapping("/alimentos/{validade}/{nome}")
    public void removerAlimento(@PathVariable String validade,@PathVariable String nome){

        Alimento obj = alimentoRepository.findByNomeAlimentoAndValidade(validade,nome);
        alimentoRepository.delete(obj);
    }
    

    // Compras

    @GetMapping("/compras")
    public List<Compras> CarrinhoDeCompras() {
        return comprasRepository.findAll();
    }

    @PostMapping("/compras")
    public Compras alimentoASerComprado(@RequestBody Compras a){
        return comprasRepository.save(a);

    }
    

  
    

    @GetMapping("/helloworld")
    public String mensagem(){
        return "Hello World!";
    }

    @DeleteMapping("/compras/{nome}/{quantidade}")
    public void removerCompra(@PathVariable String nome,@PathVariable Integer quantidade){

        Compras obj = comprasRepository.findbyNome(nome);
        obj.setQuantidade(obj.getQuantidade() - quantidade);
        comprasRepository.delete(obj);
    }
    
    

    

}