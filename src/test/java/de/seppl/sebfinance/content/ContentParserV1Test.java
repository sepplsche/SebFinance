package de.seppl.sebfinance.content;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.pdf.KontoauszugParser;
import de.seppl.sebfinance.pdf.ContentParserV1;
import de.seppl.sebfinance.pdf.PdfParser;


public class ContentParserV1Test
{
    @Test
    public void parse() throws Exception
    {
        Collection<String> content = content("rep304302916_20060601_p1285.pdf");
        KontoauszugParser parser = new ContentParserV1();

        Kontoauszug auszug = parser.kontoauszug(content);
        assertThat(auszug.monat(), equalTo(LocalDate.of(2006, 5, 31)));
        assertThat(auszug.posten().size(), is(8));
    }

    private Collection<String> content(String pdfFileName)
    {
        try
        {
            URL url = getClass().getResource(pdfFileName);
            File pdf = new File(url.toURI());
            PdfParser parser = new PdfParser();
            return parser.raw(pdf);
        }
        catch (URISyntaxException e)
        {
            throw new IllegalStateException(e);
        }
    }
}
