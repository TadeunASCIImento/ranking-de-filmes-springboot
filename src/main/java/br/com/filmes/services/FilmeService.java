package br.com.filmes.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.filmes.models.Filme;
import br.com.filmes.models.Filmes;

@Service
public class FilmeService {

	public RestTemplate template() {
		return new RestTemplate();
	}

	final String urlBase = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
	final String apiKey = "b60ba4712f3dc0dd5ce2836865dd6817";
	final String language = "&language=pt-br";

	public Filmes getFilmes(Integer pagina) {
		Filmes response = template().getForObject(urlBase + apiKey + language + "&page=" + pagina, Filmes.class);
		return response;
	}

	public Filmes filtrar(String filme, Integer pagina) {
		final String url = "https://api.themoviedb.org/3/search/movie?api_key=";
		Filmes response = template().getForObject(url + apiKey + language + "&query=" + filme + "&page=" + pagina,
				Filmes.class);
		return response;
	}

	public Filme detalhar(String id) {
		String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=";
		Filme filme = template().getForObject(url + apiKey + language, Filme.class);
		if (filme.getOverview().equals(""))
			filme.setOverview("Sinopse do filme não disponível no momento.");
		if (filme.getBackdrop_path() != null)
			filme.setBackdrop_path("https://image.tmdb.org/t/p/w500/".concat(filme.getBackdrop_path()));
		else
			filme.setBackdrop_path("https://image.tmdb.org/t/p/w185_and_h278_bestv2/".concat(filme.getPoster_path()));
		filme.setPoster_path("https://image.tmdb.org/t/p/w185_and_h278_bestv2/".concat(filme.getPoster_path()));
		return filme;

	}

	public List<Filme> getPoster(List<Filme> filmes) {
		final String url = "https://image.tmdb.org/t/p/w185_and_h278_bestv2";
		List<Filme> outraLista = new ArrayList<Filme>();
		filmes.forEach(filme -> {
			if (filme.getPoster_path() != null) {
				filme.setPoster_path(url + filme.getPoster_path());
				outraLista.add(filme);				
			}else {
				//filme.setPoster_path("/imagens/posters/no-image.png");				
			}
		});
		return outraLista;
	}

	public Integer getDataAtual() {
		return LocalDate.now().getYear();
	}

}
