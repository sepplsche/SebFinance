package de.seppl.sebfinance.argument;

import java.time.LocalDate;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

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

        String[] args = new String[] {"-f", "F:\\Seppl PF Konto", "-d", "2013-01-01", "-f", "F:\\Seppl PF Konto1"};

        ArgumentParser parser = new ArgumentParser(args);
        Arguments arguments = new Arguments();
    }
}
