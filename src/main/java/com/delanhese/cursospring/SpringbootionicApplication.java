package com.delanhese.cursospring;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.delanhese.cursospring.domain.Categoria;
import com.delanhese.cursospring.domain.Cidade;
import com.delanhese.cursospring.domain.Cliente;
import com.delanhese.cursospring.domain.Endereco;
import com.delanhese.cursospring.domain.Estado;
import com.delanhese.cursospring.domain.ItemPedido;
import com.delanhese.cursospring.domain.Pagamento;
import com.delanhese.cursospring.domain.PagamentoComBoleto;
import com.delanhese.cursospring.domain.PagamentoComCartao;
import com.delanhese.cursospring.domain.Pedido;
import com.delanhese.cursospring.domain.Produto;
import com.delanhese.cursospring.domain.enums.EstadoPagamento;
import com.delanhese.cursospring.domain.enums.TipoCliente;
import com.delanhese.cursospring.repositories.CategoriaRepository;
import com.delanhese.cursospring.repositories.CidadeRepository;
import com.delanhese.cursospring.repositories.ClienteRepository;
import com.delanhese.cursospring.repositories.EnderecoRepository;
import com.delanhese.cursospring.repositories.EstadoRepository;
import com.delanhese.cursospring.repositories.ItemPedidoRepository;
import com.delanhese.cursospring.repositories.PagamentoRepository;
import com.delanhese.cursospring.repositories.PedidoRepository;
import com.delanhese.cursospring.repositories.ProdutoRepository;

@SpringBootApplication
public class SpringbootionicApplication implements CommandLineRunner{

	private CategoriaRepository categoriaRepository; 	
	private ProdutoRepository produtoRepository;	
	private EstadoRepository estadoRepository;
	private CidadeRepository cidadeRepository;
	private ClienteRepository clienteRepository;
	private EnderecoRepository enderecoRepository;
	private PedidoRepository pedidoRepository;
	private PagamentoRepository pagamentoRepository;
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired	
	public SpringbootionicApplication(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository,
			EstadoRepository estadoRepository, CidadeRepository cidadeRepository, ClienteRepository clienteRepository,
			EnderecoRepository enderecoRepository, PedidoRepository pedidoRepository,
			PagamentoRepository pagamentoRepository,
			ItemPedidoRepository itemPedidoRepository) {
		super();
		this.categoriaRepository = categoriaRepository;
		this.produtoRepository = produtoRepository;
		this.estadoRepository = estadoRepository;
		this.cidadeRepository = cidadeRepository;
		this.clienteRepository = clienteRepository;
		this.enderecoRepository = enderecoRepository;
		this.pedidoRepository = pedidoRepository;
		this.pagamentoRepository = pagamentoRepository;
		this.itemPedidoRepository = itemPedidoRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringbootionicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));			
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("37363323", "93838939"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = 	new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pgto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}

}

