package br.com.filmes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.filmes.models.Filme;
import br.com.filmes.models.Filmes;
import br.com.filmes.services.FilmeService;

@Controller
public class FilmeController {

	@Autowired
	private FilmeService service;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home(Integer pagina) {
		ModelAndView mdv = new ModelAndView("index");
		List<Filme> filmes = service.getPoster(service.getFilmes(pagina).getResults());
		mdv.addObject("filmes", filmes);
		mdv.addObject("totalPaginas", service.getFilmes(pagina).getTotal_pages());
		mdv.addObject("anoAtual",service.getDataAtual());
		return mdv;
	}

	@RequestMapping(value = "/{pagina}", method = RequestMethod.GET)
	public ModelAndView votados(@PathVariable(value = "pagina", required = false) Integer pagina) {
		ModelAndView mdv = new ModelAndView("maisvotados");
		List<Filme> filmes = service.getPoster(service.getFilmes(pagina).getResults());
		mdv.addObject("filmes", filmes);
		mdv.addObject("totalPaginas", service.getFilmes(pagina).getTotal_pages());
		mdv.addObject("anoAtual",service.getDataAtual());
		return mdv;
	}

	@RequestMapping(value = "/detalhar/", method = RequestMethod.GET)
	public ModelAndView detalhar(String id) {
		Filme filme = service.detalhar(id);
		ModelAndView mdv = new ModelAndView("detalhes");
		mdv.addObject("filme", filme);
		mdv.addObject("anoAtual",service.getDataAtual());
		return mdv;
	}

	@RequestMapping(value = "/filtrar/", method = RequestMethod.GET)
	public ModelAndView filtrar(String filme, Integer pagina) {
		ModelAndView mdv = new ModelAndView("filtrados");
		
		Filmes filmes = service.filtrar(filme, pagina);
		mdv.addObject("pagina", filmes.getPage());
		mdv.addObject("filme", filme);
		mdv.addObject("totalPaginas", filmes.getTotal_pages());
		mdv.addObject("filmes", service.getPoster(filmes.getResults()));
		mdv.addObject("anoAtual",service.getDataAtual());
		return mdv;
	}

}
