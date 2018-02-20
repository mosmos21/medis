package jp.co.unirita.medis.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DocumentContentForm implements Serializable{
	public String documentId;
	public String templateId;
	public boolean isDocumentPublish;
	public List<ContentsForm> contents=new ArrayList<>();

}
