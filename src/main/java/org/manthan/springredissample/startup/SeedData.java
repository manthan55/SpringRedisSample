package org.manthan.springredissample.startup;

import org.manthan.springredissample.models.Post;
import org.manthan.springredissample.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// https://www.baeldung.com/running-setup-logic-on-startup-in-spring#:~:text=Spring%20Boot%20provides%20a%20CommandLineRunner,Spring%20application%20context%20is%20instantiated.
@Component
public class SeedData implements CommandLineRunner {
    private static final Logger LOG =
            LoggerFactory.getLogger(SeedData.class);
    private PostService postService;

    public SeedData(PostService postService)
    {
        this.postService = postService;
    }

    @Override
    public void run(String...args) throws Exception {
        LOG.info("Deleting existing data");
        this.postService.deleteAllPosts();
        LOG.info("Existing data deleted");

        LOG.info("Seeding sample data");

        List<Post> posts = new ArrayList<>();

        Date p1CreatedAt = Date.from(LocalDateTime.now().plusDays(-4).atZone(ZoneId.systemDefault()).toInstant());
        Date p2CreatedAt = Date.from(LocalDateTime.now().plusDays(-3).atZone(ZoneId.systemDefault()).toInstant());
        Date p3CreatedAt = Date.from(LocalDateTime.now().plusDays(-2).atZone(ZoneId.systemDefault()).toInstant());
        Date p4CreatedAt = Date.from(LocalDateTime.now().plusDays(-1).atZone(ZoneId.systemDefault()).toInstant());
        Date p5CreatedAt = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        Post a1p1 = new Post("A Crash Course in Database Sharding","As an application grows in popularity, it attracts more active users and incorporates additional features. This growth leads to a daily increase in data generation, which is a positive indicator from a business perspective.", p1CreatedAt);
        Post a1p2 = new Post("How Netflix Manages 238 Million Memberships?","As a subscription-based streaming service, Netflix's primary revenue source is its membership business. With a staggering 238 million members worldwide, managing memberships efficiently is crucial for the company's success and continued growth.", p2CreatedAt);
        Post a1p3 = new Post("A Crash Course on Microservice Communication Patterns","Microservices architecture promotes the development of independent services. However, these services still need to communicate with each other to function as a cohesive system.", p3CreatedAt);

        Post a2p1 = new Post("Brief History of Scaling Uber","On a cold evening in Paris in 2008, Travis Kalanick and Garrett Camp couldn't get a cab. That's when the idea for Uber was born. How great would it be if you could \"push a button and get a ride?\"", p4CreatedAt);
        Post a2p2 = new Post("Behind AWS S3’s Massive Scale","It’s the service that popularized the notion of cold-storage to the world of cloud. In essence - a scalable multi-tenant storage service which provides interfaces to store and retrieve objects with extremely high availability and durability at a relatively low cost. Customers share all of the underlying hardware.", p5CreatedAt);

        this.postService.addPost(a1p1);
        this.postService.addPost(a1p2);
        this.postService.addPost(a1p3);
        this.postService.addPost(a2p1);
        this.postService.addPost(a2p2);

        LOG.info("Sample data seeded");
    }
}
