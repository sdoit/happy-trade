package com.lyu.controller;

import com.lyu.common.CodeAndMessage;
import com.lyu.common.CommonResult;
import com.lyu.entity.SearchCandidateWord;
import com.lyu.service.SearchCandidateWordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LEE
 * @time 2023/1/26 12:20
 */
@RestController
@Validated
@RequestMapping("/api/search")
@CrossOrigin(origins = "${vue.address}")
public class SearchCandidateWordController {
    @Resource
    private SearchCandidateWordService searchCandidateWordService;

    @GetMapping("/candidate")
    public CommonResult<List<String>> getSearchCandidateWord(@NotNull String keyword) {
        List<SearchCandidateWord> candidateWords = searchCandidateWordService.getCandidateWords(keyword);
        ArrayList<String> searchCandidateWordResults = new ArrayList<>();
        for (SearchCandidateWord candidateWord : candidateWords) {
            searchCandidateWordResults.add(candidateWord.getKeyword());
        }
        return CommonResult.Result(CodeAndMessage.SUCCESS, searchCandidateWordResults);
    }
}
