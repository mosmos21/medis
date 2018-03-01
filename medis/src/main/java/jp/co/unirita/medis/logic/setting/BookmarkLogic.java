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
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.form.DocumentInfoForm;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
@Transactional
public class BookmarkLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final String TYPE_CREATE_DOCUMENT = "v0000000000";
	private static final String TYPE_UPDATE_DOCUMENT = "v0000000001";

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;


	public List<DocumentInfoForm> getBookmarkList(String employeeNumber) {

		//ユーザがお気に入りしている文書idの一覧を取得
		List<Bookmark> bookmark = bookmarkRepository.findByEmployeeNumberAndSelected(employeeNumber, true);
		List<String> documentIdList = new ArrayList<>();

		for (Bookmark doclist : bookmark) {
			documentIdList.add(doclist.getDocumentId());
		}

		//各documentIdごとの最新のupdateIdをもったupdate_infoのリストの取得
		List<UpdateInfo> updateInfoList = new ArrayList<>();

		for (String docs : documentIdList) {
			updateInfoList.add(updateInfoRepository.findFirst1ByDocumentIdAndUpdateTypeBetweenOrderByUpdateIdDesc(docs, TYPE_CREATE_DOCUMENT, TYPE_UPDATE_DOCUMENT));
		}

		//updateInfoListのdocumentIdの一覧の取得
		List<String> updateDocIdList = new ArrayList<>();

		for (UpdateInfo upDocId : updateInfoList) {
			updateDocIdList.add(upDocId.getDocumentId());
		}

		//updateDocIdListのdocumentInfoの取得
		List<DocumentInfo> documentInfoList = new ArrayList<>();

		for (String updocs : updateDocIdList) {
			documentInfoList.addAll(documentInfoRepository.findByDocumentId(updocs));
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
		return documentInfoForm;
	}


	//最新のIDを生成
	public String getNewBookmarkId() throws IdIssuanceUpperException{
		List<Bookmark> bookmarkList = bookmarkRepository.findAll(new Sort(Sort.Direction.DESC, "BookmarkId"));
		if(bookmarkList.size() == 0) {
            return "m0000000000";
        }
        long idNum = Long.parseLong(bookmarkList.get(0).getBookmarkId().substring(1));
        if(idNum == 9999999999L) {
            throw new IdIssuanceUpperException("IDの発行限界");
        }
        return String.format("m%010d", idNum + 1);
    }

	public void updateBookmark(String employeeNumber, String documentId) throws IdIssuanceUpperException {
		int count = bookmarkRepository.countByEmployeeNumberAndDocumentId(employeeNumber, documentId);

		if (count == 0) {
			//最新のIDを取得し、DBに登録するするIDに変換
			Bookmark bookmark = new Bookmark();
			bookmark.setBookmarkId(getNewBookmarkId());
			bookmark.setEmployeeNumber(employeeNumber);
			bookmark.setDocumentId(documentId);
			bookmark.setSelected(true);
			bookmarkRepository.saveAndFlush(bookmark);

		} else {
			//既にあるブックマークIDのフラグを変更
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