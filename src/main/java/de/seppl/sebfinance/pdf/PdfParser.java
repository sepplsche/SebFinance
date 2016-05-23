package de.seppl.sebfinance.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfParser {
    private static final Logger log = LoggerFactory.getLogger(PdfParser.class);

    public RawPdf raw(File pdf) {
        if (!pdf.getName().startsWith("rep"))
            throw new IllegalArgumentException("wrong file: " + pdf);

        PDDocument document = null;
        try {
            document = PDDocument.load(pdf);
            PDFTextStripper stripper = new PDFTextStripper();
            String content = stripper.getText(document);

            log.debug(content);

            String[] lines = StringUtils.split(content, System.lineSeparator());
            return new RawPdf(Arrays.asList(lines));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            if (document != null)
                try {
                    document.close();
                } catch (IOException e) {
                    // ignore
                }
        }
    }
}
