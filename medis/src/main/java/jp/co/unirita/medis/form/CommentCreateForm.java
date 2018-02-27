package jp.co.unirita.medis.form;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateForm implements Serializable {

	public String employeeNumber;
	public String Value;



}
