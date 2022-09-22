/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : PostgreSQL
 Source Server Version : 140005
 Source Host           : localhost:5432
 Source Catalog        : activiti7_learning
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140005
 File Encoding         : 65001

 Date: 23/09/2022 02:53:09
*/


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "public"."user";
CREATE TABLE "public"."user" (
                                 "id" int4 NOT NULL,
                                 "name" text COLLATE "pg_catalog"."default",
                                 "address" text COLLATE "pg_catalog"."default",
                                 "username" text COLLATE "pg_catalog"."default",
                                 "password" text COLLATE "pg_catalog"."default",
                                 "roles" text COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."user" OWNER TO "postgres";

-- ----------------------------
-- Records of user          密码：1        加密器：BCryptPasswordEncoder
-- ----------------------------
BEGIN;
INSERT INTO "public"."user" VALUES (1, 'admincn', 'beijing', 'admin', '$2a$10$Kcf7YJUoQlt6O8P5kJWjzOaN2gZLmtX32xWrxWpZ5I1Bavu6bputC', 'ROLE_ACTIVITI_ADMIN');
INSERT INTO "public"."user" VALUES (3, 'wukongcn', 'beijing', 'wukong', '$2a$10$Kcf7YJUoQlt6O8P5kJWjzOaN2gZLmtX32xWrxWpZ5I1Bavu6bputC', 'ROLE_ACTIVITI_USER,GROUP_activitiTeam');
INSERT INTO "public"."user" VALUES (2, 'bajiecn', 'shanghang', 'bajie', '$2a$10$Kcf7YJUoQlt6O8P5kJWjzOaN2gZLmtX32xWrxWpZ5I1Bavu6bputC', 'ROLE_ACTIVITI_USER,GROUP_activitiTeam,g_bajiewukong');
INSERT INTO "public"."user" VALUES (4, 'salaboycn', 'beijing', 'salaboy', '$2a$10$Kcf7YJUoQlt6O8P5kJWjzOaN2gZLmtX32xWrxWpZ5I1Bavu6bputC', 'ROLE_ACTIVITI_USER,GROUP_activitiTeam');
COMMIT;

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE "public"."user" ADD CONSTRAINT "user_pkey" PRIMARY KEY ("id");
