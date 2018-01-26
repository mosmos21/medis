package jp.co.unirita.medis.domain.box;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Entity
@Table(name = "box")
public class Box {

	@Id
	@Size(min = 11, max = 11)
	private String boxId;

	@Size(max = 256)
	private String boxLogicName;

	@Size(max = 256)
	private String boxName;
}
