package de.seppl.sebfinance.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


public class PdfParser
{

    public Collection<String> parse(File pdf)
    {
        if (!pdf.getName().startsWith("rep"))
            return Collections.emptyList();

        PDDocument document = null;
        try
        {
            document = PDDocument.load(pdf);
            PDFTextStripper stripper = new PDFTextStripper();
            String content = stripper.getText(document);
            String[] lines = StringUtils.split(content, System.lineSeparator());
            return Arrays.asList(lines);
        }
        catch (IOException e)
        {
            throw new IllegalStateException(e);
        }
        finally
        {
            if (document != null)
                try
                {
                    document.close();
                }
                catch (IOException e)
                {
                    // ignore
                }
        }
    }
}
