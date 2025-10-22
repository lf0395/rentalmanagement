package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.FinalizedUtilityStatement;
import de.lfrauenrath.rentalmanagement.repository.FinalizedUtilityStatementRepository;
import de.lfrauenrath.rentalmanagement.repository.UtilityStatementRepository;
import de.lfrauenrath.rentalmanagement.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("finalized-statements")
public class FinalizedUtilityStatementController {

    private final FinalizedUtilityStatementRepository repository;
    private final UtilityStatementRepository utilityStatementRepository;
    private final PdfService pdfService;

    public FinalizedUtilityStatementController(FinalizedUtilityStatementRepository repository,
                                               PdfService pdfService,
                                               UtilityStatementRepository utilityStatementRepository) {
        this.repository = repository;
        this.pdfService = pdfService;
        this.utilityStatementRepository = utilityStatementRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public List<FinalizedUtilityStatement> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Optional<FinalizedUtilityStatement> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping("/{id}/pdf")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public ResponseEntity<byte[]> getPdf(@PathVariable Long id) throws Exception {
        final var utilityStatement = this.utilityStatementRepository.findById(id);
        final var finalizedStatements = this.repository.findByUtilityStatement(utilityStatement.get());
        final var html = this.pdfService.renderTemplate("Gesamtaufstellung.html",
                Map.of("overview", utilityStatement.get(),
                        "finalizedStatements", finalizedStatements));
        final var pdf = this.pdfService.generatePdfFromHtml(html);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=uebersicht.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
