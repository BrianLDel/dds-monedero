package dds.monedero.validations;

import dds.monedero.exceptions.MaximaCantidadDepositosException;

public class MaxDepositePerDay implements Validator{
  public void validate(double cant){
    if(cant >= 3)
      throw new MaximaCantidadDepositosException("Ya excedio los 3 depositos diarios");
  }
}
