package com.challenge.food.infrastructure.pdf;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.model.input.VendaDiariaInput;
import com.challenge.food.domain.service.ConsultaDiaria;
import com.challenge.food.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class VendaReportServiceImpl implements VendaReportService {

	@Autowired
	private ConsultaDiaria consultaDiaria;

	@Override
	public byte[] gerarPDF(VendaDiariaInput input) {
		try {
			var inputStream = this.getClass().getResourceAsStream("/relatorios/total-vendas.jasper");

			var parametros = new HashMap<String, Object>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

			var vendasDiarias = consultaDiaria.consultaDiaria(input);
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

			var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}

	}

}
