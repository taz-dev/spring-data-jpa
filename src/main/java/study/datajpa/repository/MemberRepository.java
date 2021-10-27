package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //JpaRepository<엔티티, 엔티티에 매핑된 PK 타입>

    //주요 메서드
    //save(S) : 새로운 엔티티는 저장하고 이미 있는 엔티티는 병합
    //delete(T) : 엔티티 하나를 삭제, 내부에서 EntityManager.remove()호출
    //findById(ID) : 엔티티 하나를 조회, 내부에서 EntityManager.find()호출
    //getOne(ID) : 엔티티를 프록시로 조회, 내부에서 EntityManager.getReference()호출
    //findAll() : 모든 엔티티를 조회, 정렬(Sort)이나 페이징(Pageable)조건을 파라미터로 제공

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
}
