package com.demo.test;

import com.demo.jpa.model.MemberModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

class FunTest {
    private static Map<String, Sort.Direction> sort;

    private static List<MemberModel> memberList;

    @BeforeEach
    public void init(){
        sort = new HashMap<>();
        sort.put("aaa",Sort.Direction.ASC);
        sort.put("bbb",Sort.Direction.DESC);
        sort.put("ccc",Sort.Direction.ASC);

        memberList = new ArrayList<>();
        memberList.add(MemberModel.builder().cmpyNo("100").cmpyNm("델레오").mbrNo("100556677").mbrNm("정찬우").build());
        memberList.add(MemberModel.builder().cmpyNo("100").cmpyNm("델레오").mbrNo("100121234").mbrNm("홍길동").build());
        memberList.add(MemberModel.builder().cmpyNo("J01").cmpyNm("테스트").mbrNo("J01556677").mbrNm("장동건").build());
    }

    @Test
    @DisplayName("Map 집합을 특정 조건으로 filter 링 Map 재 집합")
    public void mapFilterTest(){

        BiConsumer<String, Sort.Direction> mapSum = (key,value) -> {
            System.out.println(key + " - " + value);
        };

        sort.forEach( (key, value) -> mapSum.accept(key,value) );

        System.out.println("---------------------------------");

        Map<String, Sort.Direction> sort2 = sort.entrySet().stream()
                .filter(e -> !e.getKey().equals("aaa"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        sort2.forEach( (key, value) -> mapSum.accept(key,value) );
    }

    @Test
    @DisplayName("메소드 참조 테스트")
    public void methodReferenceTest(){

        Function<String, List<MemberModel>> getMemberList
                = cmpyNo -> memberList.stream()
                                      .filter(m -> m.getCmpyNo().equals(cmpyNo))
                                      .collect(Collectors.toList())
        ;

        List<MemberModel> findMemberList = getMemberList.apply("100");
        System.out.println("---------------------------------");
        System.out.println("memberList.size = " + memberList.size());
        memberList.forEach(System.out::println);
        System.out.println("---------------------------------");
        System.out.println("findMemberList.size = " + findMemberList.size());
        findMemberList.forEach(System.out::println);
        System.out.println("---------------------------------");
        Function<String, MemberModel> getMember
                = mbrNo -> memberList.stream()
                                      .filter(m -> m.getMbrNo().equals(mbrNo))
                                      .findFirst().get();

        MemberModel member = getMember.apply("100556677");
        System.out.println(member);
        System.out.println("---------------------------------");
    }

    @Test
    public void test() {
        System.out.println("develop branch test code..");
    }

}
