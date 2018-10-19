
DROP TABLE IF EXISTS "t_user_info";
create table t_user_info(
    "id" int8 not null ,
    "username" varchar(50),
    "name" varchar(50),
    "password" varchar(200),
    "salt" varchar(200),
    "state" varchar(200),
    primary key ("id")
)WITH (OIDS=FALSE);


DROP TABLE IF EXISTS "t_sys_permission";
create table t_sys_permission(
    "id" int8 not null,
    "available" varchar(50)
    ,"name" varchar(50),
    "parent_id" int8,
    "parent_ids" varchar(200),
    "permission" varchar(50),
    "resource_type" varchar(50),
    "url" varchar(50),
    primary key ("id")
)WITH (OIDS=FALSE);


DROP TABLE IF EXISTS "t_sys_role";
create table t_sys_role(
    "id" int8 not null,
    "available" int2,
    "description" varchar(150),
    "role" varchar(150),
    primary key ("id")
)WITH (OIDS=FALSE);


DROP TABLE IF EXISTS "t_sys_role_permission";
create table t_sys_role_permission(
    "id" int8 not null,
    "permission_id" int8 not null,
    "role_id" int8,
    primary key ("id")
)WITH (OIDS=FALSE);


DROP TABLE IF EXISTS "t_sys_user_role";
create table t_sys_user_role(
    "id" int8 not null,
    "role_id" int8 not null,
    "user_id" int8 ,
    primary key ("id")
)WITH (OIDS=FALSE);

INSERT INTO "t_user_info" ("id","username","name","password","salt","state") VALUES ('1', 'admin', '管理员', '123456', '8d78869f470951332959580424d4bf4f', 0);
INSERT INTO "t_sys_permission" ("id","available","name","parent_id","parent_ids","permission","resource_type","url") VALUES (1,0,'用户管理',0,'0/','userInfoVO:view','menu','userInfoVO/userList');
INSERT INTO "t_sys_permission" ("id","available","name","parent_id","parent_ids","permission","resource_type","url") VALUES (2,0,'用户添加',1,'0/1','userInfoVO:add','button','userInfoVO/userAdd');
INSERT INTO "t_sys_permission" ("id","available","name","parent_id","parent_ids","permission","resource_type","url") VALUES (3,0,'用户删除',1,'0/1','userInfoVO:del','button','userInfoVO/userDel');
INSERT INTO "t_sys_role" ("id","available","description","role") VALUES (1,0,'管理员','admin');
INSERT INTO "t_sys_role" ("id","available","description","role") VALUES (2,0,'VIP会员','vip');
INSERT INTO "t_sys_role" ("id","available","description","role") VALUES (3,1,'test','test');
INSERT INTO "t_sys_role_permission" ("id","permission_id","role_id") VALUES (1,1,1);
INSERT INTO "t_sys_role_permission" ("id","permission_id","role_id") VALUES (2,1,1);
INSERT INTO "t_sys_role_permission" ("id","permission_id","role_id") VALUES (3,2,1);
INSERT INTO "t_sys_role_permission" ("id","permission_id","role_id") VALUES (4,3,2);
INSERT INTO "t_sys_user_role" ("id","role_id","user_id" ) VALUES (1,1,1);