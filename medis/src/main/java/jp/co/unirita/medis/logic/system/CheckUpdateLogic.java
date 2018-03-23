package jp.co.unirita.medis.logic.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfig;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
import jp.co.unirita.medis.domain.templatetag.TemplateTagRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.system.SnackbarNotificationsForm;
import jp.co.unirita.medis.util.exception.DBException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CheckUpdateLogic {
	private static final String TYPE_CREATE_DOCUMENT = "v0000000000";
	private static final String TYPE_UPDATE_DOCUMENT = "v0000000001";
	private static final String TYPE_COMMENT_DOCUMENT = "v0000000002";
	private static final String TYPE_COMMNETREAD_DOCUMENT = "v0000000003";

	@Autowired
	MailLogic mailLogic;

	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	TemplateTagRepository templateTagRepository;
	@Autowired
	DocumentTagRepository documentTagRepository;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;

	/**
	 * 自分のドキュメントについてコメントがついた場合に情報を取得
	 *
	 * @param employeeNumber ログインユーザの社員番号
	 * @param updateId ログインユーザがもつ最新のupdateId
	 */

	private List<SnackbarNotificationsForm> getCommentSnackbar(String employeeNumber, String updateId, String updateType) {
		try {
			List<SnackbarNotificationsForm> commentResult = new ArrayList<>();
			String documentId = updateInfoRepository.findOne(updateId).getDocumentId();
			String authorEmployeeNumber = documentInfoRepository.findOne(documentId).getEmployeeNumber();
			String latestUpdateId = getLatestUpdateId().get("updateId");

			if (employeeNumber.equals(authorEmployeeNumber)) {
				SnackbarNotificationsForm snackbarNotificationsForm = new SnackbarNotificationsForm(documentId, updateType,
						latestUpdateId, documentInfoRepository.findOne(documentId).getDocumentName());
				commentResult.add(snackbarNotificationsForm);
			}

			return commentResult;
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: CheckUpdateLogic, method: getCommentSnackbar]");
		}
	}

	/**
	 * 自分のコメントに既読がついている場合に情報を取得
	 *
	 * @param employeeNumber ログインユーザの社員番号
	 * @param updateId ログインユーザがもつ最新のupdateId
	 */

	private List<SnackbarNotificationsForm> getCommentReadSnackbar(String employeeNumber, String updateId, String updateType) {
		try {
			List<SnackbarNotificationsForm> commentReadResult = new ArrayList<>();
			UpdateInfo updateInfo = updateInfoRepository.findOne(updateId);
			String latestUpdateId = getLatestUpdateId().get("updateId");

			if (updateInfo.getEmployeeNumber().equals(employeeNumber)) {
				SnackbarNotificationsForm snackbarNotificationsForm = new SnackbarNotificationsForm(
						updateInfo.getDocumentId(), updateType, latestUpdateId,
						documentInfoRepository.findOne(updateInfo.getDocumentId()).getDocumentName());
				commentReadResult.add(snackbarNotificationsForm);
			}

			return commentReadResult;
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: CheckUpdateLogic, method: getCommentReadSnackbar]");
		}
	}

	/**
	 * 新規、更新文書に監視タグがついていた場合に情報を取得
	 *
	 * @param employeeNumber ログインユーザの社員番号
	 * @param updateId ログインユーザがもつ最新のupdateId
	 */

	private List<SnackbarNotificationsForm> getTagSnackbar(String employeeNumber, String updateId, String updateType) {
		try {
			List<SnackbarNotificationsForm> tagResult = new ArrayList<>();
			String documentId = updateInfoRepository.findOne(updateId).getDocumentId();
			String latestUpdateId = getLatestUpdateId().get("updateId");

			DocumentInfo documentIdInfo = documentInfoRepository.findByDocumentPublishAndDocumentId(true, documentId);

		    if (documentIdInfo != null) {
				// ドキュメントについたタグ一覧
				List<DocumentTag> documentTagList = documentTagRepository.findByDocumentId(documentIdInfo.getDocumentId());
				// ユーザの監視タグ一覧
				List<String> userNotificationTagList = notificationConfigRepository.findByEmployeeNumber(employeeNumber)
						.stream().map(NotificationConfig::getTagId).collect(Collectors.toList());

				for (DocumentTag add : documentTagList) {
					if (userNotificationTagList.contains(add.getTagId())) {
						SnackbarNotificationsForm snackbarNotificationsForm = new SnackbarNotificationsForm(
								add.getDocumentId(), updateType, latestUpdateId,
								documentInfoRepository.findOne(add.getDocumentId()).getDocumentName());
						tagResult.add(snackbarNotificationsForm);
					}
				}
		    }

			return tagResult;
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: CheckUpdateLogic, method: getTagSnackbar]");
		}
	}

	/**
	 * ログインユーザがもつUpdateID以降の情報を取得する
	 *
	 * @param employeeNumber ログインユーザの社員番号
	 * @param updateId ログインユーザがもつ最新のupdateId
	 */

	public List<SnackbarNotificationsForm> updateTypeConfirmation(String employeeNumber, String updateId) {
		try {
			List<UpdateInfo> newUpdateId = updateInfoRepository.findByUpdateIdAfter(updateId);
			List<SnackbarNotificationsForm> result = new ArrayList<>();

			for (UpdateInfo add : newUpdateId) {
				List<SnackbarNotificationsForm> snackbarNotificationsForm;

				switch (add.getUpdateType()) {
				case TYPE_CREATE_DOCUMENT:
					snackbarNotificationsForm = getTagSnackbar(employeeNumber, add.getUpdateId(), TYPE_CREATE_DOCUMENT);
					break;

				case TYPE_UPDATE_DOCUMENT:
					snackbarNotificationsForm = getTagSnackbar(employeeNumber, add.getUpdateId(), TYPE_UPDATE_DOCUMENT);
					break;

				case TYPE_COMMENT_DOCUMENT:
					snackbarNotificationsForm = getCommentSnackbar(employeeNumber, add.getUpdateId(),
							TYPE_COMMENT_DOCUMENT);
					break;

				default:
					snackbarNotificationsForm = getCommentReadSnackbar(employeeNumber, add.getUpdateId(),
							TYPE_COMMNETREAD_DOCUMENT);
				}
				result.addAll(snackbarNotificationsForm);
			}

			return result.stream().distinct().collect(Collectors.toList());
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: CheckUpdateLogic, method: updateTypeConfirmation]");
		}
	}

	/**
	 * 	最新のUpdateIDを取得する
	 */

	public Map<String, String> getLatestUpdateId() {
		try {
			HashMap<String, String> updateId = new HashMap<String, String>();
			List<String> updateInfo = updateInfoRepository.findAll(new Sort(Sort.Direction.DESC, "updateId")).stream()
					.map(UpdateInfo::getUpdateId).collect(Collectors.toList());
			updateId.put("updateId", updateInfo.get(0));

			return updateId;
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: CheckUpdateLogic, method: getLatestUpdateId]");
		}
	}
}
