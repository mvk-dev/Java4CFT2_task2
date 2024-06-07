package task2;

import lombok.Getter;

import java.util.Arrays;

public class CachedResult {
    @Getter
    private final Object result;
    private final Object[] callParameters;

    public CachedResult(Object result, Object[] callParameters) {
        this.result = result;
        this.callParameters = callParameters;
    }

    public Object[] getCallParameters() {
        return callParameters == null ? null : Arrays.copyOf(callParameters, callParameters.length);
    }

    @Override
    public String toString() {
        return "CachedResult{" +
                "result=" + result +
                ", callParameters=" + Arrays.toString(callParameters) +
                '}';
    }
}
