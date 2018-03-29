package jp.co.unirita.medis.domain.tag;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@Entity
@Table(name = "tag")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tag implements Comparable<Tag>, Serializable{

	@Id
	@Size(min = 11, max = 11)
	private String tagId;

	@Size(max = 256)
	private String tagName;

	@Override
	public int compareTo(Tag tag) {
		char ch1 = tagId.charAt(0);
		char ch2 = tag.getTagId().charAt(0);
		if(ch1 == ch2) {
			return tagId.compareTo(tag.getTagId());
		}
		return Character.compare(ch1, ch2);
	}
}
