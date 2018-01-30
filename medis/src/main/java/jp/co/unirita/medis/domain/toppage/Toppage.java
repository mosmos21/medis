package jp.co.unirita.medis.domain.toppage;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "toppage")
public class Toppage {

	@Id
	@Size(max = 64)
	private String employeeNumber;

	@Id
	@Size(min = 11, max = 11)
	private String boxId;

	private int toppageOrder;
}
