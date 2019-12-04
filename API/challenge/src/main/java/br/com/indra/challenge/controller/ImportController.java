package br.com.indra.challenge.controller;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.indra.challenge.model.Bandeira;
import br.com.indra.challenge.model.Distribuidora;
import br.com.indra.challenge.model.Estado;
import br.com.indra.challenge.model.Municipio;
import br.com.indra.challenge.model.Preco;
import br.com.indra.challenge.model.Produto;
import br.com.indra.challenge.repository.BandeiraRepository;
import br.com.indra.challenge.repository.DistribuidoraRepository;
import br.com.indra.challenge.repository.EstadoRepository;
import br.com.indra.challenge.repository.MunicipioRepository;
import br.com.indra.challenge.repository.PrecoRepository;
import br.com.indra.challenge.repository.ProdutoRepository;
import br.com.indra.challenge.validation.ResponseImportDto;

@RestController
@RequestMapping("/import")
public class ImportController {

	@Autowired
	private BandeiraRepository bandeiraRepository;

	@Autowired
	private DistribuidoraRepository distribuidoraRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private MunicipioRepository municipioRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PrecoRepository precoRepository;

	private Estado estado = null;

	private Municipio municipio = null;

	private Distribuidora distribuidora = null;

	private Bandeira bandeira = null;

	private Produto produto = null;

	private Preco preco = null;

	@PostMapping(consumes = "multipart/form-data;charset=UTF-8")
	public ResponseEntity<ResponseImportDto> upload(@RequestParam("csv") MultipartFile file) throws IllegalStateException, IOException {

		List<String> dadosInvalidos = new ArrayList<String>();
		Scanner scanner = new Scanner(file.getInputStream(), "UTF-8");
		scanner.nextLine();
		while (scanner.hasNext()) {
			String linha = scanner.nextLine();
			if (linha != null && !linha.trim().isEmpty()) {
				List<String> dados =  convertRow(linha);
				if(validateFields(dados)) 
				{
					bandeira = CreateBandeiraIfThereIsNo(dados);
					estado = CreateEstadoIfThereIsNo(dados);
					municipio = CreateMunicipioIfThereIsNo(dados);
					distribuidora = CreateDistribuidoraIfThereIsNo(dados);
					produto = CreateProdutoIfThereIsNo(dados);
					preco = CreatePreco(dados);
					clearEntities();
				}else 
				{
					dadosInvalidos.add(linha);
				}
			}

		}
		ResponseImportDto response = new ResponseImportDto();
		if(dadosInvalidos.size() >= 0) 
		{
		response.setErroRows(dadosInvalidos);
		response.setMessage("Seu arquivo foi importado, porém algumas linhas não possuiam o padrão suportado");
		return ResponseEntity.ok(response);
		}
		response.setMessage("Seu arquivo foi importado foi importado com sucesso !");
		return ResponseEntity.ok(response);
	}
	
	public static List<String> convertRow(String row)
    {
    	row = row.replaceAll("   ",";");
        String[] dados = row.split("  |;");
        List<String> list = Arrays.stream(dados).collect(Collectors.toList());
        for (String string : list) 
        {
			string = string.trim();
		}
        return list;
    }
	
	private void clearEntities()
	{
		bandeira = null;
		estado = null;
		municipio = null;
		distribuidora = null;
		produto = null;
		preco = null;
	}
	//Em caso de sucesso da conversão, quer dizer que a posição 7 está correta.
	private boolean validateFields(List<String> dados) 
	{
		try {
			Double.valueOf(dados.get(7).replaceAll(",","."));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Estado CreateEstadoIfThereIsNo(List<String> dados) {
		estado = estadoRepository.findBySigla(dados.get(1));
		if(estado == null) {
			estado = new Estado();
			estado.setRegiao(dados.get(0));
			estado.setSigla(dados.get(1));
			estado = estadoRepository.save(estado);
		}
		return estado;
	}

	private Municipio CreateMunicipioIfThereIsNo(List<String> dados) {
		municipio = municipioRepository.findByNome(dados.get(2));
		if(municipio == null) {
			municipio = new Municipio();
			municipio.setNome(dados.get(2));
			municipio.setEstado(estado);
			municipio = municipioRepository.save(municipio);
		}
		return municipio;
	}

	private Bandeira CreateBandeiraIfThereIsNo(List<String> dados) {
		String str= "";
		//valida tamanho da linha caso seja 11, o campo bandeira está na posição 10, caso contrário na posição 9
		if(dados.size() == 11) {
			str = dados.get(10).trim();
		}else {
			str = dados.get(9).trim();
		}
		bandeira = bandeiraRepository.findByNome(str);
		if(bandeira == null) {
			bandeira = new Bandeira();
			bandeira.setNome(str);
			bandeira = bandeiraRepository.save(bandeira);
		}
		return bandeira;
	}

	private Distribuidora CreateDistribuidoraIfThereIsNo(List<String> dados) {
		distribuidora = distribuidoraRepository.findByCodigo(Long.parseLong(dados.get(4).trim()));
		if(distribuidora == null) {
			distribuidora = new Distribuidora();
			distribuidora.setNome(dados.get(3).trim());
			distribuidora.setCodigo(Long.parseLong(dados.get(4).trim()));
			distribuidora.setMunicipio(municipio);
			distribuidora.setBandeira(bandeira);
			distribuidora = distribuidoraRepository.save(distribuidora);
		}
		return distribuidora;
	}

	private Produto CreateProdutoIfThereIsNo(List<String> dados) {
		if(produto == null) {
			produto = new Produto();
			produto.setNome(dados.get(5));
			produto.setDistribuidora(distribuidora);
			produto = produtoRepository.save(produto);
		}
		return produto;
	}
	
	private Preco CreatePreco(List<String> dados) {
		String venda= "";
		String medida= "";
		//valida tamanho da linha, caso seja 11 o valorVenda está na posição 8 e a medida no 9 da lista
		if(dados.size() == 11) {
			venda = dados.get(8);
			medida = dados.get(9);
		}else {
			venda = null;
			medida = dados.get(8);
		}
		if(produto != null) {
			preco = new Preco();
			preco.setProduto(produto);
			preco.setUnidadeMedida(medida);
			preco.setValorCompra(Double.valueOf(dados.get(7).replaceAll(",",".")));
			if(venda != null) {
				preco.setValorVenda(Double.valueOf(venda.replaceAll(",",".")));
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			preco.setDataColeta(LocalDate.parse(dados.get(6), formatter).atStartOfDay());
			preco = precoRepository.save(preco);
		}
		return preco;
	}
}
