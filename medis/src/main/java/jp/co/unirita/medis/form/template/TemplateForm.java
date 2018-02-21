package jp.co.unirita.medis.form.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TemplateForm {
    private String templateId;
    private String templateName;
    private boolean publish;
    private ArrayList<TemplateContentForm> contents = new ArrayList<>();
}
