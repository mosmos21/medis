package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.contentframe.ContentFrame;
import jp.co.unirita.medis.domain.contentframe.ContentFrameRepository;
import jp.co.unirita.medis.domain.contenttextarea.ContentTextarea;
import jp.co.unirita.medis.domain.contenttextarea.ContentTextareaRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.form.ContentsForm;
import jp.co.unirita.medis.form.DocumentContentForm;
import jp.co.unirita.medis.form.ItemsForm;

@Service
public class DocumentLogic {
	@Autowired
	ContentFrameRepository contentframeRepository;
	@Autowired
	ContentTextareaRepository contentTextareaRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	//@Autowired
	//DocumentInfoForm documentInfoForm;

	public DocumentContentForm getDocumentInfo(String documentId) {
		int contentOrderSize = 0;

		List<ContentFrame> contentframeList = new ArrayList<>();
		List<ContentTextarea> contentMain = new ArrayList<>();
		List<ContentFrame> contentId = new ArrayList<>();
		List<DocumentInfo> documentInfoList = new ArrayList<>();

		DocumentContentForm documentInfoForm =new DocumentContentForm();

		// content_order数の取得
		contentframeList = contentframeRepository.findByDocumentId(documentId);

		for (int i = 0; i < contentframeList.size(); i++) {
			contentOrderSize = Math.max(contentOrderSize, contentframeList.get(i).getContentOrder());
		}

		// List contents作成
		for (int i = 0; i < contentOrderSize; i++) {
			ContentsForm contentsForm = new ContentsForm();

			contentsForm.contentOrder = i + 1;
			contentId = contentframeRepository.findByContentOrderAndDocumentId(i + 1, documentId);

			for (int j = 0; j < contentId.size(); j++) {
				ItemsForm itemsForm = new ItemsForm();
				contentMain = contentTextareaRepository.findByContentId(contentId.get(j).getContentId());
				itemsForm.contentMain = contentMain.get(0).getContentMain();
				contentsForm.items.add(itemsForm);

			}

			// result.add(contentsForm);
			// JSON DocumentInfo作成

			documentInfoList = documentInfoRepository.findByDocumentId(documentId);
			documentInfoForm.documentId = documentId;
			documentInfoForm.templateId = documentInfoList.get(0).getTemplateId();
			documentInfoForm.isDocumentPublish = documentInfoList.get(0).isDocumentPublish();
			documentInfoForm.contents.add(contentsForm);

		}

		return documentInfoForm;
	}
}