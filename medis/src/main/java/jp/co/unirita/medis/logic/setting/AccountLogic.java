package jp.co.unirita.medis.logic.setting;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import jp.co.unirita.medis.util.exception.NotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfo;
import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;

@Service
@SpringBootApplication
public class AccountLogic {

	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	TempkeyInfoRepository tempkeyInfoRepository;

	public void userCheck(UserDetail userDetail) throws NotExistException {
		if (userDetailRepository.findByEmployeeNumberAndMailaddress(userDetail.getEmployeeNumber(),
				userDetail.getMailaddress()) == null)
			throw new NotExistException("employeeNumber or mailaddress",
					userDetail.getEmployeeNumber() + " or " + userDetail.getMailaddress(),
					"ユーザ情報の確認に失敗しました。入力内容に誤りがあります。");
		;
		TempkeyInfo info = new TempkeyInfo();
		info.setEmployeeNumber(userDetail.getEmployeeNumber());
		info.setTempKey(UUID.randomUUID().toString().replace("-", ""));
		info.setChangeDate(new Timestamp(System.currentTimeMillis()));
		tempkeyInfoRepository.saveAndFlush(info);
		sendMail(info.getTempKey(),userDetail.getMailaddress());
	}

	public UserDetail keyCheck(String key) {
		TempkeyInfo info = tempkeyInfoRepository.findByTempKey(key);
		if(new Timestamp(System.currentTimeMillis() - 30).after(info.getChangeDate())){

		};
		List<UserDetail> detail = userDetailRepository.findAllByEmployeeNumber(info.getEmployeeNumber());
		return(detail.get(0));
	}

	public void passwordReset(UserDetail userDetail) {

	}

    @Autowired
    private MailSender sender;

    public void sendMail(String key,String mailaddress) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(mailaddress);
        msg.setSubject("【MEDIS】パスワード再設定用URLアドレス"); //タイトルの設定
        msg.setText("MEDISのパスワードリセットメールです。\r\n\r\n"
        			+ "以下のURLにてパスワードを再設定することができます。\r\n\r\n"
        			+ "http://localhost:8080/v1/keycheck?secret=" + key + "\r\n\r\n"
        			+ "有効期間は30分です。それ以降は再送してください。"); //本文の設定

        this.sender.send(msg);
    }
}