package br.com.jpage.util;

import java.io.IOException;

import br.com.jpage.CRUD.DadosDAO;
import br.com.jpage.controller.CRUDLinhasTransPOA;
import br.com.jpage.controller.HTTPReqItinerarioTransPOA;
import br.com.jpage.controller.HTTPReqLinhasTransPOA;

public class Main {

	public Main() {
	}

	/**
	 * Solicitações a este serviço devem ser efetuadas usando uma requisição GET,
	 * com os seguintes parâmetros:
	 * 
	 * a (obrigatório) - Identificador da operação. Possíveis valores: • nc – nome e
	 * código • il - itinerários (rotas) • tp - pontos de ônibus • tx - pontos de
	 * taxi
	 * 
	 * p (obrigatório) ID relacionado a operação descrita em a. Pode ser o ID da
	 * unidade de transporte ou um range latitude x longitude para extração dos
	 * pontos de taxi/ônibus
	 * 
	 * t - Tipo da requisição. Obrigatório apenas se a=nc. Possíveis valores: • o -
	 * Ônibus • l - Lotação
	 * 
	 */
	public static void main(String[] args) {
		HTTPReqLinhasTransPOA hTTPReqLinhasTransPOA = new HTTPReqLinhasTransPOA("nc", "%", "");
		HTTPReqItinerarioTransPOA hTTPReqItinerarioTransPOA = new HTTPReqItinerarioTransPOA("il", "5566");
		CRUDLinhasTransPOA cRUDLinhasTransPOA = new CRUDLinhasTransPOA();

		try {
			hTTPReqLinhasTransPOA.getURLLinhasPOA();
			hTTPReqLinhasTransPOA.getLinhaByNome("JARDIM");
			hTTPReqItinerarioTransPOA.getURLItinerariosPOA();

			// cRUDLinhasTransPOA.executeCRUD();
			cRUDLinhasTransPOA.addSearch("id_linha", "3", DadosDAO.TIPO_INTEGER);
			System.out.println(cRUDLinhasTransPOA.executeSearch());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
