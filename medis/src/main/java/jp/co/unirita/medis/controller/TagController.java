package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;

@RestController
@RequestMapping(value = "/v1/tags")
public class TagController {

    @Autowired
    TagRepository tagRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getTagList(){
        return tagRepository.findBytagIdNot("s9999999999"); //コメント通知設定用のタグだけ除く
    }

}
