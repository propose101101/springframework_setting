package com.codingrecipe.project01.repository;

import com.codingrecipe.project01.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    public int save(MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        return 0;
    }
}
