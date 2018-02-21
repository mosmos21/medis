package jp.co.unirita.medis.domain.blockbase;

import lombok.Data;

@Data
public class Item {

    private int size;
    private String templateType;
    private String documentType;
    private String value;

    public Item(int size, String templateType, String documentType, String value) {
        this.size = size;
        this.templateType = templateType;
        this.documentType = documentType;
        this.value = value;
    }
}
