package jp.co.unirita.medis.logic.system;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<SnackbarNotificationsForm> updatetypeConfirmation(String employeeNumber, String updateId) {
		List<UpdateInfo> newUpdateId = updateInfoRepository.findByUpdateIdAfter(updateId);
		List<SnackbarNotificationsForm> result = new ArrayList<>();

		for (UpdateInfo add : newUpdateId) {
			List<SnackbarNotificationsForm> snackbarNotificationsForm = new ArrayList<>();
			if (add.getUpdateType().equals("v0000000000")) {
				snackbarNotificationsForm = documentContributionNotificationSnackbar(employeeNumber, add.getUpdateId(),"v0000000000");
			} else if (add.getUpdateType().equals("v0000000001")) {
				snackbarNotificationsForm = documentContributionNotificationSnackbar(employeeNumber, add.getUpdateId(),"v0000000001");
			} else if (add.getUpdateType().equals("v0000000002")) {
				snackbarNotificationsForm = documentContributionNotificationSnackbar(employeeNumber, add.getUpdateId(),"v0000000002");
			} else {
				snackbarNotificationsForm = documentContributionNotificationSnackbar(employeeNumber, add.getUpdateId(),"v0000000003");
			}
			result.addAll(snackbarNotificationsForm);
		}
		result = result.stream().distinct().collect(Collectors.toList());

		return result;

	}

	/**
	 * Snackbarを表示するためのロジック
	 *
	 * @param employeeNumber ログインユーザの社員番号
	 * @param updateId ログインユーザがもつ最新のupdateId
	 */

	public List<SnackbarNotificationsForm> documentContributionNotificationSnackbar(String employeeNumber,
			String updateId, String updateType) {
		List<SnackbarNotificationsForm> result = new ArrayList<>();

		System.out.println(updateId);
		String documentId = updateInfoRepository.findOne(updateId).getDocumentId();
		DocumentInfo documentIdInfo = documentInfoRepository.findByDocumentPublishAndDocumentId(true, documentId);

		if (!(documentIdInfo == null)) {

			// ドキュメントについたタグ一覧
			List<DocumentTag> documentTagList = documentTagRepository
					.findByDocumentId(documentIdInfo.getDocumentId());

			// ユーザの監視タグ一覧
			List<String> userNotificationTagList = notificationConfigRepository.findByEmployeeNumber(employeeNumber)
					.stream().map(NotificationConfig::getTagId).collect(Collectors.toList());

			for (DocumentTag add : documentTagList) {
				if (userNotificationTagList.contains(add.getTagId())) {
					SnackbarNotificationsForm snackbarNotificationsForm = new SnackbarNotificationsForm();
					snackbarNotificationsForm.setDocumentId(add.getDocumentId());
					snackbarNotificationsForm.setUpdateType(updateType);
					snackbarNotificationsForm
							.setDocumentName(documentInfoRepository.findOne(add.getDocumentId()).getDocumentName());

					result.add(snackbarNotificationsForm);
				}
			}
		}
		return result;

	}

}
