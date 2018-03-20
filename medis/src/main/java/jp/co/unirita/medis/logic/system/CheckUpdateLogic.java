package jp.co.unirita.medis.logic.system;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
import jp.co.unirita.medis.form.setting.SnackbarNotificationsForm;

@Service
public class CheckUpdateLogic {

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
	 * コメント通知のSnackbarを表示するためのロジック
	 *
	 * @param employeeNumber
	 *            ログインユーザの社員番号
	 * @param updateId
	 *            ログインユーザがもつ最新のupdateId
	 */

	public List<SnackbarNotificationsForm> commentNotificationSnackbar(String employeeNumber, String updateId,
			String updateType) {
		List<SnackbarNotificationsForm> commentResult = new ArrayList<>();
		String documentId = updateInfoRepository.findOne(updateId).getDocumentId();
		String authorEmployeeNumber = documentInfoRepository.findOne(documentId).getEmployeeNumber();

		if (employeeNumber.equals(authorEmployeeNumber)) {
			SnackbarNotificationsForm snackbarNotificationsForm = new SnackbarNotificationsForm(documentId, updateType,
					documentInfoRepository.findOne(documentId).getDocumentName(), getLatestUpdateId());
			commentResult.add(snackbarNotificationsForm);

		}

		return commentResult;
	}

	/**
	 * コメント既読の通知Snackbarを表示するためのロジック
	 *
	 * @param employeeNumber
	 *            ログインユーザの社員番号
	 * @param updateId
	 *            ログインユーザがもつ最新のupdateId
	 */

	private List<SnackbarNotificationsForm> commentReadNotificationSnackbar(String employeeNumber, String updateId,
			String updateType) {
		List<SnackbarNotificationsForm> commentReadResult = new ArrayList<>();
		UpdateInfo updateInfo = updateInfoRepository.findOne(updateId);
		if (updateInfo.getEmployeeNumber().equals(employeeNumber)) {
			SnackbarNotificationsForm snackbarNotificationsForm = new SnackbarNotificationsForm(
					updateInfo.getDocumentId(), updateType,
					documentInfoRepository.findOne(updateInfo.getDocumentId()).getDocumentName(), getLatestUpdateId());
			commentReadResult.add(snackbarNotificationsForm);

		}

		return commentReadResult;
	}

	/**
	 * タグに関するSnackbarを表示するためのロジック
	 *
	 * @param employeeNumber
	 *            ログインユーザの社員番号
	 * @param updateId
	 *            ログインユーザがもつ最新のupdateId
	 */

	public List<SnackbarNotificationsForm> tagNotificationSnackbar(String employeeNumber, String updateId,
			String updateType) {
		List<SnackbarNotificationsForm> tagResult = new ArrayList<>();
		String documentId = updateInfoRepository.findOne(updateId).getDocumentId();
		DocumentInfo documentIdInfo = documentInfoRepository.findByDocumentPublishAndDocumentId(true, documentId);

		if (!(documentIdInfo == null)) {
			// ドキュメントについたタグ一覧
			List<DocumentTag> documentTagList = documentTagRepository.findByDocumentId(documentIdInfo.getDocumentId());
			// ユーザの監視タグ一覧
			List<String> userNotificationTagList = notificationConfigRepository.findByEmployeeNumber(employeeNumber)
					.stream().map(NotificationConfig::getTagId).collect(Collectors.toList());

			for (DocumentTag add : documentTagList) {
				if (userNotificationTagList.contains(add.getTagId())) {
					SnackbarNotificationsForm snackbarNotificationsForm = new SnackbarNotificationsForm(
							add.getDocumentId(), updateType,
							documentInfoRepository.findOne(add.getDocumentId()).getDocumentName(), getLatestUpdateId());
					tagResult.add(snackbarNotificationsForm);

				}
			}
		}

		return tagResult;
	}

	public List<SnackbarNotificationsForm> updatetypeConfirmation(String employeeNumber, String updateId) {
		List<UpdateInfo> newUpdateId = updateInfoRepository.findByUpdateIdAfter(updateId);
		List<SnackbarNotificationsForm> result = new ArrayList<>();

		for (UpdateInfo add : newUpdateId) {
			List<SnackbarNotificationsForm> snackbarNotificationsForm = new ArrayList<>();
			if (add.getUpdateType().equals("v0000000000")) {
				snackbarNotificationsForm = tagNotificationSnackbar(employeeNumber, add.getUpdateId(), "v0000000000");
			} else if (add.getUpdateType().equals("v0000000001")) {
				snackbarNotificationsForm = tagNotificationSnackbar(employeeNumber, add.getUpdateId(), "v0000000001");
			} else if (add.getUpdateType().equals("v0000000002")) {
				snackbarNotificationsForm = commentNotificationSnackbar(employeeNumber, add.getUpdateId(),
						"v0000000002");
			} else {
				snackbarNotificationsForm = commentReadNotificationSnackbar(employeeNumber, add.getUpdateId(),
						"v0000000003");
			}
			result.addAll(snackbarNotificationsForm);

		}
		result = result.stream().distinct().collect(Collectors.toList());

		return result;
	}

	public String getLatestUpdateId() {
		String latestUpdateId = updateInfoRepository.findAll(new Sort(Sort.Direction.DESC, "updateId")).get(0)
				.getUpdateId();

		return latestUpdateId;
	}
}
