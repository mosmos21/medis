package jp.co.unirita.medis.logic.document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.bookmark.Bookmark;
import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documentitem.DocumentItem;
import jp.co.unirita.medis.domain.documentitem.DocumentItemRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.document.DocumentContentForm;
import jp.co.unirita.medis.form.document.DocumentForm;
import jp.co.unirita.medis.logic.util.TagLogic;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
@Transactional(rollbackFor = Exception.class)
public class DocumentLogic {

	private static final Logger logger = LoggerFactory.getLogger(DocumentLogic.class);

	@Autowired
	UpdateInfoLogic updateInfoLogic;

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	DocumentTagRepository documentTagRepository;
	@Autowired
	DocumentItemRepository documentItemRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	CommentRepository commentRepository;

	@Autowired
	TagLogic tagLogic;


	public DocumentInfo getDocumentInfo(String documentId) {
		try {
			DocumentInfo documentInfo = documentInfoRepository.findOne(documentId);
			return documentInfo;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: getDocumentInfo]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: getDocumentInfo]");
		}
	}

	public DocumentForm getDocument(String employeeNumber, String documentId) {
		try {
			DocumentInfo info = documentInfoRepository.findOne(documentId);
			UserDetail detail = userDetailRepository.findOne(info.getEmployeeNumber());
			Bookmark bookmark = bookmarkRepository.findByEmployeeNumberAndDocumentId(employeeNumber, documentId);

			DocumentForm document = new DocumentForm();
			document.setEmployeeNumber(info.getEmployeeNumber());
			document.setName(
					new StringBuilder().append(detail.getLastName()).append(" ").append(detail.getFirstName()).toString());
			document.setDocumentCreateDate(info.getDocumentCreateDate());
			document.setDocumentId(documentId);
			document.setTemplateId(info.getTemplateId());
			document.setDocumentName(info.getDocumentName());
			document.setContents(getDocumentContents(documentId));
			document.setSelected(bookmark == null ? false : bookmark.isSelected());
			return document;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: getDocument]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: getDocument]");
		}
	}

	private List<DocumentContentForm> getDocumentContents(String documentId) {
		try {
			List<DocumentContentForm> contents = new ArrayList<>();

			List<DocumentItem> items = documentItemRepository
					.findByDocumentIdOrderByContentOrderAscLineNumberAsc(documentId);
			int before = 1;
			DocumentContentForm content = new DocumentContentForm();
			content.setContentOrder(1);
			for (DocumentItem item : items) {
				if (item.getContentOrder() != before) {
					contents.add(content);
					content = new DocumentContentForm();
					content.setContentOrder(item.getContentOrder());
					before = item.getContentOrder();
				}
				content.getItems().add(item.getValue());
			}
			contents.add(content);
			return contents;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: getDocumentContents]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: getDocumentContents]");
		}
	}

	public List<Tag> getDocumentTags(String id) {
		return getDocumentTagStartWith(id, "n");
	}

	public List<Tag> getDocumentSystemTags(String id) {
		return getDocumentTagStartWith(id, "s");
	}

	private List<Tag> getDocumentTagStartWith(String id, String startStr) {
		try {
			List<DocumentTag> documentTagList = documentTagRepository.findByDocumentId(id);
			List<String> tagIdList = documentTagList.stream().map(DocumentTag::getTagId)
					.filter(str -> str.startsWith(startStr)).collect(Collectors.toList());
			List<Tag> tagList = tagRepository.findByTagIdIn(tagIdList);
			tagList.sort(Comparator.naturalOrder());
			return tagList;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: getDocumentTagStartWith]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: getDocumentTagStartWith]");
		}
	}

	public void toggleDocumentPublish(String documentId, boolean documentPublish) {
		try {
			DocumentInfo info = documentInfoRepository.findOne(documentId);
			info.setDocumentPublish(documentPublish);
			documentInfoRepository.save(info);
			logger.info("[method: toggleDocumentPublish] Update info of documentID '" + documentId + "' " + info);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: toggleDocumentPublish]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: toggleDocumentPublish]");
		}
	}

	public String save(DocumentForm documentForm, String employeeNumber) throws IdIssuanceUpperException {
		try {
			documentItemRepository.deleteByDocumentId(documentForm.getDocumentId());
			String id = saveDocumentInfo(documentForm, employeeNumber);
			documentForm.setDocumentId(id);
			saveDocumentContent(documentForm.getDocumentId(), documentForm.getContents());
			return id;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: save]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: save]");
		}
	}

	public String saveDocumentInfo(DocumentForm document, String employeeNumber) throws IdIssuanceUpperException {
		try {
			String documentId = document.getDocumentId() == null ? createNewDocumentId() : document.getDocumentId();
			String templateId = document.getTemplateId();
			String documentName = document.getDocumentName();
			Timestamp documentCreateDate = new Timestamp(System.currentTimeMillis());
			boolean documentPublish = document.isPublish();
			DocumentInfo info = new DocumentInfo(documentId, documentName, employeeNumber, templateId, documentCreateDate,
					documentPublish);
			documentInfoRepository.saveAndFlush(info);
			return info.getDocumentId();
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: saveDocumentInfo]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: saveDocumentInfo]");
		}
	}

	private void saveDocumentContent(String documentId, List<DocumentContentForm> contents) {
		try {
			for (DocumentContentForm content : contents) {
				saveDocumentItems(documentId, content.getContentOrder(), content.getItems());
			}
			documentItemRepository.flush();
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: saveDocumentContent]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: saveDocumentContent]");
		}
	}

	private void saveDocumentItems(String documentId, int contentOrder, List<String> items) {
		try {
			int lineNumber = 1;
			for (String item : items) {
				documentItemRepository.save(new DocumentItem(documentId, contentOrder, lineNumber, item));
				lineNumber++;
			}
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: saveDocumentItems]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: saveDocumentItems]");
		}
	}

	public void saveTags(final String documentId, List<Tag> tags) throws IdIssuanceUpperException {
		try {
			documentTagRepository.deleteByDocumentId(documentId);
			DocumentInfo documentInfo = documentInfoRepository.findOne(documentId);
			int order = 1;
			for (Tag tag : tagLogic.applyTags(tags)) {
				if (tag.getTagId() == null) {
					tag.setTagId(tagLogic.getNewTagId());
				}
				documentTagRepository.save(new DocumentTag(documentId, order, tag.getTagId()));
				order++;
			}
			// システムタグ追加
			UserDetail detail = userDetailRepository.findOne(documentInfo.getEmployeeNumber());
			List<Tag> systemTagList = tagRepository.findByTagName(detail.getLastName() + " " + detail.getFirstName());
			if (systemTagList.size() == 0) {
				systemTagList.addAll(tagLogic
						.applySystemTag(Arrays.asList(new Tag("", detail.getLastName() + " " + detail.getFirstName()))));
			}
			documentTagRepository.saveAndFlush(new DocumentTag(documentId, order, systemTagList.get(0).getTagId()));
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: saveTags]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: saveTags]");
		}
	}

	public void deleteDocument(String documentId) {
		try {
			bookmarkRepository.delete(bookmarkRepository.findByDocumentId(documentId));
			documentTagRepository.deleteByDocumentId(documentId);
			documentItemRepository.deleteByDocumentId(documentId);
			commentRepository.delete(commentRepository.findByDocumentId(documentId));
			updateInfoRepository.delete(updateInfoRepository.findByDocumentId(documentId));
			documentInfoRepository.delete(documentId);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: deleteDocument]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: deleteDocument]");
		}
	}

	private synchronized String createNewDocumentId() throws IdIssuanceUpperException {
		try {
			List<DocumentInfo> list = documentInfoRepository.findAll(new Sort(Sort.Direction.DESC, "documentId"));
			if (list.size() == 0) {
				return "d0000000000";
			}
			long idNum = Long.parseLong(list.get(0).getDocumentId().substring(1));
			if (idNum == 9999999999L) {
				throw new IdIssuanceUpperException("文書の発行限界");
			}
			return String.format("d%010d", idNum + 1);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: DocumentLogic, method: createNewDocumentId]");
			throw new DBException("DB Runtime Error[class: DocumentLogic, method: createNewDocumentId]");
		}
	}
}
