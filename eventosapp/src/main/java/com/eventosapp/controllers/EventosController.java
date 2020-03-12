package com.eventosapp.controllers;

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

@Controller
public class EventosController {
	
	@Autowired
	private EventosRepository eventosRepository;
	
	@Autowired
	private ConvidadoRepository convidadoRepository;
	
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
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("listaEventos");
		Iterable<Eventos> eventos = eventosRepository.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("id") long id) {
		Eventos eventos = eventosRepository.findById(id);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("eventos", eventos);
		
		Iterable<Convidado> convidado = convidadoRepository.findByEventos(eventos);
		mv.addObject("convidado", convidado);
		
		return mv;
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long id) {
		Eventos eventos = eventosRepository.findById(id);
		eventosRepository.delete(eventos);
		return "redirect:/eventos";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("id") long id, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{id}";
		}
		Eventos eventos = eventosRepository.findById(id);
		convidado.setEventos(eventos);
		convidadoRepository.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
		return "redirect:/{id}";
	}
	
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
