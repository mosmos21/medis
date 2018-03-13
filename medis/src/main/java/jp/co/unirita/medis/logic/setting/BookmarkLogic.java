package jp.co.unirita.medis.logic.setting;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.bookmark.Bookmark;
import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.document.DocumentInfoForm;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
@Transactional
public class BookmarkLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UserDetailRepository userDetailRepository;

	public List<DocumentInfoForm> getBookmarkList(String employeeNumber) {

		// ユーザがお気に入りしている文書の一覧を取得
		List<Bookmark> bookmark = bookmarkRepository.findByEmployeeNumberAndSelected(employeeNumber, true);
		List<DocumentInfo> documentList = new ArrayList<>();
		for (Bookmark mark : bookmark) {
			documentList.add(documentInfoRepository.findOne(mark.getDocumentId()));
		}

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
	}

	// 最新のIDを生成
	public String getNewBookmarkId() throws IdIssuanceUpperException {
		List<Bookmark> bookmarkList = bookmarkRepository.findAll(new Sort(Sort.Direction.DESC, "bookmarkId"));
		if (bookmarkList.size() == 0) {
			return "m0000000000";
		}
		long idNum = Long.parseLong(bookmarkList.get(0).getBookmarkId().substring(1));
		if (idNum == 9999999999L) {
			throw new IdIssuanceUpperException("IDの発行限界");
		}
		return String.format("m%010d", idNum + 1);
	}

	public void updateBookmark(String employeeNumber, String documentId) throws IdIssuanceUpperException {
		Bookmark info = bookmarkRepository.findByEmployeeNumberAndDocumentId(employeeNumber, documentId);

		if (info == null) {
			// 最新のIDを取得し、DBに登録
			Bookmark bookmark = new Bookmark();
			bookmark.setBookmarkId(getNewBookmarkId());
			bookmark.setEmployeeNumber(employeeNumber);
			bookmark.setDocumentId(documentId);
			bookmark.setSelected(true);
			bookmarkRepository.saveAndFlush(bookmark);

		} else {
			// 既にあるブックマークIDのフラグを変更
			Bookmark bookmarkInfo = bookmarkRepository.findByEmployeeNumberAndDocumentId(employeeNumber, documentId);
			String bookmarkId = bookmarkInfo.getBookmarkId();
			boolean flug = bookmarkInfo.isSelected();

			Bookmark bookmark = new Bookmark();
			bookmark.setBookmarkId(bookmarkId);
			bookmark.setEmployeeNumber(employeeNumber);
			bookmark.setDocumentId(documentId);
			if (flug) {
				bookmark.setSelected(false);
			} else {
				bookmark.setSelected(true);
			}
			bookmarkRepository.saveAndFlush(bookmark);
		}
	}
}