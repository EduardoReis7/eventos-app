package com.eventosapp.validacao;

import java.util.List;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

public class Validacao {

	public boolean validaCPF(String cpf) {
		
		CPFValidator cpfValidator = new CPFValidator();
		List<ValidationMessage> errors = cpfValidator.invalidMessagesFor(cpf);
		
		if (errors.size() > 0) {
			System.out.println("Verifique os campos!");
			return false;
		} 
		
		System.out.println("Convidado adicionado com sucesos!");
		return true;
	}
}
