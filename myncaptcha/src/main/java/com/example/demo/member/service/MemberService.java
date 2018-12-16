package com.example.demo.member.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.member.domain.Member;
import com.example.demo.member.mapper.MemberMapper;

@Service("memberService")
public class MemberService {

	@Resource(name = "memberMapper")
	private MemberMapper mapper;

	public List<Member> getAll() throws Exception {
		return mapper.selectAll();
	}

	public void add(Member m) throws Exception {
		mapper.insert(m);
	}
	
	public boolean login(Member m) throws Exception {
		Member e = new Member();
		e = mapper.select(m.getId());
		if(e!=null && e.getPwd().equals(m.getPwd())) {
			return true;
		}
		return false;
		
	}
	
}
