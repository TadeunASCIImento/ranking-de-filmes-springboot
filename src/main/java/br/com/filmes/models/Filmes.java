package br.com.filmes.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Filmes implements Serializable {

	private static final long serialVersionUID = 1413525990320224489L;

	private Integer page;
	private Integer total_results;
	private Integer total_pages;
	private List<Filme> results = new ArrayList<Filme>();

}
