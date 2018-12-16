package com.example.demo.member.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.member.domain.Choice;
import com.example.demo.member.domain.Member;
import com.example.demo.member.mapper.ChoiceMapper;

@Service("choiceService")
public class ChoiceService {
	
	@Resource(name = "choiceMapper")
	private ChoiceMapper mapper;

	public ArrayList<Choice> getAll() throws Exception {
		return mapper.getAll();
	}

	public void add(Choice c) throws Exception {
		mapper.insert(c);
	}
	
	public void remove() throws Exception{
		mapper.delete();
	}
	
	public Choice search(String bz) throws Exception{
		return mapper.selectBybz(bz);
	}
	
	public int getCount() throws Exception{
		return mapper.count();
	}
	
}
