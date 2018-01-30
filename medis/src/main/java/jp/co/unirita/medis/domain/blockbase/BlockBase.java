package jp.co.unirita.medis.domain.blockbase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;


import lombok.Data;

@Data
@Entity
@Table(name = "block_base")
public class BlockBase {

	@Id
	@Size(min = 11, max = 11)
	private String blockId;

	private String blockBaseHtml;

	private boolean isUnique;

	private boolean Variable;
}
