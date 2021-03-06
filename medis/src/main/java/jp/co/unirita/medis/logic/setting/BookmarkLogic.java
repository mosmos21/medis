package jp.co.unirita.medis.logic.setting;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.unirita.medis.logic.util.IdIssuanceLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.bookmark.Bookmark;
import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.document.DocumentInfoForm;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
@Transactional(rollbackFor = Exception.class)
public class BookmarkLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	IdIssuanceLogic idIssuanceLogic;

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UserDetailRepository userDetailRepository;

	public List<DocumentInfoForm> getBookmarkList(String employeeNumber) {
		try {
			// ユーザがお気に入りしている文書の一覧を取得
			List<Bookmark> bookmark = bookmarkRepository.findByEmployeeNumberAndSelected(employeeNumber, true);
			List<DocumentInfo> documentList = new ArrayList<>();
			for (Bookmark mark : bookmark) {
				documentList.add(documentInfoRepository.findOne(mark.getDocumentId()));
			}
			documentList = documentList.stream().filter(info -> info.isDocumentPublish() == true).collect(Collectors.toList());

			//userDetailの取得
			List<UserDetail> userDetail = new ArrayList<>();
			for (DocumentInfo docInfo : documentList) {
				userDetail.add(userDetailRepository.findOne(docInfo.getEmployeeNumber()));
			}

			//documentListとuserDetailの値をDocumentInfoFormに格納
			List<DocumentInfoForm> form = new ArrayList<>();

			for (int i = 0; i < documentList.size(); i++) {
				form.add(new DocumentInfoForm(documentList.get(i), userDetail.get(i)));
			}
			form.sort(Comparator.comparing(DocumentInfoForm::getDocumentCreateDate).reversed());

			return form;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: BookmarkLogic, method: getBookmarkList]");
			throw new DBException("DB Runtime Error[class: BookmarkLogic, method: getBookmarkList]");
		}
	}

	public void updateBookmark(String employeeNumber, String documentId, boolean selected) throws IdIssuanceUpperException {
		try {
			Bookmark info = bookmarkRepository.findByEmployeeNumberAndDocumentId(employeeNumber, documentId);
			System.out.println("info = " + info);
			if (info == null) {
				// 最新のIDを取得し、DBに登録
				Bookmark bookmark = new Bookmark();
				bookmark.setBookmarkId(idIssuanceLogic.createBookmarkId());
				bookmark.setEmployeeNumber(employeeNumber);
				bookmark.setDocumentId(documentId);
				bookmark.setSelected(true);
				bookmarkRepository.saveAndFlush(bookmark);
			} else {
				// 既にあるブックマークIDのフラグを変更
				Bookmark bookmark = bookmarkRepository.findByEmployeeNumberAndDocumentId(employeeNumber, documentId);
				bookmark.setSelected(selected);
				bookmarkRepository.saveAndFlush(bookmark);
			}
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: BookmarkLogic, method: updateBookmark]");
			throw new DBException("DB Runtime Error[class: BookmarkLogic, method: updateBookmark]");
		}
	}
}