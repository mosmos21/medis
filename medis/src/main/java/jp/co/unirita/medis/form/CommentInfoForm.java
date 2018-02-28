package jp.co.unirita.medis.form;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentInfoForm implements Serializable {

	private Timestamp commentDate;

	private boolean isIcon;

	private String lastName;

	private String firstName;

	private boolean isRead;

	private String commentContent;

}
