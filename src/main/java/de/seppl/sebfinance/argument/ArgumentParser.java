package de.seppl.sebfinance.argument;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import de.seppl.sebfinance.argument.Argument.MandatoryArgument;
import de.seppl.sebfinance.argument.Argument.OptionalArgument;


public class ArgumentParser
{

    private final Map<String, Stream<String>> args;

    public ArgumentParser(String[] args)
    {
        String joinedArgs = StringUtils.join(args, " ");
        String[] splitArgs = StringUtils.split(joinedArgs, "-");

        this.args = Stream.of(splitArgs) //
            .map(a -> createDashArg(a)) //
            .collect(
                Collectors.toMap(DashArg::param, DashArg::values, (v1, v2) -> Stream.concat(v1, v2)));
    }

    private DashArg createDashArg(String args)
    {
        String param = "-" + StringUtils.substringBefore(args, " ");
        String valuesStr = StringUtils.substringAfter(args, " ");
        String[] values = StringUtils.split(valuesStr, ",");
        return new DashArg(param, Stream.of(values));
    }

    public <T> T parse(Argument<T> definedArg)
    {
        Optional<T> arg = Optional.ofNullable(args.get(definedArg.param())) //
            .map(definedArg.converter()) //
            .filter(a -> filterNullOrEmpty(a));

        if (definedArg instanceof MandatoryArgument)
        {
            MandatoryArgument<T> mandatoryParam = (MandatoryArgument<T>) definedArg;
            return arg
                .orElseThrow(() -> new IllegalArgumentException(mandatoryParam.exceptionMessage()));
        }
        else if (definedArg instanceof OptionalArgument)
        {
            OptionalArgument<T> optionalParam = (OptionalArgument<T>) definedArg;
            return arg.orElse(optionalParam.defaultValue());
        }
        throw new IllegalStateException("Unbekannte ArgumentClass: " + definedArg.getClass());
    }

    private <T> boolean filterNullOrEmpty(T value)
    {
        if (value == null)
            return false;
        if (value instanceof Collection<?>)
        {
            return !((Collection<?>) value).isEmpty();
        }
        return true;
    }

    private static class DashArg
    {
        private final String param;
        private final Stream<String> values;

        private DashArg(String param, Stream<String> values)
        {
            this.param = param;
            this.values = values;
        }

        public String param()
        {
            return param;
        }

        public Stream<String> values()
        {
            return values;
        }
    }
}
