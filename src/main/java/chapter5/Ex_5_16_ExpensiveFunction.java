package chapter5;

import java.math.BigInteger;

public class Ex_5_16_ExpensiveFunction implements Ex_5_16_Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        return new BigInteger(arg);
    }
}
