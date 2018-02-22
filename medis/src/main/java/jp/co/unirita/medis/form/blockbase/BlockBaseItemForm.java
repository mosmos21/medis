package jp.co.unirita.medis.form.blockbase;

import lombok.Data;

@Data
public class BlockBaseItemForm {

    private int size;
    private String templateType;
    private String documentType;
    private String value;

    public BlockBaseItemForm(int size, String templateType, String documentType, String value) {
        this.size = size;
        this.templateType = templateType;
        this.documentType = documentType;
        this.value = value;
    }
}
