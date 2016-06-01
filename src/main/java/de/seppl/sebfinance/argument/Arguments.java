package de.seppl.sebfinance.argument;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

import de.seppl.sebfinance.Mode;
import de.seppl.sebfinance.argument.Argument.MandatoryArgument;
import de.seppl.sebfinance.argument.Argument.OptionalArgument;

public class Arguments {
    public Argument<Collection<File>> files() {
        return new MandatoryArgument<>("-f", (a -> argToFiles(a)));
    }

    public Argument<LocalDate> date() {
        return new OptionalArgument<>("-d", (a -> argToDate(a)), LocalDate.of(2006, 1, 1));
    }

    public Argument<Mode> mode() {
        return new OptionalArgument<>("-m", (a -> argToMode(a)), Mode.NORMAL);
    }

    private Collection<File> argToFiles(List<String> arg) {
        return files(arg.stream().map(a -> new File(a)).collect(toList()));
    }

    private Collection<File> files(List<File> files) {
        Collection<File> pdfs = files.stream() //
                .filter(File::isFile) //
                .filter(this::pdf) //
                .collect(toList());

        pdfs.addAll(files.stream() //
                .filter(File::isDirectory) //
                .map(File::listFiles) //
                .map(Arrays::asList) //
                .map(this::files) //
                .flatMap(Collection::stream) //
                .collect(toList()));

        return pdfs;
    }

    private boolean pdf(File file) {
        String name = file.getName();
        return name.startsWith("rep304302916") || name.startsWith("rep302302056");
    }

    private LocalDate argToDate(List<String> arg) {
        return arg.stream().map(a -> LocalDate.parse(a)).findFirst().get();
    }

    private Mode argToMode(List<String> arg) {
        return arg.stream().map(Mode::of).findFirst().get();
    }
}
