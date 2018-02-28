package jp.co.unirita.medis.logic.setting;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfo;
import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfoRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.AccountForm;
import jp.co.unirita.medis.util.exception.NotExistException;

@Service
@SpringBootApplication
public class AccountLogic {

	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	TempkeyInfoRepository tempkeyInfoRepository;
	@Autowired
	UserRepository userRepository;

	public void userCheck(AccountForm form) throws NotExistException {
		if (userDetailRepository.findByEmployeeNumberAndMailaddress(form.getEmployeeNumber(),
				form.getMailaddress()) == null)
			throw new NotExistException("employeeNumber or mailaddress",
					form.getEmployeeNumber() + " or " + form.getMailaddress(),
					"ユーザ情報の確認に失敗しました。入力内容に誤りがあります。");
		;
		TempkeyInfo info = new TempkeyInfo();
		info.setEmployeeNumber(form.getEmployeeNumber());
		info.setTempKey(UUID.randomUUID().toString().replace("-", ""));
		info.setChangeDate(new Timestamp(System.currentTimeMillis()));
		tempkeyInfoRepository.delete(tempkeyInfoRepository.findByEmployeeNumber(form.getEmployeeNumber())); // 上書きするとエラーになるので仮措置
		tempkeyInfoRepository.saveAndFlush(info);
		sendMail(info.getTempKey(),form.getMailaddress());
	}

	public UserDetail keyCheck(String key) throws NotExistException {
		TempkeyInfo info = tempkeyInfoRepository.findByTempKey(key);
		// System.out.println(new SimpleDateFormat("yyyy/MM/dd
		// HH:mm:ss").format(System.currentTimeMillis() - 1800000));
		if (key == null || key.length() != 32 || info == null
				|| new Timestamp(System.currentTimeMillis() - 1800000).after(info.getChangeDate())) {
			throw new NotExistException("temp_key", key, "メール発行から30分を過ぎているか、URLアドレスに誤りがあります。");
		}
		;
		UserDetail detail = userDetailRepository.findByEmployeeNumber(info.getEmployeeNumber());
		return(detail);
	}

	public void passwordReset(AccountForm form) throws NotExistException {
		if (userDetailRepository.findByEmployeeNumberAndMailaddress(form.getEmployeeNumber(),
				form.getMailaddress()) == null)
			throw new NotExistException("employeeNumber or mailaddress",
					form.getEmployeeNumber() + " or " + form.getMailaddress(),
					"ユーザ情報の確認に失敗しました。入力内容に誤りがあります。");
		;
		User user = userRepository.findFirstByEmployeeNumber(form.getEmployeeNumber());
		user.setPassword(form.getPassword());
		userRepository.saveAndFlush(user);

	}

    @Autowired
    private MailSender sender;

    public void sendMail(String key,String mailaddress) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(mailaddress);
        msg.setSubject("【MEDIS】パスワード再設定用URLアドレス"); //タイトルの設定
        msg.setText("MEDISのパスワードリセットメールです。\r\n\r\n"
        			+ "以下のURLにてパスワードを再設定することができます。\r\n\r\n"
        			+ "http://localhost:8080/v1/accounts/keycheck?secret=" + key + "\r\n\r\n"
        			+ "有効期間は30分です。それ以降は再送してください。"); //本文の設定

        this.sender.send(msg);
    }
}