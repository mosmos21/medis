package jp.co.unirita.medis.form.blockbase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockBaseItemForm {

    private String templateType;
    private String documentType;
    private String value;
}
