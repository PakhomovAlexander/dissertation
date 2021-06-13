package com.github.sbst.core.blueprint.materialized;

import com.github.sbst.core.blueprint.ValuePlaceholder;

public interface MaterializedValuePlaceholder extends ValuePlaceholder {
    String getValue(); //todo: Should it be a string?
}
