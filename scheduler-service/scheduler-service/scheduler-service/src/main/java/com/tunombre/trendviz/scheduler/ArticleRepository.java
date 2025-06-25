package com.tunombre.trendviz.scheduler;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    // Puedes agregar métodos customizados si los necesitas, por ejemplo:
    // List<Article> findByCountryOrderByPublishedAtDesc(String country);
}
