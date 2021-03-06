package jp.co.unirita.medis.logic.template;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jp.co.unirita.medis.config.path.ServerResourcesPathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.unirita.medis.form.template.BlockBaseForm;
import jp.co.unirita.medis.form.template.BlockBaseItemForm;

@Service
public class BlockLogic {

    @Autowired
    ServerResourcesPathUtil serverResourcesPathUtil;

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public List<BlockBaseForm> getBlockList() {
        List<BlockBaseForm> list = new ArrayList<>();
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(serverResourcesPathUtil.getPath() + "/block.json"));
            System.out.println(root+"2");
            for(JsonNode node : root) {
                String blockId = node.get("blockId").asText();
                String blockName = node.get("blockName").asText();
                boolean unique = node.get("unique").asBoolean();
                String templateWrapType = node.get("templateWrapType").asText();
                String documentWrapType = node.get("documentWrapType").asText();
                List<BlockBaseItemForm> blockBaseItemForm = createItemList(node, "items");
                String additionalType = node.get("additionalType").asText();
                List<BlockBaseItemForm> addBlockBaseItemForm = createItemList(node, "addItems");
                list.add(new BlockBaseForm(blockId, blockName, unique, templateWrapType, documentWrapType, blockBaseItemForm, additionalType, addBlockBaseItemForm));
            }
        }catch (Exception e){
            // TODO 後でどうにかする
            e.printStackTrace();
        }
        list.sort(Comparator.comparing(BlockBaseForm::getBlockId));

        return list;
    }

    public List<BlockBaseItemForm> createItemList(JsonNode node, String key) {
        List<BlockBaseItemForm> blockBaseItemForm = new ArrayList<>();
        for(JsonNode item: node.get(key)){
            String templateType = item.get("templateType").asText();
            String documentType = item.get("documentType").asText();
            String value = item.get("value").asText();
            blockBaseItemForm.add(new BlockBaseItemForm(templateType, documentType, value));
        }
        return blockBaseItemForm;
    }
}
