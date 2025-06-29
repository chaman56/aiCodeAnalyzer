package com.ai.analyzer.service;

import com.ai.analyzer.dto.AnalysisResponse;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public ByteArrayInputStream generatePdfReport(AnalysisResponse analysisResponse) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("AI Code Analysis Report"));
        document.add(new Paragraph("Suggestions:"));
        analysisResponse.getSuggestions().forEach(suggestion -> document.add(new Paragraph("- " + suggestion)));

        document.add(new Paragraph("Generated Fix:"));
        document.add(new Paragraph(analysisResponse.getGeneratedFix()));

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
