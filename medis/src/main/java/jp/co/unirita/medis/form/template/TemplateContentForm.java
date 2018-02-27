package jp.co.unirita.medis.form.template;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TemplateContentForm {
    private int contentOrder;
    private String blockId;
    private ArrayList<String> items = new ArrayList<>();
}
