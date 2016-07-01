#! /bin/sh/expect
spawn scp ../ikongtiao-adminweb/target/admin.war root@121.40.123.201:/data/www/webapps/admin.new.war
expect "\r"
spawn scp ../ikongtiao-im/target/im.war root@121.40.123.201:/data/www/webapps/im.new.war
expect "\r"
spawn scp ../ikongtiao-notification/target/push.war root@121.40.123.201:/data/www/webapps/push.new.war
expect "\r%"
spawn scp ../ikongtiao-web/target/web.war root@121.40.123.201:/data/www/webapps/web.new.war
expect "\r%"
spawn scp ../ikongtiao-wechat/target/weixin.war root@121.40.123.201:/data/www/webapps/weixin.new.war
expect "\r%"
sleep 15
spawn ssh root@121.40.123.201
sleep 15
expect "#"
send "cd /data/www/webapps\r"
expect "#"
send "/usr/local/tomcat/bin/shutdown.sh\r"
expect "#"
send "rm -rf admin.war admin/ web.war web/ push.war push/ im.war im/ weixin.war weixin/"
expect "#"
send "mv admin.new.war admin.war\r"
expect "#"
send "mv web.new.war web.war\r"
expect "#"
send "mv im.new.war im.war\r"
expect "#"
send "mv push.new.war push.war\r"
expect "#"
send "mv weixin.new.war weixin.war\r"
expect "#"
send "/usr/local/tomcat/bin/startup.sh\n"
interact


