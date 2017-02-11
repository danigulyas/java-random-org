import com.fasterxml.jackson.core.type.TypeReference;
import request.RequestBuilder;
import request.RequestException;
import response.Response;
import response.mapper.Result;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
public class RandomApiClient {
    public static String GENERATE_INTEGERS_METHOD = "generateIntegers";
    public static String GENERATE_DECIMAL_FRACTIONS_METHOD = "generateDecimalFractions";
    public static String GENERATE_STRINGS_METHOD = "generateStrings";

    /**
     * Function to generate a serie of random numbers with base less than ten.
     * @param n Amount of random numbers to generate.
     * @param min Lower bound for integers.
     * @param max Upper bound for integers.
     * @param replacement Specifies if the random number should be picked with replacement.
     * @param base Base of numbers, for higher than ten please use the other function.
     */
    public static Response<Result<Double>> generateIntegers(
            RandomApiContext context, Double n, Double min, Double max, Boolean replacement, Integer base) throws RequestException {
        checkNotNull(context);
        checkNotNull(n);
        checkNotNull(min);
        checkNotNull(max);
        checkNotNull(replacement);
        checkNotNull(base);

        checkArgument(n > 0 && n < 1e4, "n must be bigger than 0 and less than 1e4, it as %d", n);
        checkArgument(min >= -1e9 && min < 1e9, "min must be in the range of [%d,%d]", -1e9, 1e9);
        checkArgument(max >= -1e9 && max < 1e9, "max must be in the range of [%d,%d]", -1e9, 1e9);
        checkArgument(base == 2 || base == 8 || base == 10,
                "base must be in [2,8,10], for 16 please use generateIntegersWithBase16 (different type).");

        RequestBuilder builder = RequestBuilder
                .of("n", n, "min", min, "max", max, "replacement", replacement, "base", base)
                .setMethodName(GENERATE_INTEGERS_METHOD);

        return context.query(builder, new TypeReference<Result<Double>>() {});
    }

    public static Response<Result<Double>> generateIntegers(
            RandomApiContext context, Double n, Double min, Double max) throws RequestException {
        return generateIntegers(context, n, min, max, true, 10);
    }

    public static Response<Result<Double>> generateIntegers(
            RandomApiContext context, Double n) throws RequestException {
        return generateIntegers(context, n, -1e9, 1e9);
    }

    /**
     * Geneartes a serie of decimal fractions.
     * @param context API context
     * @param n Amount of numbers to create.
     * @param decimalPlaces Decimal places to return with.
     * @param replacement Specifies whether a random number should be picked with a replacement.
     */
    public static Response<Result<Float>> generateDecimalFractions(
            RandomApiContext context, Double n, Integer decimalPlaces, Boolean replacement) throws RequestException {
        checkNotNull(context);
        checkNotNull(n);
        checkNotNull(decimalPlaces);
        checkNotNull(replacement);

        checkArgument(n > 0 && n < 1e4, "N must be between 0 and 1e4, was %d", n);
        checkArgument(decimalPlaces > 0 && decimalPlaces < 20,
                "decimalPlaces must be between 0 and 20, was %d", decimalPlaces);

        RequestBuilder builder = RequestBuilder
                .of("n", n, "decimalPlaces", decimalPlaces, "replacement", replacement)
                .setMethodName(GENERATE_DECIMAL_FRACTIONS_METHOD);

        return context.query(builder, new TypeReference<Result<Float>>() {});
    }

    public static Response<Result<Float>> generateDecimalFractions(
            RandomApiContext context, Double n, Integer decimalPlaces) throws RequestException {
        return generateDecimalFractions(context, n, decimalPlaces, true);
    }

    /**
     * Generates random strings.
     * @param context
     * @param n Amount of strings to generate.
     * @param length Lengths of each individual string.
     * @param characterset Characterset which the strings should be assembled from.
     * @param replacement Should be picked with a replacement.
     * @throws RequestException
     */
    public static Response<Result<String>> generateStrings(
            RandomApiContext context, Double n, Integer length, String characterset, Boolean replacement) throws RequestException {
        checkNotNull(context);
        checkNotNull(n);
        checkNotNull(length);
        checkNotNull(characterset);
        checkNotNull(replacement);

        checkArgument(n > 0 && n < 1e4, "N must be between 0 and 1e4, was: %d", n);
        checkArgument(length > 1 && length < 20,
                "Length must be between 1 and 20, was: %d", length);
        checkArgument(characterset.length() < 80,
                "The length of the characterset must be less than 80, was: %d", characterset.length());

        RequestBuilder builder = RequestBuilder
                .of("n", n, "length", length, "characterset", characterset, "replacement", replacement)
                .setMethodName(GENERATE_STRINGS_METHOD);

        return context.query(builder, new TypeReference<Result<String>>() {});
    }

    public static Response<Result<String>> generateStrings(
            RandomApiContext context, Double n, Integer length) throws RequestException {
        final String charset = "ABCDEFGHIKLMNOPQRSTVXYZ";

        return generateStrings(context, n, length, charset, true);
    }
}
