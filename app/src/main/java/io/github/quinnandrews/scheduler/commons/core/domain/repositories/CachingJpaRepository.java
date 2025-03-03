package io.github.quinnandrews.scheduler.commons.core.domain.repositories;

import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

import static org.hibernate.jpa.HibernateHints.HINT_CACHEABLE;

@NoRepositoryBean
public interface CachingJpaRepository<T, ID> extends JpaRepository<T, ID>,
                                                     JpaSpecificationExecutor<T> {

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    default List<T> findAllWithCaching() {
        return findAll();
    }

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    default List<T> findAllWithCaching(Sort sort) {
        return findAll(sort);
    }

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    default Page<T> findAllWithCaching(Pageable pageable) {
        return findAll(pageable);
    }

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    default List<T> findAllWithCaching(Specification<T> specification) {
        return findAll(specification);
    }

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    default List<T> findAllWithCaching(Specification<T> specification, Sort sort) {
        return findAll(specification, sort);
    }

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    default Page<T> findAllWithCaching(Specification<T> specification, Pageable pageable) {
        return findAll(specification, pageable);
    }

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    default Optional<T> findOneWithCaching(Specification<T> specification) {
        return findOne(specification);
    }

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    default Optional<T> findByIdWithCaching(ID id) {
        return findById(id);
    }
}
