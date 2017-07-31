package leeco.fibonacci.computeFibo;

public class computeFibo {

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
}
