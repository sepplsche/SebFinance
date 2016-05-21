package de.seppl.sebfinance.print;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrintableColumn<T> {

	private final String head;
	private final Function<T, String> columnToString;
	private final boolean left;

	public PrintableColumn(String head, Function<T, String> columnToString, boolean left) {
		this.head = head;
		this.columnToString = columnToString;
		this.left = left;
	}

	public String head() {
		return head;
	}

	public String value(T element) {
		return columnToString.apply(element);
	}

	public int size(Collection<T> elements) {
		List<String> strings = new ArrayList<>(elements.stream().map(columnToString).collect(Collectors.toList()));
		strings.add(head);
		return strings.stream().mapToInt(s -> s.length()).max().orElse(0);
	}

	public boolean left() {
		return left;
	}
}
