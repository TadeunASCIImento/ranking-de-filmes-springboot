package br.com.filmes.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filme implements Serializable {
	private static final long serialVersionUID = 7889018098711161085L;

	private Integer id;
	private String title;
	private String original_language;
	private String release_date;
	private String overview;
	private String backdrop_path;
	private String poster_path;
	private String popularity;
	private Integer vote_count;
	private Integer vote_average;

}
