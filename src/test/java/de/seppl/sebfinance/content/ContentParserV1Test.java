package de.seppl.sebfinance.content;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import de.seppl.sebfinance.pdf.ContentParser;
import de.seppl.sebfinance.pdf.ContentParserV1;
import de.seppl.sebfinance.pdf.PdfParser;
import de.seppl.sebfinance.pdf.RawPdf;


public class ContentParserV1Test
{
    @Test
    public void parse() throws Exception
    {
        RawPdf raw = raw("rep304302916_20060601_p1285.pdf");
        ContentParser parser = new ContentParserV1();

        assertThat(parser.monat(raw), equalTo(LocalDate.of(2006, 5, 31)));
        assertThat(parser.posten(raw).size(), is(8));
    }

    private RawPdf raw(String pdfFileName)
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
