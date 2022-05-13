package dds.monedero.validations;

import dds.monedero.exceptions.SaldoMenorException;

public class InsuficientBalance implements Validator{

  @Override
  public void validate(double amount) {
    if(amount < 0)
      throw new SaldoMenorException("No puede sacar mas de " + amount + " $");
  }
}
