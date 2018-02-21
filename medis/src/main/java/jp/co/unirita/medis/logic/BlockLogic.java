package jp.co.unirita.medis.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.unirita.medis.domain.blockbase.BlockBase;
import jp.co.unirita.medis.domain.blockbase.Item;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlockLogic {

    public List<BlockBase> getBlockList() {
        List<BlockBase> list = new ArrayList<>();
        try{
            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(new File("resources/block.json"));
            for(JsonNode node : root) {
                String blockId = node.get("blockId").asText();
                String blockName = node.get("blockName").asText();
                boolean unique = node.get("unique").asBoolean();
                List<Item> items = createItemList(node, "items");
                String additionalType = node.get("additionalType").asText();
                List<Item> addItems = createItemList(node, "addItems");
                list.add(new BlockBase(blockId, blockName, unique, items, additionalType, addItems));
            }
        }catch (Exception e){
            // TODO 後でどうにかする
            e.printStackTrace();
        }
        return list;
    }

    public List<Item> createItemList(JsonNode node, String key) {
        List<Item> items = new ArrayList<>();
        for(JsonNode item: node.get(key)){
            int size = item.get("size").asInt();
            String templateType = item.get("templateType").asText();
            String documentType = item.get("documentType").asText();
            String value = item.get("value").asText();
            items.add(new Item(size, templateType, documentType, value));
        }
        return items;
    }
}
