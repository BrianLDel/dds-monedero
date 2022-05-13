package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import dds.monedero.validations.AmountValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {}

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) {
    AmountValidator.negativeAmount(cuanto);
    AmountValidator.maxDesposite(getTodaysDeposites());

    movimientos.add(new Movimiento(LocalDate.now(), cuanto, true));
  }

  public void sacar(double cuanto) {
    AmountValidator.negativeAmount(cuanto);
    AmountValidator.insuficientBalance(getSaldo() - cuanto);
    AmountValidator.maxWithdraw(getMontoExtraidoA(LocalDate.now()));

    movimientos.add(new Movimiento(LocalDate.now(), cuanto, false));
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> movimiento.fueExtraido(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    double depositosMonto =  getMovimientos().stream()
        .filter(movimiento -> movimiento.isDeposito())
        .mapToDouble(Movimiento::getMonto)
        .sum();
    double retirosMonto = getMovimientos().stream()
        .filter(movimiento -> movimiento.isExtraccion())
        .mapToDouble(Movimiento::getMonto)
        .sum();

    return depositosMonto - retirosMonto;
  }

  public double getTodaysDeposites(){
    return getMovimientos().stream().filter(movimiento -> movimiento.fueDepositado(LocalDate.now())).count();
  }

}
