package jp.co.unirita.medis.form.document;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateForm implements Serializable {
	private String employeeNumber;
	private String Value;
}
