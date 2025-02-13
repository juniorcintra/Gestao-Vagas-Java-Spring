package br.com.juniorcintra.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException() {
    super("Usuário ja cadastrado!");
  }
}
