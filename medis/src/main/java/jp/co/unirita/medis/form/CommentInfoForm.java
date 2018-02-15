package jp.co.unirita.medis.form;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentInfoForm implements Serializable {

	public Timestamp commentDate;

	public boolean isIcon;

	public String lastName;

	public String firstName;

	public boolean isRead;

	public String commentContent;

}
