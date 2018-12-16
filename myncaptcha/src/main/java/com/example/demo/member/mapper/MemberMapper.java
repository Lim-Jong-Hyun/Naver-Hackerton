package com.example.demo.member.mapper;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.example.demo.member.domain.Member;

/*
 * DAO & MAPPER
 */
@Repository("memberMapper")
public interface MemberMapper {

	public void insert(Member m) throws Exception;

	public List<Member> selectAll() throws Exception;

	public Member select(String id) throws Exception;
}
