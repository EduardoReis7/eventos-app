package com.eventosapp.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventosapp.models.Convidado;
import com.eventosapp.models.Eventos;
import com.eventosapp.repository.ConvidadoRepository;
import com.eventosapp.repository.EventosRepository;
import com.eventosapp.validacao.Validacao;

@Controller
public class EventosController {
	
	// Variável para auxiliar no processo de validação
	
	String cpf;
	
	//Injeção de dependências
	
	@Autowired
	private EventosRepository eventosRepository;
	
	@Autowired
	private ConvidadoRepository convidadoRepository;
	
	//Cadastra novos eventos
	
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value ="/cadastrarEvento", method = RequestMethod.POST)
	public String form(@Valid Eventos eventos, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/eventos";
		}
		
		eventosRepository.save(eventos);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
		return "redirect:/eventos";
	}
	
	//Atualiza informações do evento 
	
	@RequestMapping(value = "/atualizar/{id}", method = RequestMethod.GET)
	public ModelAndView atualizarEvento(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("evento/formAtualizarEvento");
		Eventos eventos = eventosRepository.findById(id);
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@Transactional
	@RequestMapping(value = "/atualizar/{id}", method = RequestMethod.POST)
	public String atualizarEventoPost(@PathVariable("id") Long id, @Valid Eventos eventos, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			System.out.println("AQUIIIIII" + result.getAllErrors());
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/atualizar/{id}";
		}
		
		eventosRepository.save(eventos);
		attributes.addFlashAttribute("mensagem" , "Evento atualizado com sucesso!");
		return "redirect:/eventos";
	}
	
	//Exibe uma lista com todos os eventos
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("listaEventos");
		Iterable<Eventos> eventos = eventosRepository.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	//Mostra os detalhes de um determinado evento da lista, pelo ID
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("id") Long id) {
		Eventos eventos = eventosRepository.findById(id);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("eventos", eventos);
		
		Iterable<Convidado> convidado = convidadoRepository.findByEventos(eventos);
		mv.addObject("convidado", convidado);
		
		return mv;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("id") long id, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{id}";
		}
		Eventos eventos = eventosRepository.findById(id);
		convidado.setEventos(eventos);
		
		cpf = convidado.getCpf();
		Validacao validacao = new Validacao();
		boolean valido = validacao.validaCPF(cpf);
		if (valido) {
			convidadoRepository.save(convidado);
			attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
			return "redirect:/{id}";
		} else {
			attributes.addFlashAttribute("mensagem" , "CPF inválido!");
			return "redirect:/{id}";
		}
	}
	
	//Deleta um evento da lista
	
		@RequestMapping("/deletarEvento")
		public String deletarEvento(long id) {
			Eventos eventos = eventosRepository.findById(id);
			eventosRepository.delete(eventos);
			return "redirect:/eventos";
	}
		
	//Deleta um convidado de um determinado evento
		
	@RequestMapping("/deletarConvidado") 
	public String deletarConvidado(Long idConvidado) {
		Convidado convidado = convidadoRepository.findByIdConvidado(idConvidado);
		convidadoRepository.delete(convidado);
		
		Eventos eventos = convidado.getEventos();
		long idEventos = eventos.getId();
		String id = "" + idEventos;
		return "redirect:/" + id;
	}
}
