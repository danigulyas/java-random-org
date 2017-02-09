import response.Response;

/**
 * @author dani
 */
public interface RandomClient {
    Response<Integer> generateIntegers(int n, int min, int max);
    Response<Integer> generateIntegers(int n, int min, int max, int base);
    Response<Integer> generateIntegers(int n, int min, int max, boolean replacement, int base);
}
