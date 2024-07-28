# SpringRedisSample
This is a sample application demonstration redis caching capabilities in Spring Boot. This app is also deployable to AWS ElasticBeanstalk and integrates with AWS ElastiCache.

---

## Reach out to Me
- [LinkedIn - Manthan Patidar](https://www.linkedin.com/in/manthan-patidar/)
- [Slack - Manthan Patidar](https://scaler-co.slack.com/team/U0375PUUKFC)
    - Reachable only by Scaler students

---

## Application Overview
- A simple post management system allowing Read operations on and `Posts` using SQLite DB as persistence store.
- During the startup sequence, the project seeds dummy data into the SQLite database to have something to play with. This is done via the `SeedData` class within `startup` package.

> **Note:** All data is wiped (from both SQLite DB as well as ES) on every reboot of the app.

- The app has 3 endpoints
    - `GET /` -> Hello world endpoint which redirects to `/actuator/info`
    - `GET /posts/` -> Get all posts
    - `GET /post/{postId}` -> retrieve post by id
        - The initial call will take `4 seconds` to respond - but subsequent calls will be much faster thanks to reading from redis cache. The TTL for entries in cache is set to `30 seconds`.
> **Note:** As we are using SQLite database, the code intentionally includes a `4 second` delay to simulate a proper (over network) DB call.

- Refer postman folder for collection & environment (you can import in postman)

### Spring Profiles

The Spring Boot project contain multiple `application.yaml` files accounting for multiple environments/profiles
- application-local.yaml
	- is used when running the project locally via IntelliJ and uses the dependencies running in docker containers (or you can also setup other locally running dependencies)
- application-aws.yaml
	- is used when the app is deployed on AWS Elasticbeanstalk and uses AWS Elasticache

The above profiles can be switched at runtime by setting the environment variable,

```
SPRING_PROFILES_ACTIVE=aws
```

---

## Running Locally
- Open the project in IntelliJ
- Make sure the redis server is running before you run the project
    - redis server can be setup via docker or by any other means (docker setup can be achieved by running the provided `docker-compose.yml` file in the docker folder)

## Running on AWS
- Setup AWS ElastiCache (steps mentioned below)
- Copy the AWS ElastiCache endpoint & port in `application-aws.yaml` file
- Create a `.jar` file
- Setup AWS ElasticBeanstalk and deploy the `.jar` file.

---

### AWS ElastiCache Setup
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

13. Once created the endpoint is available here
![Create AWS ElastiCache - Endpoint](./docs/assets/CreateAwsElastiCache_12.PNG?raw=true)