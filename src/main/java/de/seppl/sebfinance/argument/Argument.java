package de.seppl.sebfinance.argument;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;


abstract class Argument<T>
{
    private final String param;
    private final Function<Stream<String>, T> converter;

    Argument(String param, Function<Stream<String>, T> converter)
    {
        this.param = checkNotNull(param);
        this.converter = checkNotNull(converter);
    }

    public String param()
    {
        return param;
    }

    public Function<Stream<String>, T> converter()
    {
        return converter;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(param);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        Argument<?> other = (Argument<?>) obj;
        return param.equals(other.param);
    }

    public static class MandatoryArgument<T>
        extends Argument<T>
    {
        private static final String DEFAULT_EXEPTION_MSG = "Argument %s not found!";
        private final String exceptionMessage;

        public MandatoryArgument(String param, Function<Stream<String>, T> converter)
        {
            this(param, converter, String.format(DEFAULT_EXEPTION_MSG, param));
        }

        public MandatoryArgument(String param, Function<Stream<String>, T> converter,
            String exceptionMessage)
        {
            super(param, converter);
            this.exceptionMessage = checkNotNull(exceptionMessage);
        }

        public String exceptionMessage()
        {
            return exceptionMessage;
        }
    }

    public static class OptionalArgument<T>
        extends Argument<T>
    {
        private final T defaultValue;

        public OptionalArgument(String param, Function<Stream<String>, T> converter, T defaultValue)
        {
            super(param, converter);
            this.defaultValue = checkNotNull(defaultValue);
        }

        public T defaultValue()
        {
            return defaultValue;
        }
    }
}
