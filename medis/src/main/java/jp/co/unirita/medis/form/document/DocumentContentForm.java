package jp.co.unirita.medis.form.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocumentContentForm {
    private int contentOrder;
    private ArrayList<String> items = new ArrayList<>();
}
