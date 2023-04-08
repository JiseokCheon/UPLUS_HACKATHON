package uplus.hackerton.barogo.domain;

import java.time.ZonedDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

	@CreationTimestamp
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	private ZonedDateTime updatedAt;
}
