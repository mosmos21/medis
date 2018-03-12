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

	private String employeeNumber;

	private String commentId;

	private boolean read;

	private String commentContent;

}
