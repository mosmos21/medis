package jp.co.unirita.medis.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ContentsForm implements Serializable{
	public int contentOrder;
	public List <ItemsForm> items=new ArrayList<>();

}
