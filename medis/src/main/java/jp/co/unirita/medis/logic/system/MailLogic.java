package jp.co.unirita.medis.logic.system;

import jp.co.unirita.medis.config.ClientPathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailLogic {

    private final static String BR = "\r\n\n";

    @Autowired
    ClientPathUtil clientPathUtil;
    @Autowired
    private MailSender sender;

    public void sendResetPasswordLink(String mailAddress, String key) {
        SimpleMailMessage msg = new SimpleMailMessage();
        String url = clientPathUtil.getPath() + "/resetpass?secret=" + key;

        msg.setTo(mailAddress);
        msg.setSubject("【MEDIS】パスワード設定用URL");
        msg.setText(new StringBuilder()
                .append("MEDISのパスワードリセットメールです。").append(BR)
                .append("以下のURLにてパスワードを設定することができます。").append(BR)
                .append(BR)
                .append(url)
                .append("有効期間は30分です。有効期限が過ぎた場合はメールを再送してください。")
                .toString());
        sender.send(msg);
    }

    public void sendDocumentContributionNotification(
            String mailAddress, String documentId, String documentName, String employeeNumber, String name) {
        SimpleMailMessage msg = new SimpleMailMessage();
        String url = clientPathUtil.getPath() + "/browsing/" + documentId;

        msg.setTo(mailAddress);
        msg.setSubject("【MEDIS】文書投稿通知");
        msg.setText(new StringBuilder()
                .append("あなたが監視しているタグの文書が投稿されました。").append(BR)
                .append(BR)
                .append("【文書タイトル】").append(BR)
                .append("\t").append("「").append(documentName).append("」").append(BR)
                .append("【投稿者】").append(BR)
                .append("\t").append("社員番号: ").append(employeeNumber).append(BR)
                .append("\t").append("氏名: ").append(name).append(BR)
                .append(BR)
                .append("以下のリンクをクリックして文書を確認してください。").append(BR)
                .append(url)
                .toString());
        sender.send(msg);
    }

    public void sendDocumentUpdateNotification(
            String mailAddress, String documentId, String documentName, String employeeNumber, String name) {
        SimpleMailMessage msg = new SimpleMailMessage();
        String url = clientPathUtil.getPath() + "/browsing/" + documentId;

        msg.setTo(mailAddress);
        msg.setSubject("【MEDIS】文書更新通知");
        msg.setText(new StringBuilder()
                .append("あなたが監視しているタグの文書が更新されました。").append(BR)
                .append(BR)
                .append("【文書タイトル】").append(BR)
                .append("\t").append("「").append(documentName).append("」").append(BR)
                .append("【投稿者】").append(BR)
                .append("\t").append("社員番号: ").append(employeeNumber).append(BR)
                .append("\t").append("氏名: ").append(name).append(BR)
                .append(BR)
                .append("以下のリンクをクリックして文書を確認してください。").append(BR)
                .append(url)
                .toString());
        sender.send(msg);
    }

    public void sendCommentNotification(
            String mailAddress, String documentId, String documentName, String employeeNumber, String name) {
        SimpleMailMessage msg = new SimpleMailMessage();
        String url = clientPathUtil.getPath() + "/browsing/" + documentId;

        msg.setTo(mailAddress);
        msg.setSubject("【MEDIS】コメント通知");
        msg.setText(new StringBuilder()
                .append("あなたが投稿した文書にコメントが付きました。").append(BR)
                .append(BR)
                .append("【文書タイトル】").append(BR)
                .append("\t").append("「").append(documentName).append("」").append(BR)
                .append("【コメント者】").append(BR)
                .append("\t").append("社員番号: ").append(employeeNumber).append(BR)
                .append("\t").append("氏名: ").append(name).append(BR)
                .append(BR)
                .append("以下のリンクをクリックして文書を確認してください。").append(BR)
                .append(url)
                .toString());
        sender.send(msg);
    }

    public void sendCommentReadNotification(
            String mailAddress, String documentId, String documentName, String employeeNumber, String name) {
        SimpleMailMessage msg = new SimpleMailMessage();
        String url = clientPathUtil.getPath() + "/browsing/" + documentId;

        msg.setTo(mailAddress);
        msg.setSubject("【MEDIS】コメント既読通知");
        msg.setText(new StringBuilder()
                .append("あなたが書いたコメントに既読が付きました。").append(BR)
                .append(BR)
                .append("【文書タイトル】").append(BR)
                .append("\t").append("「").append(documentName).append("」").append(BR)
                .append("【投稿者】").append(BR)
                .append("\t").append("社員番号: ").append(employeeNumber).append(BR)
                .append("\t").append("氏名: ").append(name).append(BR)
                .append(BR)
                .append("以下のリンクをクリックして文書を確認してください。").append(BR)
                .append(url)
                .toString());
        sender.send(msg);
    }
}
