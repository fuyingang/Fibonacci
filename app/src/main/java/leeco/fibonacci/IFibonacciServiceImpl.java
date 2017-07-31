package leeco.fibonacci;

import android.os.SystemClock;

import leeco.fibonacci.computeFibo.computeFibo;

/**
 * Created by fuyingang on 2017/7/26.
 */

public class IFibonacciServiceImpl extends IFibonacciService.Stub {

    private static final String TAG = "IFibonacciServiceImpl";

    private  final computeFibo localComputeFibo = new computeFibo();

    public FibonacciResponse fib(FibonacciRequest request) {
        long timeInMillis = SystemClock.uptimeMillis();
        long result;

        switch (request.getType()) {
            case ITERATE_JAVA:
                result = localComputeFibo.iterate_J(request.getN());
                break;
            case RECURSIVE_JAVA:
                result = localComputeFibo.recursive_J(request.getN());
                break;
            case ITERATE_NATIVE:
                result = this.iterateNative(request.getN());
                break;
            case RECURSIVE_NATIVE:
                result = this.recursiveNative(request.getN());
                break;
            default:
                return null;
        }

        timeInMillis = SystemClock.uptimeMillis() - timeInMillis;

        return new FibonacciResponse(result,timeInMillis);
    }


    public long recursive_J(long num) {

        if(num == 1)
            return 0;
        if(num == 2)
            return 1;

        return recursive_J(num - 2)+ recursive_J(num - 1);
    }

    public long iterate_J(long num) {

        long resultN_2, resultN_1, result = 0;

        if (num == 1)
            return 0;
        if (num == 2)
            return 1;

        resultN_2 = 0;
        resultN_1 = 1;

        for(int i = 3; i<= num; i++) {
            result = resultN_2 + resultN_1;
            resultN_2 = resultN_1;
            resultN_1 = result;
        }


        return result;
    }

    public native long recursiveNative(long num);

    public native long iterateNative(long num);

    static {
        System.loadLibrary("fibonacci");
    }
}
