#! /bin/sh/shell
mvn clean package -Dikongtiao.build.env=prod
scp ../ikongtiao-adminweb/target/admin.war root@121.40.123.201:/data/www/deploy/admin.war
scp ../ikongtiao-im/target/im.war root@121.40.123.201:/data/www/deploy/im.war
scp ../ikongtiao-notification/target/push.war root@121.40.123.201:/data/www/deploy/push.war
scp ../ikongtiao-web/target/web.war root@121.40.123.201:/data/www/deploy/web.war
scp ../ikongtiao-wechat/target/weixin.war root@121.40.123.201:/data/www/deploy/weixin.war
ssh root@121.40.123.201 "source /data/www/redeploy_webapps.sh"



