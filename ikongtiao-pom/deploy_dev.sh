#! /bin/sh/shell
mvn clean package -Dikongtiao.build.env=dev
scp ../ikongtiao-adminweb/target/admin.war root@test.waids.cn:/data/www/deploy/admin.war
scp ../ikongtiao-im/target/im.war root@test.waids.cn:/data/www/deploy/im.war
scp ../ikongtiao-notification/target/push.war root@test.waids.cn:/data/www/deploy/push.war
scp ../ikongtiao-web/target/web.war root@test.waids.cn:/data/www/deploy/web.war
scp ../ikongtiao-wechat/target/weixin.war root@test.waids.cn:/data/www/deploy/weixin.war
ssh root@test.waids.cn "source /data/www/redeploy_webapps.sh"



