package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    //@Query -> 값 조회하기
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    //@Query -> DTO 조회하기
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    //파라미터 바인딩(이름 기반)
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    //반환 타입
    List<Member> findListByUsername(String username); //컬렉션
    Member findMemberByUsername(String username); //단건
    Optional<Member> findOptionalByUsername(String username); //단건 Optional

    //페이징과 정렬
    //@Query(value = "select m from Member m left join m.team t",
    //        countQuery = "select count(m) from Member m")
    //1. Page -> 추가 count 쿼리 결과를 포함하는 페이징
    Page<Member> findByAge(int age, Pageable pageable);
    //2. Slice -> 추가 count 쿼리 없이 다음 페이지만 확인 가능(내부적으로 limit + 1조회)
    //Slice<Member> findByAge(int age, Pageable pageable);
    //3. List -> 추가 count 쿼리 없이 결과만 반환

    //벌크성 수정 쿼리
    @Modifying(clearAutomatically = true) //JPA의 executeUpdate()를 실행하기 위해 필요한 어노테이션
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    //Fetch Join -- @EntityGraph
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    //JPA Hint & Lock
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
