package jp.co.unirita.medis.domain.bookmark;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Entity
@Table(name = "bookmark")
public class Bookmark {

	@Id
	@Size(min = 11, max = 11)
	private String bookmarkId;

	@Size(max = 64)
	private String employeeNumber;

	@Size(min = 11, max = 11)
	private String documentId;
}
