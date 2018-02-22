package jp.co.unirita.medis.logic;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documentitem.DocumentItem;
import jp.co.unirita.medis.domain.documentitem.DocumentItemRepository;
import jp.co.unirita.medis.form.document.DocumentContentForm;
import jp.co.unirita.medis.form.document.DocumentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DocumentLogic {

    @Autowired
    DocumentInfoRepository documentInfoRepository;
    @Autowired
    DocumentItemRepository documentItemRepository;

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

    public void save(DocumentForm documentForm, String employeeNumber) throws Exception {
        String id = saveDocumentInfo(documentForm, employeeNumber);
        documentForm.setDocumentId(id);

        saveDocumentContent(documentForm.getDocumentId(), documentForm.getContents());
    }

    public void update(DocumentForm documentForm, String employeeNumber) throws Exception {
        saveDocumentInfo(documentForm, employeeNumber);
        updateDocumentContent(documentForm.getDocumentId(), documentForm.getContents());
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

    public void saveDocumentContent(String documentId, List<DocumentContentForm> contents) {
        for(DocumentContentForm content: contents) {
            saveDocumentItems(documentId, content.getContentOrder(), content.getItems());
        }
    }

    public void saveDocumentItems(String documentId, int contentOrder, List<String> items) {
        int lineNumbeer = 1;
        for(String item: items) {
            documentItemRepository.save(new DocumentItem(documentId, contentOrder, lineNumbeer, item));
            lineNumbeer++;
        }
    }

    public void updateDocumentContent(String documentId, List<DocumentContentForm> contents) {
        List<DocumentContentForm> oldContents = getDocumentContents(documentId);

        for(Iterator<DocumentContentForm> oldItr = oldContents.iterator(), itr = contents.iterator(); itr.hasNext();) {
            DocumentContentForm oldContent = oldItr.next();
            DocumentContentForm content = itr.next();
            updateDocumentItems(documentId, content.getContentOrder(), oldContent.getItems(), content.getItems());
        }
    }

    public void updateDocumentItems(String documentId, int contentOrder, List<String> oldItems,List<String> items) {
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
