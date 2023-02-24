package br.com.filmes.controllers;

import static br.com.filmes.utils.constants.FilmeControllerConstants.TOTAL_PAGE_LIMIT;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;

import br.com.filmes.models.Filme;
import br.com.filmes.models.Filmes;
import br.com.filmes.services.FilmeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FilmeController {

	@Autowired
	private FilmeService service;

	@RequestMapping(method = GET, value = "/", produces = APPLICATION_JSON_VALUE)
	public ModelAndView home(Integer pagina) throws JsonParseException {
		ModelAndView mdv = new ModelAndView("index");
		try {
			List<Filme> filmes = service.getPoster(service.getFilmes(pagina).getResults());
			mdv.addObject("filmes", filmes);
			mdv.addObject("totalPaginas", service.getFilmes(pagina).getTotal_pages());
			mdv.addObject("anoAtual", service.getDataAtual());
		} catch (Exception e) {
			log.error("Error to load home page.", e.getMessage(), e);
		}
		return mdv;
	}

	@RequestMapping(value = "/{pagina}", method = RequestMethod.GET)
	public ModelAndView votados(@PathVariable(value = "pagina", required = false) Integer pagina) {
		ModelAndView mdv = new ModelAndView("maisvotados");
		List<Filme> filmes = service.getPoster(service.getFilmes(pagina).getResults());
		mdv.addObject("filmes", filmes);
		Integer totalPages = service.getFilmes(pagina).getTotal_pages();
		mdv.addObject("totalPaginas", totalPages > TOTAL_PAGE_LIMIT ? TOTAL_PAGE_LIMIT : totalPages);
		mdv.addObject("anoAtual", service.getDataAtual());
		return mdv;
	}

	@RequestMapping(value = "/detalhar/", method = RequestMethod.GET)
	public ModelAndView detalhar(String id) {
		Filme filme = service.detalhar(id);
		ModelAndView mdv = new ModelAndView("detalhes");
		mdv.addObject("filme", filme);
		mdv.addObject("anoAtual", service.getDataAtual());
		return mdv;
	}

	@RequestMapping(value = "/filtrar/{pagina}/", method = RequestMethod.GET)
	public ModelAndView filtrar(@PathVariable final Integer pagina, String filme) {
		ModelAndView mdv = new ModelAndView("filtrados");

		Filmes filmes = service.filtrar(filme, pagina);
		mdv.addObject("pagina", filmes.getPage());
		mdv.addObject("filme", filme);
		mdv.addObject("totalPaginas", filmes.getTotal_pages());
		mdv.addObject("filmes", service.getPoster(filmes.getResults()));
		mdv.addObject("anoAtual", service.getDataAtual());
		return mdv;
	}

}
