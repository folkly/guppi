package io.github.folkly.guppi.embeddings.util;

import io.github.folkly.openai.model.v1.embeddings.Embedding;
import org.apache.commons.math3.linear.ArrayRealVector;

public class EmbeddingsUtil {
    public static double calculateDistance(Embedding embedding1, Embedding embedding2) {
        return new ArrayRealVector(embedding1.embedding).getDistance(new ArrayRealVector(embedding2.embedding));
    }

    public static double calculateDistance(double[] vec1, double[] vec2) {
        return new ArrayRealVector(vec1).getDistance(new ArrayRealVector(vec2));
    }

    public static double calculateDistance(ArrayRealVector vec1, Embedding vec2) {
        return vec1.getDistance(new ArrayRealVector(vec2.embedding));
    }

    public static double calculateDistance(ArrayRealVector vec1, double[] vec2) {
        return vec1.getDistance(new ArrayRealVector(vec2));
    }

    public static double calculateDistance(ArrayRealVector vec1, ArrayRealVector vec2) {
        return vec1.getDistance(vec2);
    }
}
