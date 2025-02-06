package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;
    @ManyToOne
    @JoinColumn(name = "utility_cost_id")
    private UtilityCost utilityCost;

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

    public UtilityCost getUtilityCost() {
        return utilityCost;
    }

    public void setUtilityCost(UtilityCost utilityCost) {
        this.utilityCost = utilityCost;
    }
}
