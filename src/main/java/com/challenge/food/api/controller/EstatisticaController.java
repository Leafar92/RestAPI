package com.challenge.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.domain.model.dto.VendaDiaria;
import com.challenge.food.domain.model.input.VendaDiariaInput;
import com.challenge.food.domain.service.ConsultaDiaria;
import com.challenge.food.domain.service.VendaReportService;

@RestController
@RequestMapping("estatisticas")
public class EstatisticaController {

	@Autowired
	private ConsultaDiaria consultaDiaria;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendas(VendaDiariaInput input){
		return consultaDiaria.consultaDiaria(input);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasPdf(VendaDiariaInput input){
		
		var bytesPdf  = vendaReportService.gerarPDF(input);
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		return ResponseEntity.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(bytesPdf);
	}
}
