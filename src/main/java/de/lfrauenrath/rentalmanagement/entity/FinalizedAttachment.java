package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "finalized_attachment")
public class FinalizedAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    private String fileType;
    @Lob
    private byte[] data;
    @ManyToOne
    @JoinColumn(name = "finalized_utility_cost_id")
    private FinalizedUtilityCost finalizedUtilityCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FinalizedUtilityCost getFinalizedUtilityCost() {
        return finalizedUtilityCost;
    }

    public void setFinalizedUtilityCost(FinalizedUtilityCost finalizedUtilityCost) {
        this.finalizedUtilityCost = finalizedUtilityCost;
    }
}
