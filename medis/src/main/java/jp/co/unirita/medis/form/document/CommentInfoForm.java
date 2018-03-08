package jp.co.unirita.medis.form.document;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentInfoForm implements Serializable {

	private Timestamp commentDate;

	private boolean icon;

	private String lastName;

	private String firstName;

	private boolean read;

	private String value;

}
