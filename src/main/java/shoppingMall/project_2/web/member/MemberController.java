package shoppingMall.project_2.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shoppingMall.project_2.domain.member.Member;
import shoppingMall.project_2.repository.member.MemberUpdateForm;
import shoppingMall.project_2.service.MemberService;
import shoppingMall.project_2.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static shoppingMall.project_2.domain.member.Grade.VIP;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {


    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute Member member, BindingResult bindingResult) {
        //userId, username, password 검증

        //비밀번호는 10자리 이하
        if (member.getPassword().length() > 10) {
            bindingResult.rejectValue("password", "range", new Object[]{0, 10}, null);
        }
        if (member.getUserId().length() > 10) {
            bindingResult.rejectValue("userId", "range", new Object[]{10}, null);
        }

        if (member.getUsername().length() > 10) {
            bindingResult.rejectValue("username", "range", new Object[]{10}, null);
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult ={}", bindingResult);

            return "members/addMemberForm";
        }


        member.setGrade(VIP);
        memberService.save(member);

        return "redirect:/";
    }

    //비밀번호 수정 (update 부분)
    @GetMapping("/update")
    public String updateForm(@ModelAttribute MemberUpdateForm memberUpdateForm) {
        return "members/updateMemberForm";
    }

    @PostMapping("/update")
    public String updatePwd(@Validated @ModelAttribute MemberUpdateForm memberUpdateForm, BindingResult bindingResult, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Member FindMember = memberService.findByloginId(sessionMember.getUserId()).get();

        if (!FindMember.getUserId().equals(memberUpdateForm.getUserId())) {
            bindingResult.rejectValue("userId", "check.member.userid", null, null);

        }
        if (!FindMember.getPassword().equals(memberUpdateForm.getOldPassword())) {
            bindingResult.rejectValue("oldPassword", "check.member.oldPassword", null, null);
        }
        if (memberUpdateForm.getNewPassword().length() > 10) {
            bindingResult.rejectValue("newPassword", "range.member.password", new Object[]{0, 10}, null);
        }

        if (!memberUpdateForm.getNewCheckPassword().equals(memberUpdateForm.getNewPassword())) {
            bindingResult.rejectValue("newCheckPassword", "check.member.password", null);
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult ={}", bindingResult);
            return "members/updateMemberForm";
        }

        memberService.update(memberUpdateForm.getUserId(), memberUpdateForm.getNewPassword());

        return "redirect:/";
    }

    //회원 탈퇴
    @PostMapping("/delete")
    public String deleteMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        memberService.deleteById(member.getUserId(),member.getPassword());
        session.invalidate();

        return "redirect:/";
    }

}
