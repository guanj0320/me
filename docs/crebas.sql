/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/5/27 10:10:12                           */
/*==============================================================*/


drop index Index_aid on me_album;

drop table if exists me_album;

drop index Index_createtime on me_article;

drop index Index_arcid on me_article;

drop table if exists me_article;

drop index Index_bid on me_banner;

drop table if exists me_banner;

drop index Index_colid on me_column;

drop table if exists me_column;

drop index Index_arcid on me_comment;

drop index Index_cid on me_comment;

drop table if exists me_comment;

drop index Index_fid on me_feedback;

drop table if exists me_feedback;

drop index Index_pid on me_photo;

drop table if exists me_photo;

drop index Index_tid on me_tag;

drop table if exists me_tag;

drop index Index_username on me_user;

drop index Index_uid on me_user;

drop table if exists me_user;

/*==============================================================*/
/* Table: me_album                                              */
/*==============================================================*/
create table me_album
(
   aid                  char(22) not null comment '相册id',
   username             varchar(20) comment '登录名',
   albumname            varchar(20) comment '相册名称',
   pic                  varchar(200) comment '相册图片地址',
   createtime           datetime comment '创建时间',
   primary key (aid)
);

/*==============================================================*/
/* Index: Index_aid                                             */
/*==============================================================*/
create index Index_aid on me_album
(
   aid
);

/*==============================================================*/
/* Table: me_article                                            */
/*==============================================================*/
create table me_article
(
   arcid                char(22) not null comment '文章id',
   colid                char(22) comment '专栏id',
   tid                  varchar(400) comment '存放标签中文',
   username             varchar(20) comment '文章发表人',
   property             int(1) comment '1:纯文字;2:图文',
   pic                  varchar(100) comment '缩略图',
   description          varchar(800) comment '文章摘要',
   content              mediumtext comment '文章内容',
   createtime           datetime comment '创建时间',
   updatetime           datetime comment '更新时间',
   clicl                int(6) comment '点击次数',
   source               varchar(200) comment '文章来源',
   ord                  int(4) comment '文章排序',
   title                varchar(100) comment '文章标题',
   primary key (arcid)
);

/*==============================================================*/
/* Index: Index_arcid                                           */
/*==============================================================*/
create index Index_arcid on me_article
(
   arcid
);

/*==============================================================*/
/* Index: Index_createtime                                      */
/*==============================================================*/
create index Index_createtime on me_article
(
   createtime
);

/*==============================================================*/
/* Table: me_banner                                             */
/*==============================================================*/
create table me_banner
(
   bid                  char(22) not null comment '轮播id',
   title                varchar(20) comment '标题',
   content              varchar(80) comment '内容',
   pic                  varchar(100) comment '图片',
   ord                  int(1) comment '排序',
   createtime           datetime comment '创建时间',
   updatetime           datetime comment '更新时间',
   primary key (bid)
);

/*==============================================================*/
/* Index: Index_bid                                             */
/*==============================================================*/
create index Index_bid on me_banner
(
   bid
);

/*==============================================================*/
/* Table: me_column                                             */
/*==============================================================*/
create table me_column
(
   colid                char(22) not null comment '专栏id',
   colname              varchar(20) comment '专栏名称',
   description          varchar(100) comment '专栏描述',
   seo                  varchar(50) comment '专栏seo',
   ord                  int(2) comment '专栏排序',
   pcolid               char(22) comment '上级专栏',
   primary key (colid)
);

/*==============================================================*/
/* Index: Index_colid                                           */
/*==============================================================*/
create index Index_colid on me_column
(
   colid
);

/*==============================================================*/
/* Table: me_comment                                            */
/*==============================================================*/
create table me_comment
(
   cid                  char(22) not null comment '评论id',
   arcid                char(22) comment '文章id',
   writer               varchar(20) comment '评价人',
   ip                   varchar(30),
   content              varchar(800) comment '评论内容',
   createtime           datetime comment '评论时间',
   email                varchar(50) comment '评论人邮箱',
   pcid                 char(22) comment '回复所属评论id',
   primary key (cid)
);

