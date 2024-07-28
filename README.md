# SpringRedisSample
This is a sample application demonstration redis caching capabilities in Spring Boot. This app is also deployable to AWS ElasticBeanstalk and integrates with AWS ElastiCache.

## Reach out to Me
- [LinkedIn - Manthan Patidar](https://www.linkedin.com/in/manthan-patidar/)
- [Slack - Manthan Patidar](https://scaler-co.slack.com/team/U0375PUUKFC)
    - Reachable only by Scaler students

## Application Overview
- The app has 3 endpoints
    - `/` -> Hello world endpoint which redirects to `/actuator/info`
    - `/posts/` -> Get all posts
    - `/post/{postId}` -> retrieve post by id
        - this endpoint is cached by redis
- Refer postman folder for collection & environment (you can import in postman)

## AWS ElastiCache Setup
1. Go To AWS Elasticache > Redis OSS Cache > Create and then select the following configuration options
![Create AWS ElastiCache - Configuration](./docs/assets/CreateAwsElastiCache_1.PNG?raw=true)

2. Disable `Cluster Mode` - as this is just for demo we dont need multiple shards
![Create AWS ElastiCache - Cluster Mode](./docs/assets/CreateAwsElastiCache_2.PNG?raw=true)

3. Set cache name (and optional description)
![Create AWS ElastiCache - Cluster Info](./docs/assets/CreateAwsElastiCache_3.PNG?raw=true)

4. Set Location and make sure to disable `MultiAZ` and `Auto Failover` to save some bucks
![Create AWS ElastiCache - Location](./docs/assets/CreateAwsElastiCache_4.PNG?raw=true)

5. Cluster Settings
    - Parameter groups -> `default.redis7`
    - Make sure to select smallest node type -> `cache.t2.micro`
    - And set number of replicas to `0` 
![Create AWS ElastiCache - Cluster Settings](./docs/assets/CreateAwsElastiCache_5.PNG?raw=true)

6. Subnet group settings
    - Provide a name and select a VPC
    - this will determine the placement of redis cache in the subnets and therefore its reachability from other subnets 
    - it is preferred to select only private subnets 
    - but as this is just for demo, I've selected all subnets available in the VPC
![Create AWS ElastiCache - Subnet Group Settings](./docs/assets/CreateAwsElastiCache_6.PNG?raw=true)

7. Availbility Zone Placements -- NO CHANGE
![Create AWS ElastiCache - Availbility Zone Placements](./docs/assets/CreateAwsElastiCache_7.PNG?raw=true)

8. Security
    - Disable `Encryption at rest`
    - Disable `Encryption in transit`
    - Select default security group of the VPC
![Create AWS ElastiCache - Security](./docs/assets/CreateAwsElastiCache_8.PNG?raw=true)

9. Backup
    - Disable as this is just for demo/learning purposes
![Create AWS ElastiCache - Backup](./docs/assets/CreateAwsElastiCache_9.PNG?raw=true)

10. Maintenance
    - Disable `Auto upgrade minor versions`
![Create AWS ElastiCache - Maintenance](./docs/assets/CreateAwsElastiCache_10.PNG?raw=true)

11. Logs
    - Disable `Slow logs`
    - Disable `Engine logs`
![Create AWS ElastiCache - Logs](./docs/assets/CreateAwsElastiCache_11.PNG?raw=true)

12. Hit `Create`
    - it takes about `~10` minutes to become available