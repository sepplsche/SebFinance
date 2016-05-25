package de.seppl.sebfinance.argument;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import de.seppl.sebfinance.argument.Argument.MandatoryArgument;

public class ArgumentParserTest {

    @Test
    public void twoArgs() throws Exception {

        String[] args = new String[] {"-f", "F:\\Seppl PF Konto", "-d", "2013-01-01"};

        ArgumentParser parser = new ArgumentParser(args);
        Arguments arguments = new Arguments();

        LocalDate date = parser.parse(arguments.date());
        assertThat(date, equalTo(LocalDate.of(2013, 1, 1)));
    }

    @Test
    public void duplicateArgs() throws Exception {
        String[] args = new String[] {"-arg", "1", "2", "-d", "2013-01-01", "-arg", "3", "4"};
        Argument<List<Integer>> arg =
                new MandatoryArgument<>("-arg", (a -> a.stream().map(Integer::valueOf).collect(toList())));

        ArgumentParser parser = new ArgumentParser(args);

        List<Integer> ints = parser.parse(arg);
        assertThat(ints.size(), is(4));

        int summe = ints.stream().reduce(0, (a, b) -> a + b);
        assertThat(summe, is(10));
    }
}
