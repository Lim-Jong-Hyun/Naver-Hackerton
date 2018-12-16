package com.example.demo.member.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.example.demo.member.domain.Choice;

@Repository("choiceMapper")
public interface ChoiceMapper {

	void insert(Choice c);

	ArrayList<Choice> getAll();

	Choice selectBybz(String bz);

	void delete();
	
	int count();
}
