package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
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

    //메소드 이름으로 쿼리 생성
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    //JPA NamedQuery
    //@Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    //@Query -> 레포지토리 메소드에 쿼리 정의하기
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    //@Query -> 값,DTO 조회하기
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();
}
