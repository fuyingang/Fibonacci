// IFibonacciService.aidl
package leeco.fibonacci;

import leeco.fibonacci.FibonacciRequest;
import leeco.fibonacci.FibonacciResponse;

// Declare any non-default types here with import statements

interface IFibonacciService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
      //      double aDouble, String aString);

    long iterate_J(in long num);
    long recursive_J(in long num);
    long iterateNative(in long num);
    long recursiveNative(in long num);

    FibonacciResponse fib(in FibonacciRequest request);
}
