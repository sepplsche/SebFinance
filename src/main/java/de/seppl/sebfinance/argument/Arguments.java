package de.seppl.sebfinance.argument;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

import de.seppl.sebfinance.argument.Argument.MandatoryArgument;


public class Arguments
{
    public Argument<Collection<File>> files()
    {
        return new MandatoryArgument<>("-f", (a -> argToFiles(a)));
    }

    private Collection<File> argToFiles(Stream<String> arg)
    {
        List<File> files = arg.map(a -> new File(a)).collect(toList());

        Collection<File> pdfs = files.stream().filter(File::isFile).collect(toList());

        pdfs.addAll(files.stream() //
            .filter(File::isDirectory) //
            .flatMap(dir -> Stream.of(dir.listFiles())) //
            .collect(toList()));

        return pdfs;
    }
}
