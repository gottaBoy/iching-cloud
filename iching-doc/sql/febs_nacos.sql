/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : febs_nacos

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 04/11/2019 18:35:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
                                `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
                                `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
                                `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
                                `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
                                `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
                                `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (8, 'febs-gateway.yaml', 'DEFAULT_GROUP', 'server:\r\n  port: 8301\r\nspring:\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n        instance:\r\n          prefer-ip: true\r\n  cloud:\r\n    gateway:\r\n      globalcors:\r\n        corsConfigurations:\r\n          \'[/**]\':\r\n            allowedOrigins: \"*\"\r\n            allowedMethods: \"*\"\r\n            allowedHeaders: \"*\"\r\n            allowCredentials: true\r\n      routes:\r\n        - id: FEBS-Auth-Social\r\n          uri: lb://FEBS-Auth\r\n          predicates:\r\n            - Path=/auth/social/**\r\n          filters:\r\n            - name: Hystrix\r\n              args:\r\n                name: socialfallback\r\n                fallbackUri: forward:/fallback/FEBS-Auth\r\n        - id: FEBS-Auth\r\n          uri: lb://FEBS-Auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            - name: Hystrix\r\n              args:\r\n                name: authfallback\r\n                fallbackUri: forward:/fallback/FEBS-Auth\r\n        - id: FEBS-Server-System\r\n          uri: lb://FEBS-Server-System\r\n          predicates:\r\n            - Path=/system/**\r\n          filters:\r\n            - name: Hystrix\r\n              args:\r\n                name: systemfallback\r\n                fallbackUri: forward:/fallback/FEBS-Server-System\r\n        - id: FEBS-Server-test\r\n          uri: lb://FEBS-Server-Test\r\n          predicates:\r\n            - Path=/test/**\r\n          filters:\r\n            - name: Hystrix\r\n              args:\r\n                name: testfallback\r\n                fallbackUri: forward:/fallback/FEBS-Server-Test\r\n      loadbalancer:\r\n        use404: true\r\n      default-filters:\r\n        - StripPrefix=1\r\n\r\nhystrix:\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 3000\r\n    socialfallback:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 60000\r\n\r\nribbon:\r\n  eager-load:\r\n    enabled: true\r\n\r\nmanagement:\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: health,info,gateway', '161da681d1415dace6d7c974ba9e5005', '2019-09-18 10:37:02', '2019-10-11 10:18:13', NULL, '0:0:0:0:0:0:0:1', '', '', 'FEBS-Gateway微服务配置', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (15, 'febs-monitor-admin.yaml', 'DEFAULT_GROUP', 'server:\r\n  port: 8401\r\n\r\nspring:\r\n  security:\r\n    user:\r\n      name: febs\r\n      password: 123456\r\n  boot:\r\n    admin:\r\n      ui:\r\n        title: ${spring.application.name}', '2347087e64301f1d5e0e9a9410007def', '2019-09-19 09:39:49', '2019-09-19 09:39:49', NULL, '0:0:0:0:0:0:0:1', '', '', 'FEBS-Monitor-Admin微服务配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (16, 'febs-auth-dev.yaml', 'DEFAULT_GROUP', 'server:\r\n  port: 8101\r\nspring:\r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        connection-timeout: 30000\r\n        max-lifetime: 1800000\r\n        max-pool-size: 15\r\n        min-idle: 5\r\n        connection-test-query: select 1\r\n        pool-name: FebsHikariCP\r\n      primary: base\r\n      datasource:\r\n        base:\r\n          username: root\r\n          password: 123456\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://${mysql.url}:3306/febs_cloud_base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\r\n\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n\r\n  redis:\r\n    database: 0\r\n    host: ${redis.url}\r\n    port: 6379\r\n    lettuce:\r\n      pool:\r\n        min-idle: 8\r\n        max-idle: 500\r\n        max-active: 2000\r\n        max-wait: 10000\r\n    timeout: 5000\r\nmybatis-plus:\r\n  type-aliases-package: cc.mrbird.febs.common.entity.system\r\n  mapper-locations: classpath:mapper/*.xml\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\n\r\njustauth:\r\n  enabled: true\r\n  type:\r\n    github:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    gitee:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    tencent_cloud:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    dingtalk:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    qq:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    microsoft:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n  cache:\r\n    type: redis\r\n    prefix: \'FEBS::CLOUD::SOCIAL::STATE::\'\r\n    timeout: 1h\r\n\r\nfebs:\r\n  frontUrl: \'http://localhost:9527\'\r\n', '12b69dd276927b4d27f358e9a9174a80', '2019-09-19 09:56:38', '2019-10-11 09:13:07', NULL, '0:0:0:0:0:0:0:1', '', '', 'FEBS-Auth微服务配置（开发环境）', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (17, 'febs-auth-test.yaml', 'DEFAULT_GROUP', 'server:\r\n  port: 8101\r\nspring:\r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        connection-timeout: 30000\r\n        max-lifetime: 1800000\r\n        max-pool-size: 15\r\n        min-idle: 5\r\n        connection-test-query: select 1\r\n        pool-name: FebsHikariCP\r\n      primary: base\r\n      datasource:\r\n        base:\r\n          username: root\r\n          password: 123456\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://${mysql.url}:3306/febs_cloud_base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\r\n\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n\r\n  redis:\r\n    database: 0\r\n    host: ${redis.url}\r\n    port: 6379\r\n    lettuce:\r\n      pool:\r\n        min-idle: 8\r\n        max-idle: 500\r\n        max-active: 2000\r\n        max-wait: 10000\r\n    timeout: 5000\r\nmybatis-plus:\r\n  type-aliases-package: cc.mrbird.febs.common.entity.system\r\n  mapper-locations: classpath:mapper/*.xml\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\n\r\njustauth:\r\n  enabled: true\r\n  type:\r\n    github:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    gitee:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    tencent_cloud:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    dingtalk:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    qq:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    microsoft:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n  cache:\r\n    type: redis\r\n    prefix: \'FEBS::CLOUD::SOCIAL::STATE::\'\r\n    timeout: 1h\r\n\r\nfebs:\r\n  frontUrl: \'http://localhost:9527\'', '2227c43882d1e32a36f14e573a3dc781', '2019-09-19 09:57:30', '2019-10-11 09:13:32', NULL, '0:0:0:0:0:0:0:1', '', '', 'FEBS-Auth微服务配置（测试环境）', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (19, 'febs-auth-prod.yaml', 'DEFAULT_GROUP', 'server:\r\n  port: 8101\r\nspring:\r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        connection-timeout: 30000\r\n        max-lifetime: 1800000\r\n        max-pool-size: 15\r\n        min-idle: 5\r\n        connection-test-query: select 1\r\n        pool-name: FebsHikariCP\r\n      primary: base\r\n      datasource:\r\n        base:\r\n          username: root\r\n          password: 123456\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://${mysql.url}:3306/febs_cloud_base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\r\n\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n\r\n  redis:\r\n    database: 0\r\n    host: ${redis.url}\r\n    port: 6379\r\n    password: 123456\r\n    lettuce:\r\n      pool:\r\n        min-idle: 8\r\n        max-idle: 500\r\n        max-active: 2000\r\n        max-wait: 10000\r\n    timeout: 5000\r\nmybatis-plus:\r\n  type-aliases-package: cc.mrbird.febs.common.entity.system\r\n  mapper-locations: classpath:mapper/*.xml\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\n\r\njustauth:\r\n  enabled: true\r\n  type:\r\n    github:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    gitee:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    tencent_cloud:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    dingtalk:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    qq:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n    microsoft:\r\n      client-id: \r\n      client-secret: \r\n      redirect-uri: \r\n  cache:\r\n    type: redis\r\n    prefix: \'FEBS::CLOUD::SOCIAL::STATE::\'\r\n    timeout: 1h\r\n\r\nfebs:\r\n  frontUrl: \'http://localhost:9527\'', '998c44e08724e30187bcc601cc6f49ea', '2019-09-19 09:58:33', '2019-10-11 09:13:46', NULL, '0:0:0:0:0:0:0:1', '', '', 'FEBS-Auth微服务配置（生产环境）', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (21, 'febs-server-system-dev.yaml', 'DEFAULT_GROUP', 'server:\r\n  port: 8201\r\nspring:\r\n  aop:\r\n    proxy-target-class: true\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n  freemarker:\r\n    check-template-location: false\r\n\r\n  datasource:\r\n    dynamic:\r\n      p6spy: true\r\n      hikari:\r\n        connection-timeout: 30000\r\n        max-lifetime: 1800000\r\n        max-pool-size: 15\r\n        min-idle: 5\r\n        connection-test-query: select 1\r\n        pool-name: FebsHikariCP\r\n      primary: base\r\n      datasource:\r\n        base:\r\n          username: root\r\n          password: 123456\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://${mysql.url}:3306/febs_cloud_base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\r\n  zipkin:\r\n    sender:\r\n      type: rabbit\r\n  sleuth:\r\n    sampler:\r\n      probability: 1\r\n  rabbitmq:\r\n    host: ${rabbitmq.url}\r\n    port: 5672\r\n    username: febs\r\n    password: 123456\r\n\r\n  autoconfigure:\r\n    exclude: org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration\r\n\r\nmybatis-plus:\r\n  type-aliases-package: cc.mrbird.febs.common.entity.system\r\n  mapper-locations: classpath:mapper/*/*.xml\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      id: ${spring.application.name}\r\n      user-info-uri: http://${febs-gateway}:8301/auth/user\r\n\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS', '930c59fee7cbc281a297b9e328a9dc3b', '2019-09-19 10:14:31', '2019-09-19 10:14:31', NULL, '0:0:0:0:0:0:0:1', '', '', 'FEBS-Server-System微服务配置（开发环境）', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (22, 'febs-server-system-prod.yaml', 'DEFAULT_GROUP', 'server:\r\n  port: 8201\r\nspring:\r\n  aop:\r\n    proxy-target-class: true\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n  freemarker:\r\n    check-template-location: false\r\n\r\n  datasource:\r\n    dynamic:\r\n      p6spy: true\r\n      hikari:\r\n        connection-timeout: 30000\r\n        max-lifetime: 1800000\r\n        max-pool-size: 15\r\n        min-idle: 5\r\n        connection-test-query: select 1\r\n        pool-name: FebsHikariCP\r\n      primary: base\r\n      datasource:\r\n        base:\r\n          username: root\r\n          password: 123456\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://${mysql.url}:3306/febs_cloud_base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\r\n  zipkin:\r\n    sender:\r\n      type: rabbit\r\n  sleuth:\r\n    sampler:\r\n      probability: 1\r\n  rabbitmq:\r\n    host: ${rabbitmq.url}\r\n    port: 5672\r\n    username: febs\r\n    password: 123456\r\n\r\n  autoconfigure:\r\n    exclude: org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration\r\n\r\nmybatis-plus:\r\n  type-aliases-package: cc.mrbird.febs.common.entity.system\r\n  mapper-locations: classpath:mapper/*/*.xml\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      id: ${spring.application.name}\r\n      user-info-uri: http://${febs-gateway}:8301/auth/user\r\n\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS', '930c59fee7cbc281a297b9e328a9dc3b', '2019-09-19 10:15:27', '2019-09-21 23:18:55', NULL, '0:0:0:0:0:0:0:1', '', '', 'FEBS-Server-System微服务配置（生产环境）', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (23, 'febs-server-system-test.yaml', 'DEFAULT_GROUP', 'server:\r\n  port: 8201\r\nspring:\r\n  aop:\r\n    proxy-target-class: true\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n  freemarker:\r\n    check-template-location: false\r\n\r\n  datasource:\r\n    dynamic:\r\n      p6spy: true\r\n      hikari:\r\n        connection-timeout: 30000\r\n        max-lifetime: 1800000\r\n        max-pool-size: 15\r\n        min-idle: 5\r\n        connection-test-query: select 1\r\n        pool-name: FebsHikariCP\r\n      primary: base\r\n      datasource:\r\n        base:\r\n          username: root\r\n          password: 123456\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://${mysql.url}:3306/febs_cloud_base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\r\n  zipkin:\r\n    sender:\r\n      type: rabbit\r\n  sleuth:\r\n    sampler:\r\n      probability: 1\r\n  rabbitmq:\r\n    host: ${rabbitmq.url}\r\n    port: 5672\r\n    username: febs\r\n    password: 123456\r\n\r\n  autoconfigure:\r\n    exclude: org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration\r\n\r\nmybatis-plus:\r\n  type-aliases-package: cc.mrbird.febs.common.entity.system\r\n  mapper-locations: classpath:mapper/*/*.xml\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      id: ${spring.application.name}\r\n      user-info-uri: http://${febs-gateway}:8301/auth/user\r\n\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS', '930c59fee7cbc281a297b9e328a9dc3b', '2019-09-19 10:16:16', '2019-09-20 17:12:15', NULL, '222.76.46.138', '', '', 'FEBS-Server-System微服务配置（测试环境）', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (24, 'febs-server-test.yaml', 'DEFAULT_GROUP', 'server:\r\n  port: 8202\r\nspring:\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n  autoconfigure:\r\n    exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration\r\n\r\n  zipkin:\r\n    sender:\r\n      type: rabbit\r\n  sleuth:\r\n    sampler:\r\n      probability: 1\r\n  rabbitmq:\r\n    host: ${rabbitmq.url}\r\n    port: 5672\r\n    username: febs\r\n    password: 123456\r\n\r\nfeign:\r\n  hystrix:\r\n    enabled: true\r\n\r\nhystrix:\r\n  shareSecurityContext: true\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 3000\r\n\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      id: ${spring.application.name}\r\n      user-info-uri: http://${febs-gateway}:8301/auth/user\r\n\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS', '1134382382b519a507b647c86998c91f', '2019-09-19 10:17:25', '2019-11-04 18:33:09', NULL, '0:0:0:0:0:0:0:1', '', '', 'FEBS-Server-Test微服务配置', 'null', 'null', 'yaml', 'null');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                     `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                     `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
                                     `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
                                     `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
                                     `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                     `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                     `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                     `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
                                     `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
                                     `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
                                     `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
                                     `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
                                     `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
                                     `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
                                     `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
                                     `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                    `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
                                    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
                                    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
                                    `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
                                    `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
                                    `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
                                    `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
                                    `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
                                         `id` bigint(20) NOT NULL COMMENT 'id',
                                         `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
                                         `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
                                         `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                         `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                         `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                         `nid` bigint(20) NOT NULL AUTO_INCREMENT,
                                         PRIMARY KEY (`nid`) USING BTREE,
                                         UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
                                         INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
                                   `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                   `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
                                   `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
                                   `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
                                   `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
                                   `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
                                   `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
                                   `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
                                   `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
                                   `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
                                    `id` bigint(64) UNSIGNED NOT NULL,
                                    `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
                                    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
                                    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                    `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                    `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00',
                                    `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00',
                                    `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
                                    `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                    `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                    PRIMARY KEY (`nid`) USING BTREE,
                                    INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
                                    INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
                                    INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (16, 50, 'febs-auth-dev.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 8101\r\nspring:\r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        connection-timeout: 30000\r\n        max-lifetime: 1800000\r\n        max-pool-size: 15\r\n        min-idle: 5\r\n        connection-test-query: select 1\r\n        pool-name: FebsHikariCP\r\n      primary: base\r\n      datasource:\r\n        base:\r\n          username: root\r\n          password: 123456\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://${mysql.url}:3306/febs_cloud_base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\r\n\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n\r\n  redis:\r\n    database: 0\r\n    host: ${redis.url}\r\n    port: 6379\r\n    lettuce:\r\n      pool:\r\n        min-idle: 8\r\n        max-idle: 500\r\n        max-active: 2000\r\n        max-wait: 10000\r\n    timeout: 5000\r\nmybatis-plus:\r\n  type-aliases-package: cc.mrbird.febs.common.entity.system\r\n  mapper-locations: classpath:mapper/*.xml\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\n', 'b280b40eda7c079a46576c627127e1f4', '2010-05-05 00:00:00', '2019-10-11 09:13:07', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (17, 51, 'febs-auth-test.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 8101\r\nspring:\r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        connection-timeout: 30000\r\n        max-lifetime: 1800000\r\n        max-pool-size: 15\r\n        min-idle: 5\r\n        connection-test-query: select 1\r\n        pool-name: FebsHikariCP\r\n      primary: base\r\n      datasource:\r\n        base:\r\n          username: root\r\n          password: 123456\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://${mysql.url}:3306/febs_cloud_base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\r\n\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n\r\n  redis:\r\n    database: 0\r\n    host: ${redis.url}\r\n    port: 6379\r\n    lettuce:\r\n      pool:\r\n        min-idle: 8\r\n        max-idle: 500\r\n        max-active: 2000\r\n        max-wait: 10000\r\n    timeout: 5000\r\nmybatis-plus:\r\n  type-aliases-package: cc.mrbird.febs.common.entity.system\r\n  mapper-locations: classpath:mapper/*.xml\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\n', 'b280b40eda7c079a46576c627127e1f4', '2010-05-05 00:00:00', '2019-10-11 09:13:32', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (19, 52, 'febs-auth-prod.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 8101\r\nspring:\r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        connection-timeout: 30000\r\n        max-lifetime: 1800000\r\n        max-pool-size: 15\r\n        min-idle: 5\r\n        connection-test-query: select 1\r\n        pool-name: FebsHikariCP\r\n      primary: base\r\n      datasource:\r\n        base:\r\n          username: root\r\n          password: 123456\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://${mysql.url}:3306/febs_cloud_base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\r\n\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n\r\n  redis:\r\n    database: 0\r\n    host: ${redis.url}\r\n    port: 6379\r\n    password: 123456\r\n    lettuce:\r\n      pool:\r\n        min-idle: 8\r\n        max-idle: 500\r\n        max-active: 2000\r\n        max-wait: 10000\r\n    timeout: 5000\r\nmybatis-plus:\r\n  type-aliases-package: cc.mrbird.febs.common.entity.system\r\n  mapper-locations: classpath:mapper/*.xml\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\n', '2a73e1753884a17c51ca88e5a9d6fb60', '2010-05-05 00:00:00', '2019-10-11 09:13:46', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (8, 53, 'febs-gateway.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 8301\r\nspring:\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n        instance:\r\n          prefer-ip: true\r\n  cloud:\r\n    gateway:\r\n      globalcors:\r\n        corsConfigurations:\r\n          \'[/**]\':\r\n            allowedOrigins: \"*\"\r\n            allowedMethods: \"*\"\r\n            allowedHeaders: \"*\"\r\n            allowCredentials: true\r\n      routes:\r\n        - id: FEBS-Auth\r\n          uri: lb://FEBS-Auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            - name: Hystrix\r\n              args:\r\n                name: authfallback\r\n                fallbackUri: forward:/fallback/FEBS-Auth\r\n        - id: FEBS-Server-System\r\n          uri: lb://FEBS-Server-System\r\n          predicates:\r\n            - Path=/system/**\r\n          filters:\r\n            - name: Hystrix\r\n              args:\r\n                name: systemfallback\r\n                fallbackUri: forward:/fallback/FEBS-Server-System\r\n        - id: FEBS-Server-test\r\n          uri: lb://FEBS-Server-Test\r\n          predicates:\r\n            - Path=/test/**\r\n          filters:\r\n            - name: Hystrix\r\n              args:\r\n                name: testfallback\r\n                fallbackUri: forward:/fallback/FEBS-Server-Test\r\n      loadbalancer:\r\n        use404: true\r\n      default-filters:\r\n        - StripPrefix=1\r\n\r\nhystrix:\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 3000\r\n\r\nribbon:\r\n  eager-load:\r\n    enabled: true\r\n\r\nmanagement:\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: health,info,gateway', '5792434d160fd99b01fd2252ae233603', '2010-05-05 00:00:00', '2019-10-11 10:18:13', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (24, 54, 'febs-server-test.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 8202\r\nspring:\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n  autoconfigure:\r\n    exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration\r\n\r\n  zipkin:\r\n    sender:\r\n      type: rabbit\r\n  sleuth:\r\n    sampler:\r\n      probability: 1\r\n  rabbitmq:\r\n    host: ${rabbitmq.url}\r\n    port: 5672\r\n    username: febs\r\n    password: 123456\r\n\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      id: ${spring.application.name}\r\n      user-info-uri: http://${febs-gateway}:8301/auth/user\r\n\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS', '3bb77ea06e16d8d0ac4f47ff0ce13f74', '2010-05-05 00:00:00', '2019-10-25 16:49:50', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (24, 55, 'febs-server-test.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 8202\r\nspring:\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n  autoconfigure:\r\n    exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration\r\n\r\n  zipkin:\r\n    sender:\r\n      type: rabbit\r\n  sleuth:\r\n    sampler:\r\n      probability: 1\r\n  rabbitmq:\r\n    host: ${rabbitmq.url}\r\n    port: 5672\r\n    username: febs\r\n    password: 123456\r\n\r\nfeign:\r\n  hystrix:\r\n    enabled: true\r\n\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      id: ${spring.application.name}\r\n      user-info-uri: http://${febs-gateway}:8301/auth/user\r\n\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS', '755bbe796e3bd119dbab94cecc7f3ba1', '2010-05-05 00:00:00', '2019-11-04 17:35:04', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (24, 56, 'febs-server-test.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 8202\r\nspring:\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n  autoconfigure:\r\n    exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration\r\n\r\n  zipkin:\r\n    sender:\r\n      type: rabbit\r\n  sleuth:\r\n    sampler:\r\n      probability: 1\r\n  rabbitmq:\r\n    host: ${rabbitmq.url}\r\n    port: 5672\r\n    username: febs\r\n    password: 123456\r\n\r\nfeign:\r\n  hystrix:\r\n    enabled: true\r\n\r\nhystrix:\r\n  shareSecurityContext: true\r\n\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      id: ${spring.application.name}\r\n      user-info-uri: http://${febs-gateway}:8301/auth/user\r\n\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS', '7742746ab29a804704da24e619c4d7d2', '2010-05-05 00:00:00', '2019-11-04 18:15:48', NULL, '0:0:0:0:0:0:0:1', 'U', '');
INSERT INTO `his_config_info` VALUES (24, 57, 'febs-server-test.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 8202\r\nspring:\r\n  boot:\r\n    admin:\r\n      client:\r\n        url: http://${febs-monitor-admin}:8401\r\n        username: febs\r\n        password: 123456\r\n  autoconfigure:\r\n    exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration\r\n\r\n  zipkin:\r\n    sender:\r\n      type: rabbit\r\n  sleuth:\r\n    sampler:\r\n      probability: 1\r\n  rabbitmq:\r\n    host: ${rabbitmq.url}\r\n    port: 5672\r\n    username: febs\r\n    password: 123456\r\n\r\nfeign:\r\n  hystrix:\r\n    enabled: true\r\n\r\nhystrix:\r\n  shareSecurityContext: true\r\n\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      id: ${spring.application.name}\r\n      user-info-uri: http://${febs-gateway}:8301/auth/user\r\n\r\ninfo:\r\n  app:\r\n    name: ${spring.application.name}\r\n    description: \"@project.description@\"\r\n    version: \"@project.version@\"\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS', '7742746ab29a804704da24e619c4d7d2', '2010-05-05 00:00:00', '2019-11-04 18:33:09', NULL, '0:0:0:0:0:0:0:1', 'U', '');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
                          `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                          `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('febs', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
                                    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
                                    `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
                                    `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
                                    `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
                                    `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
                                    `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
                                    `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
                                    `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
                                    `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
                                `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
                                `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
                                `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
                                `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
                                `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
                                INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
                          `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                          `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                          `enabled` tinyint(1) NOT NULL,
                          PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('febs', '$2a$10$NJRJ.JUIUVX5suXNFRuOFezX5nzQLxl86OyMNnA7yxH1zr94H/gBS', 1);

SET FOREIGN_KEY_CHECKS = 1;
