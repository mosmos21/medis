package jp.co.unirita.medis.form.document;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocumentForm {
    private String documentId;
    private String templateId;
    private String documentName;
    private boolean publish;
    private boolean selected;
    private List<DocumentContentForm> contents;
}
