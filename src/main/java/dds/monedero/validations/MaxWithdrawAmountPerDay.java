package dds.monedero.validations;

import dds.monedero.exceptions.MaximoExtraccionDiarioException;

public class MaxWithdrawAmountPerDay implements Validator{
  @Override
  public void validate(double amount) {
    if(amount > 1000)
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $1000 diarios");
  }
}
