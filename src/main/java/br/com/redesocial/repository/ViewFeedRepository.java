package br.com.redesocial.repository;

import br.com.redesocial.model.ViewFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewFeedRepository extends JpaRepository<ViewFeed, Long> {
    
}
