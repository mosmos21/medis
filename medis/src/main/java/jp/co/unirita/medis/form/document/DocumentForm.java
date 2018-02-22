package jp.co.unirita.medis.form.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocumentForm {
    private String documentId;
    private String templateId;
    private String documentName;
    private boolean publish;
    private List<DocumentContentForm> contents;
}
