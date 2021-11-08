package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass //공통된 속성을 Entity에서 사용 가능
@Getter
public class JpaBaseEntity {

    @Column(updatable = false) //createdDate 업데이트 되지 않게!!
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now; //this.createdDate 라고 써도 됨
        updatedDate = now; //등록일과 수정일을 같이 맞춰놔야 나중에 수정하기 편리함
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
