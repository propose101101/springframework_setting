package com.codingrecipe.project01.controller;

import com.codingrecipe.project01.dto.MemberDTO;
import com.codingrecipe.project01.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        //System.out.println("memberDTO = " + memberDTO);
        int saveResult = memberService.save(memberDTO);
        if (saveResult > 0) {
            return "login";
        } else if (saveResult == -1) {
            System.out.println("중복됨" );
            return "index";
        } else {
            return "index";
        }
    }


    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = memberService.login(memberDTO);
        if(loginResult) {
            //session 생성
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "main";
        }else {
            return "login";
        }
    }


    @GetMapping("/")
    public String findAll(Model model) {
        List<MemberDTO> memberList = memberService.findAll();
        model.addAttribute("memberList", memberList);
        return "list";
    }
    // /member?id=1
    @GetMapping
    public String findById(@RequestParam("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        memberService.delete(id);
        return "redirect:/member/";
    }

    @GetMapping("/update")
    public String update(HttpSession session, Model model) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        System.out.println("컨트롤러 memberDTO = " + memberDTO );
        model.addAttribute("member", memberDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        boolean result = memberService.update(memberDTO);
        if(result) {
            return "redirect:/member?id=" + memberDTO.getId();
        }else{
            return "index";
        }
    }

    @PostMapping("/email-check")
    @ResponseBody
    public Map<String, Boolean> emailCheck(@RequestParam("memberEmail") String memberEmail) {
        boolean isAvailable = memberService.emailCheck(memberEmail);
        Map<String, Boolean> map = new HashMap<>();
        map.put("isAvailable", isAvailable);
        return map;
    }

    @GetMapping("/info")
    @ResponseBody
    public Map<String, Object> getMemberInfo() {
        Map<String, Object> result = new HashMap<>();
        result.put("userId", "banana123");
        result.put("userName", "홍길동");
        result.put("age", 28);
        result.put("isActive", true);
        return result;
    }


    @GetMapping("/members")
    @ResponseBody
    public List<MemberDTO> getAllMembers() {
        return memberService.findAll();
    }


}
