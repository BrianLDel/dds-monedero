package dds.monedero.validations;

import dds.monedero.exceptions.MontoNegativoException;

public class NegativeAmount implements Validator{

  public void validate(double amount) {
    if(amount < 0)
      throw new MontoNegativoException(amount + ": el monto a ingresar debe ser un valor positivo");
  }
}
