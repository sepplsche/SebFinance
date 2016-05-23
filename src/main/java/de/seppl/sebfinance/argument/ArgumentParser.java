package de.seppl.sebfinance.argument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.seppl.sebfinance.argument.Argument.MandatoryArgument;
import de.seppl.sebfinance.argument.Argument.OptionalArgument;

public class ArgumentParser {

    private final Map<String, List<String>> args;

    public ArgumentParser(String[] args) {
        this.args = parse(args);
    }

    private Map<String, List<String>> parse(String[] args) {
        Map<String, List<String>> argsMap = new HashMap<>();

        String lastArg = null;
        List<String> lastParams = new ArrayList<>();

        for (String arg : args) {
            if (arg.startsWith("-")) {
                if (lastArg != null && !lastParams.isEmpty()) {
                    List<String> params = argsMap.get(lastArg);
                    if (params == null) {
                        argsMap.put(lastArg, new ArrayList<>(lastParams));
                    } else {
                        params.addAll(new ArrayList<>(lastParams));
                    }
                }
                lastArg = arg;
                lastParams.clear();
            } else if (lastArg != null) {
                lastParams.add(arg);
            } else {
                // ignore
            }
        }
        if (lastArg != null && !lastParams.isEmpty()) {
            List<String> params = argsMap.get(lastArg);
            if (params == null) {
                argsMap.put(lastArg, new ArrayList<>(lastParams));
            } else {
                params.addAll(new ArrayList<>(lastParams));
            }
        }
        return argsMap;
    }

    public <T> T parse(Argument<T> definedArg) {
        Optional<T> arg = Optional.ofNullable(args.get(definedArg.param())) //
                .map(definedArg.converter()) //
                .filter(a -> filterNullOrEmpty(a));

        if (definedArg instanceof MandatoryArgument) {
            MandatoryArgument<T> mandatoryParam = (MandatoryArgument<T>) definedArg;
            return arg.orElseThrow(() -> new IllegalArgumentException(mandatoryParam.exceptionMessage()));
        } else if (definedArg instanceof OptionalArgument) {
            OptionalArgument<T> optionalParam = (OptionalArgument<T>) definedArg;
            return arg.orElse(optionalParam.defaultValue());
        }
        throw new IllegalStateException("Unbekannte ArgumentClass: " + definedArg.getClass());
    }

    private <T> boolean filterNullOrEmpty(T value) {
        if (value == null)
            return false;
        if (value instanceof Collection<?>) {
            return !((Collection<?>) value).isEmpty();
        }
        return true;
    }
}
