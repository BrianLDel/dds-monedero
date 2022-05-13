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

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

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

  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public double getTodaysDeposites(){
    return getMovimientos().stream().filter(movimiento -> movimiento.isDeposito()).count();
  }

}
