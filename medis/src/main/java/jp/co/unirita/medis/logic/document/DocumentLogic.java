package jp.co.unirita.medis.logic.document;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documentitem.DocumentItem;
import jp.co.unirita.medis.domain.documentitem.DocumentItemRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.form.document.DocumentContentForm;
import jp.co.unirita.medis.form.document.DocumentForm;
import jp.co.unirita.medis.logic.util.TagLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentLogic {

    private static final Logger logger = LoggerFactory.getLogger(DocumentLogic.class);

    @Autowired
    DocumentInfoRepository documentInfoRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    DocumentTagRepository documentTagRepository;
    @Autowired
    DocumentItemRepository documentItemRepository;

    @Autowired
    TagLogic tagLogic;

    public List<Tag> getDocumentTags(String id) {
        List<DocumentTag> documentTagList = documentTagRepository.findByDocumentIdOrderByTagOrderAsc(id);
        List<Tag> tag = tagRepository.findByTagId(documentTagList.stream().map(t -> t.getTagId()).collect(Collectors.toList()));
        return tag;
    }

    public DocumentForm getDocument(String id) {
        DocumentInfo info = documentInfoRepository.findOne(id);

        DocumentForm document = new DocumentForm();
        document.setDocumentId(id);
        document.setTemplateId(info.getTemplateId());
        document.setDocumentName(info.getDocumentName());
        document.setContents(getDocumentContents(id));
        return document;
    }

    private List<DocumentContentForm> getDocumentContents(String documentId) {
        List<DocumentContentForm> contents = new ArrayList<>();

        List<DocumentItem> items = documentItemRepository.findByDocumentIdOrderByContentOrderAscLineNumberAsc(documentId);
        int before = 1;
        DocumentContentForm content = new DocumentContentForm();
        content.setContentOrder(1);
        for(DocumentItem item: items){
            if(item.getContentOrder() != before){
                contents.add(content);
                content = new DocumentContentForm();
                content.setContentOrder(item.getContentOrder());
                before = item.getContentOrder();
            }
            content.getItems().add(item.getValue());
        }
        contents.add(content);
        return contents;
    }

    public void toggleDocumentPublish(String documentId, boolean documentPublish) {
        DocumentInfo info = documentInfoRepository.findOne(documentId);
        info.setDocumentPublish(documentPublish);
        documentInfoRepository.save(info);
        logger.info("[method: toggleDocumentPublish] Update info of documentID '" + documentId + "' " + info);
    }

    public void save(DocumentForm documentForm, String employeeNumber) throws Exception {
        String id = saveDocumentInfo(documentForm, employeeNumber);
        documentForm.setDocumentId(id);
        saveDocumentContent(documentForm.getDocumentId(), documentForm.getContents());
    }

    public void saveTags(String documentId, List<Tag> tags) throws Exception{
        int order = 1;
        for(Tag tag : tags) {
            if(tag.getTagId() == null) {
                tag.setTagId(tagLogic.getNewTagId());
            }
            documentTagRepository.save(new DocumentTag(documentId, order, tag.getTagId()));
            order++;
        }
    }

    public void update(DocumentForm documentForm, String employeeNumber) throws Exception {
        saveDocumentInfo(documentForm, employeeNumber);
        updateDocumentContent(documentForm.getDocumentId(), documentForm.getContents());
    }

    public void updateTags(String documentId, List<Tag> tags) {
        List<DocumentTag> oldTags = documentTagRepository.findByDocumentIdOrderByTagOrderAsc(documentId);

        int common = Math.min(oldTags.size(), tags.size());
        int order = 1;
        for(Tag tag: tags) {
            if(common < order){
                break;
            }
            documentTagRepository.save(new DocumentTag(documentId, order, tag.getTagId()));
            order++;
        }
        if(oldTags.size() < tags.size()) {
            List<Tag> addTags = tags.subList(common, tags.size());
            for(Tag tag: addTags) {
                documentTagRepository.save(new DocumentTag(documentId, order, tag.getTagId()));
                order++;
            }
        } else {
            for(;order <= oldTags.size(); order++) {
                documentTagRepository.delete(new DocumentTag.PK(documentId, order));
            }
        }
    }

    public String saveDocumentInfo(DocumentForm document, String employeeNumber) throws Exception{
        String documentId = document.getDocumentId() == null ? createNewDocumentId() : document.getDocumentId();
        String templateId = document.getTemplateId();
        String documentName = document.getDocumentName();
        Timestamp documentCreateDate = new Timestamp(System.currentTimeMillis());
        boolean documentPublish = document.isPublish();
        DocumentInfo info = new DocumentInfo(documentId, employeeNumber, templateId, documentName, documentCreateDate, documentPublish);
        documentInfoRepository.save(info);
        return info.getDocumentId();
    }

    private void saveDocumentContent(String documentId, List<DocumentContentForm> contents) {
        for(DocumentContentForm content: contents) {
            saveDocumentItems(documentId, content.getContentOrder(), content.getItems());
        }
    }

    private void saveDocumentItems(String documentId, int contentOrder, List<String> items) {
        int lineNumbeer = 1;
        for(String item: items) {
            documentItemRepository.save(new DocumentItem(documentId, contentOrder, lineNumbeer, item));
            lineNumbeer++;
        }
    }

    private void updateDocumentContent(String documentId, List<DocumentContentForm> contents) {
        List<DocumentContentForm> oldContents = getDocumentContents(documentId);

        for(Iterator<DocumentContentForm> oldItr = oldContents.iterator(), itr = contents.iterator(); itr.hasNext();) {
            DocumentContentForm oldContent = oldItr.next();
            DocumentContentForm content = itr.next();
            updateDocumentItems(documentId, content.getContentOrder(), oldContent.getItems(), content.getItems());
        }
    }

    private void updateDocumentItems(String documentId, int contentOrder, List<String> oldItems,List<String> items) {
        int lineNumber = 1;
        int common = Math.min(oldItems.size(), items.size());

        for(String value: items) {
            if(common < lineNumber) {
                break;
            }
            documentItemRepository.save(new DocumentItem(documentId, contentOrder, lineNumber, value));
            lineNumber++;
        }
        if(oldItems.size() < items.size()) {
            List<String> addItems = items.subList(lineNumber - 1, items.size());
            for(String value : addItems) {
                documentItemRepository.save(new DocumentItem(documentId, contentOrder, lineNumber, value));
                lineNumber++;
            }
        } else {
            for(; lineNumber <= oldItems.size(); lineNumber++) {
                documentItemRepository.delete(new DocumentItem.PK(documentId, contentOrder, lineNumber));
            }
        }
    }

    private String createNewDocumentId() throws Exception {
        List<DocumentInfo> list = documentInfoRepository.findAll(new Sort(Sort.Direction.DESC, "documentId"));
        if(list.size() == 0) {
            return "d0000000000";
        }
        long idNum = Long.parseLong(list.get(0).getTemplateId().substring(1));
        if(idNum == 9999999999L){
            throw new Exception("文書の発行限界");
        }
        return String.format("d%010d", idNum + 1);
    }
}