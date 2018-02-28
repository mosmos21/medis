package jp.co.unirita.medis.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.logic.util.TagLogic;

@RestController
@RequestMapping(value = "/v1/tags")
public class TagController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    TagLogic tagLogic;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getTagList(){
        return tagLogic.getTagList();

    }
}