/*==============================================================*/
/* Index: Index_cid                                             */
/*==============================================================*/
create index Index_cid on me_comment
(
   cid
);

/*==============================================================*/
/* Index: Index_arcid                                           */
/*==============================================================*/
create index Index_arcid on me_comment
(
   arcid
);

/*==============================================================*/
/* Table: me_feedback                                           */
/*==============================================================*/
create table me_feedback
(
   fid                  char(22) not null comment '留言id',
   username             varchar(20) comment '登录名',
   writer               varchar(20) comment '留言人',
   ip                   varchar(30) comment 'IP地址',
   content              varchar(800) comment '留言内容',
   createtime           datetime comment '留言时间',
   email                varchar(50) comment '留言人邮箱',
   pfid                 char(22) comment '回复所属留言',
   isreply              int(1) comment '是否已回复',
   replytime            datetime comment '回复时间',
   replycontent         text comment '回复内容',
   primary key (fid)
);

/*==============================================================*/
/* Index: Index_fid                                             */
/*==============================================================*/
create index Index_fid on me_feedback
(
   fid
);

/*==============================================================*/
/* Table: me_photo                                              */
/*==============================================================*/
create table me_photo
(
   pid                  char(22) not null comment '照片id',
   aid                  char(22) comment '相册id',
   username             varchar(20) comment '登录名',
   title                varchar(100) comment '照片说明',
   src                  varchar(200) comment '原图地址',
   pre                  varchar(200) comment '预览图地址',
   width                int(5) comment '原图宽',
   height               int(5) comment '原图高',
   createtime           datetime comment '创建时间',
   primary key (pid)
);

/*==============================================================*/
/* Index: Index_pid                                             */
/*==============================================================*/
create index Index_pid on me_photo
(
   pid
);

/*==============================================================*/
/* Table: me_tag                                                */
/*==============================================================*/
create table me_tag
(
   tid                  char(22) not null comment '标签id',
   tagname              varchar(20) comment '标签名称',
   num                  int(4) comment '使用次数',
   createtime           datetime comment '创建时间',
   primary key (tid)
);

/*==============================================================*/
/* Index: Index_tid                                             */
/*==============================================================*/
create index Index_tid on me_tag
(
   tid
);

/*==============================================================*/
/* Table: me_user                                               */
/*==============================================================*/
create table me_user
(
   uid                  char(22) not null comment '用户id',
   username             varchar(20) not null comment '登录名',
   pwd                  varchar(40) comment '登录密码',
   nickname             varchar(20) comment '昵称',
   bindaccount          varchar(50) comment '绑定账号(暂不用)',
   logintime            datetime comment '登录时间',
   loginip              varchar(30) comment '登录IP',
   createtime           datetime comment '创建时间',
   updatetime           datetime comment '更新时间',
   email                varchar(50) comment '电子邮箱',
   primary key (username)
);

/*==============================================================*/
/* Index: Index_uid                                             */
/*==============================================================*/
create index Index_uid on me_user
(
   uid
);

/*==============================================================*/
/* Index: Index_username                                        */
/*==============================================================*/
create index Index_username on me_user
(
   username
);

alter table me_album add constraint FK_Reference_6 foreign key (username)
      references me_user (username) on delete restrict on update restrict;

alter table me_article add constraint FK_Reference_2 foreign key (colid)
      references me_column (colid) on delete restrict on update restrict;

alter table me_article add constraint FK_Reference_4 foreign key (username)
      references me_user (username) on delete restrict on update restrict;

alter table me_comment add constraint FK_Reference_1 foreign key (arcid)
      references me_article (arcid) on delete restrict on update restrict;

alter table me_feedback add constraint FK_Reference_5 foreign key (username)
      references me_user (username) on delete restrict on update restrict;

alter table me_photo add constraint FK_Reference_7 foreign key (aid)
      references me_album (aid) on delete restrict on update restrict;

alter table me_photo add constraint FK_Reference_8 foreign key (username)
      references me_user (username) on delete restrict on update restrict;

