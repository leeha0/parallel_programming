package chapter5;

import annotation.ThreadSafe;

import java.math.BigInteger;

@ThreadSafe
public class Ex_5_20_Factorizer
        /*implements Servlet*/ {

    private final Ex_5_16_Computable<BigInteger, BigInteger[]> c = arg -> factor(arg);
    private final Ex_5_16_Computable<BigInteger, BigInteger[]> cache = new Ex_5_19_Memoizer<>(c);

    private BigInteger[] factor(BigInteger arg) {
        // 인수분해
        return new BigInteger[0];
    }

//    public void service(ServletRequest req, ServletResponse resp) {
//        try {
//            BigInteger i = extractFormRequest(req);
//            encodeIntoResponse(resp, cache.compute(i));
//        } catch (InterruptedException e) {
//            encodeError(resp, "factorization interrupted");
//        }
//    }
//
//    private BigInteger extractFormRequest(ServletRequest req) {
//        return null;
//    }
//
//    private void encodeIntoResponse(ServletResponse resp, BigInteger[] compute) {
//    }
//
//    private void encodeError(ServletResponse resp, String message) {
//    }
}
