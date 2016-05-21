package de.seppl.sebfinance.content;

import java.util.Collection;

import de.seppl.sebfinance.kontoauszug.Kontoauszug;

public interface ContentParser {

	Kontoauszug kontoauszug(Collection<String> content);
}
