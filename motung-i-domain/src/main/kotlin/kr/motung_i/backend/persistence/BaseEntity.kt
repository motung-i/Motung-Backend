package kr.motung_i.backend.persistence

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    open var createdAt: LocalDateTime? = null
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    open var updatedAt: LocalDateTime? = null
        protected set
}