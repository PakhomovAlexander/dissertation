package com.github.sbst.core;

import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import com.github.sbst.core.blueprint.IntValuePlaceholder;
import com.github.sbst.core.blueprint.ValuePlaceholder;
import com.github.sbst.core.blueprint.materialized.IntValueMaterializedPlaceholder;
import com.github.sbst.core.blueprint.materialized.MaterializedValuePlaceholder;

public class ValuePlaceholderMaterializer {

    private static final int[] multipliers = {
            0,
            10, 10, 10 , 10, 10, 10, 10, 10, 10,
            100, 100, 100, 100, 100, 100,
            1000, 1000, 1000,
            10000, 10000,
            100000,
            1000000,
    };

    private final ValueGenerationConfig config;

    //fixme
    private final Random random = new Random();
    private final Map<Class<? extends ValuePlaceholder>, Supplier<MaterializedValuePlaceholder>> materializationMap = Map.of(
            IntValuePlaceholder.class, () -> new IntValueMaterializedPlaceholder(generatePseudoGaussianInt())
    );

    public ValuePlaceholderMaterializer(ValueGenerationConfig config) {this.config = config;}

    public ValuePlaceholderMaterializer() {this.config = new ValueGenerationConfig();}

    private int generatePseudoGaussianInt() {
        double gaussian = random.nextGaussian();
        return (int) Math.round(gaussian * multipliers[random.nextInt(multipliers.length)]);
    }

    public MaterializedValuePlaceholder materialize(ValuePlaceholder valuePlaceholder) {
        if (config.isOnlyDefaultValues()) {
            return new IntValueMaterializedPlaceholder(0); // fixme
        } else {
            return materializationMap.get(IntValuePlaceholder.class).get();
        }
    }
}
