package io.github.quinnandrews.scheduler.commons.core.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SpecificationJpaRepository<T, I> extends JpaRepository<T, I>,
                                                          JpaSpecificationExecutor<T> {
}
