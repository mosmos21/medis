package jp.co.unirita.medis.form.blockbase;

import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockBaseForm {

	private String blockId;

	private String blockName;

	private boolean unique;

    private String templateWrapType;

    private String documentWrapType;

    private List<BlockBaseItemForm> items;

    private String additionalType;

    private List<BlockBaseItemForm> addItems;
}
