package jp.co.unirita.medis.domain.blockbase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockBase {

	@Id
	@Size(min = 11, max = 11)
	private String blockId;

	private String blockName;

	private boolean unique;

    private List<Item> items;

    private String additionalType;

    private List<Item> addItems;
}
