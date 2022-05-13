package dds.monedero.validations;

public class AmountValidator {
  public static void negativeAmount(double amount){
    new NegativeAmount().validate(amount);
  }

  public static void maxDesposite(double cant){
    new MaxDepositePerDay().validate(cant);
  }

  public static void insuficientBalance(double amount){
    new InsuficientBalance().validate(amount);
  }

  public static void maxWithdraw(double amount){
    new MaxWithdrawAmountPerDay().validate(amount);
  }
}
