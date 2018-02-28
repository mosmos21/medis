package jp.co.unirita.medis.logic.setting;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.bookmark.Bookmark;
import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.form.DocumentInfoForm;

@Service
@Transactional
public class BookmarkLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;


	public List<DocumentInfoForm> getBookmarkList(String employeeNumber, Integer maxSize) {

		//ユーザがお気に入りしている文書idの一覧を取得
		List<Bookmark> bookmark = bookmarkRepository.findByEmployeeNumberAndSelected(employeeNumber, true);
		List<String> documentIdList = new ArrayList<>();

		for (Bookmark doclist : bookmark) {
			documentIdList.add(doclist.getDocumentId());
		}

		//各documentIdごとの最新のupdateIdをもったupdate_infoのリストの取得
		List<UpdateInfo> updateInfoList = new ArrayList<>();

		for (int i = 0; i < documentIdList.size(); i++) {
			updateInfoList.addAll(updateInfoRepository.findFirst1ByDocumentIdAndUpdateTypeBetweenOrderByUpdateIdDesc(documentIdList.get(i), "v0000000000", "v0000000001"));
		}

		//updateInfoListのdocumentIdの一覧の取得
		List<String> updateDocIdList = new ArrayList<>();

		for (UpdateInfo upDocId : updateInfoList) {
			updateDocIdList.add(upDocId.getDocumentId());
		}

		//updateDocIdListのdocumentInfoの取得
		List<DocumentInfo> documentInfoList = new ArrayList<>();

		for (int i = 0; i < updateDocIdList.size(); i++) {
			documentInfoList.addAll(documentInfoRepository.findByDocumentId(updateDocIdList.get(i)));
		}

		//documentInfoListとupdateInfoListの値をDocumentInfoFormに格納
		List<DocumentInfoForm> documentInfoForm = new ArrayList<>();

		for(int i = 0; i < documentInfoList.size(); i++) {
			documentInfoForm.add(new DocumentInfoForm(documentInfoList.get(i), updateInfoList.get(i)));
		}

		documentInfoForm.sort(new Comparator<DocumentInfoForm>(){
			@Override
			public int compare(DocumentInfoForm i1, DocumentInfoForm i2) {
				return i2.getUpdateDate().compareTo(i1.getUpdateDate());
			}
		});

		if (maxSize != -1 && documentInfoForm.size() > maxSize) {
			documentInfoForm = documentInfoForm.subList(0, maxSize);
		}

		return documentInfoForm;
	}


	public void updateBookmark(String employeeNumber, String documentId) {
		int count = bookmarkRepository.countByEmployeeNumberAndDocumentId(employeeNumber, documentId);

		if (count == 0) {
			//最新のIDを取得し、DBに登録するするIDに変換
			List<Bookmark> bookmarkList = bookmarkRepository.findByOrderByBookmarkIdDesc();

			List<String> bookmarkIdList = new ArrayList<>();
			for (Bookmark bookmark : bookmarkList) {
				bookmarkIdList.add(bookmark.getBookmarkId());
			}

			String lastBookmarkId = bookmarkIdList.get(0);
			String head = lastBookmarkId.substring(0, 1);
			String body = lastBookmarkId.substring(1,11);
			int temp = Integer.parseInt(body);
			temp++;
			body = String.format("%010d", temp);
			lastBookmarkId = head + body;

			Bookmark bookmark = new Bookmark();
			bookmark.setBookmarkId(lastBookmarkId);
			bookmark.setEmployeeNumber(employeeNumber);
			bookmark.setDocumentId(documentId);
			bookmark.setSelected(true);

			bookmarkRepository.saveAndFlush(bookmark);

		} else {
			//既にあるブックマークIDのフラグを変更
			List<Bookmark> bookmarkList = bookmarkRepository.findByEmployeeNumberAndDocumentId(employeeNumber, documentId);

			String bookmarkId = bookmarkList.get(0).getBookmarkId();

			boolean flug = bookmarkList.get(0).isSelected();

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