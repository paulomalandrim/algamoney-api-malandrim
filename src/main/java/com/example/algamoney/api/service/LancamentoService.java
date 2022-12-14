package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) throws PessoaInexistenteOuInativaException {
		Lancamento lancamentoSalva = buscarLancamentoPeloCodigo(codigo);
		
		if (!lancamento.getPessoa().equals(lancamentoSalva.getPessoa())) {
			validarPessoa(lancamento.getPessoa());
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");
		return lancamentoRepository.save(lancamentoSalva);	
	}
	
	
	private void validarPessoa(Pessoa pessoa) throws PessoaInexistenteOuInativaException {
		
		Optional<Pessoa> pessoaAux = pessoaRepository.findById(pessoa.getCodigo());
		
		if (pessoaAux == null || !pessoaAux.get().isAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}	
		
	}

	public Lancamento salvar(Lancamento lancamento) throws PessoaInexistenteOuInativaException {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		
		if (pessoa == null || !pessoa.get().isAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}
	
	private Lancamento buscarLancamentoPeloCodigo(Long codigo) {
		Lancamento lancamentoSalva = lancamentoRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return lancamentoSalva;
	}	
	
}
