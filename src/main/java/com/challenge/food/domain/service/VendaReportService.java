package com.challenge.food.domain.service;

import com.challenge.food.domain.model.input.VendaDiariaInput;

public interface VendaReportService {

	byte[] gerarPDF(VendaDiariaInput input);
}
