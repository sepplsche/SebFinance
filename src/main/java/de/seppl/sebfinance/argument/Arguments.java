package de.seppl.sebfinance.argument;

import java.io.File;
import java.util.stream.Stream;

import de.seppl.sebfinance.argument.Argument.OptionalArgument;

public class Arguments {

	public Argument<File> pdf() {
		return new OptionalArgument<File>("-pdf", (a -> argToFile(a)), new EmptyFile());
	}

	public Argument<File> dir() {
		return new OptionalArgument<File>("-dir", (a -> argToFile(a)), new EmptyFile());
	}

	private File argToFile(Stream<String> arg) {
		return arg //
				.map(a -> new File(a)) //
				.findFirst() //
				.get();
	}

	public static class EmptyFile extends File {

		private static final long serialVersionUID = 1L;

		public EmptyFile() {
			super("emptyFile");
		}
	}
}
