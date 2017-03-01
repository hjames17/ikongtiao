package com.wetrack.ikongtiao.notification.statistics;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.constant.RepairOrderState;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.statistics.MissionCompDuration;
import com.wetrack.ikongtiao.domain.statistics.MonthStats;
import com.wetrack.ikongtiao.domain.statistics.StatsCount;
import com.wetrack.ikongtiao.param.StatsQueryParam;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.AccessoryRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import com.wetrack.ikongtiao.repo.jpa.statistics.MonthStatsRepo;
import com.wetrack.ikongtiao.service.api.StatisticService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by zhanghong on 15/5/4.
 */
@Service
public class StatisticsImpl implements Statistics, InitializingBean, ResourceLoaderAware {

    static Logger log = LoggerFactory.getLogger(StatisticsImpl.class);

    @Value("${email.receipt.all}")
    String allMailString;

    @Value("${admin.page.host}")
    String adminPage;

    @Autowired
    OutputService<String> outputService;

    @Autowired
    MissionRepo missionRepo;

    @Autowired
    RepairOrderRepo repairOrderRepo;

    @Autowired
    AccessoryRepo accessoryRepo;

    @Autowired
    StatisticService statisticService;

    private Map<String, Object> stats(DateTime start, DateTime end){

        //统计新增任务
        StatsQueryParam param = new StatsQueryParam();
        param.setCreateTimeStart(start.toDate());
        param.setDescent(true);
        param.setCreateTimeEnd(end.toDate());
        param.setStates(new Integer[]{MissionState.NEW.getCode(), MissionState.ACCEPT.getCode(),
                MissionState.DISPATCHED.getCode(), MissionState.FIXING.getCode(), MissionState.COMPLETED.getCode()});
        int newMissionCount = missionRepo.countMissionByStatsParam(param);
        List<StatsCount> statsCounts = missionRepo.groupMissionByArea(param);

        //统计本周完成任务
        StatsQueryParam param2 = new StatsQueryParam();
        param2.setUpdateTimeStart(start.toDate());
        param2.setUpdateTimeEnd(end.toDate());
        param2.setStates(new Integer[]{MissionState.COMPLETED.getCode()});
        int finishedMissionCount = missionRepo.countMissionByStatsParam(param2);

        //统计剩余未关闭任务
        StatsQueryParam param3 = new StatsQueryParam();
        param3.setStates(new Integer[]{MissionState.NEW.getCode(), MissionState.ACCEPT.getCode(),
                MissionState.DISPATCHED.getCode(), MissionState.FIXING.getCode()});
        int unfinishedMissionCount = missionRepo.countMissionByStatsParam(param3);

        //统计本周期收入：
        //1. 本周期内新增的，而且状态介于已报价和已完成的维修单收入
        //TODO 本周期前创建的，于本周期报价维修的收入
        StatsQueryParam param4 = new StatsQueryParam();
        param4.setCreateTimeStart(start.toDate());
        param4.setCreateTimeEnd(end.toDate());
        param4.setStates(new Integer[]{RepairOrderState.COST_READY.getCode().intValue(), RepairOrderState.PREPARED.getCode().intValue(), RepairOrderState.AUDIT_READY.getCode().intValue(),
                RepairOrderState.CONFIRMED.getCode().intValue(), RepairOrderState.FIXING.getCode().intValue(), RepairOrderState.COMPLETED.getCode().intValue()});
        int laborCost = 0;//repairOrderRepo.countLaborCostByStatsParam(param4);
        int accessoryCost = 0;
        List<RepairOrder> repairOrders = repairOrderRepo.listByStatsParam(param4);
        if(repairOrders != null && repairOrders.size() > 0 ) {
            List<Long> repairOrderIds = new ArrayList<Long>();
            repairOrders.forEach(repairOrder -> {
                repairOrderIds.add(repairOrder.getId());
            });
            for (RepairOrder item : repairOrders) {
                if(item.getLaborCost() != null) {
                    laborCost += item.getLaborCost();
                }
            }
            accessoryCost = accessoryRepo.countMoneyInRepairOrders(repairOrderIds);

        }

        //统计本周完成的任务的时间分布
        MissionCompDuration duration = statisticService.statsDuration(start.toDate(), end.toDate());

        Long all = duration.countAll();

        //TODO 任务按省排列


//        System.out.printf("new mission %d, finish mission %d, unfinishMisson %d, income %d\n", newMissionCount, finishedMissionCount, unfinishedMissionCount, (laborCost + accessoryCost) / 100);

        Map<String, Object> map = new HashMap<>();
        map.put("fromDay", start.toString("yyyy-MM-dd"));
        map.put("endDay", end.toString("yyyy-MM-dd"));
        map.put("newMissionCount", newMissionCount);
        map.put("finishedMissionCount", finishedMissionCount);
        map.put("unfinishedMissionCount", unfinishedMissionCount);
        map.put("income", (laborCost + accessoryCost) / 100f);
        map.put("incomeCent", laborCost + accessoryCost);
        map.put("startTime", start.getMillis());
        map.put("endTime", end.getMillis());
        map.put("adminPage", adminPage);
        if(all > 0) {
            map.put("durations", Arrays.asList(duration.getDay1() / all.doubleValue(), duration.getDay3() / all.doubleValue(), duration.getDay7() / all.doubleValue(), duration.getDay15() / all.doubleValue(), duration.getDay30() / all.doubleValue(), duration.getDayMax()/all.doubleValue()));
        }else{
            map.put("durations", Arrays.asList(0,0,0,0,0,0));
        }

        if(statsCounts != null&& statsCounts.size() > 0){
            statsCounts.forEach(areaCount -> {
                String pId = null;
                try{
                    pId = areaCount.getId();
                }catch (Exception e){

                }

                Map<String, Object> province = (Map<String, Object>)areaMap.get(pId);
                if(province != null) {
                    areaCount.setName(province.get("name").toString());
                }else{
                    areaCount.setName("其他");
                }
            });
            map.put("areaList", statsCounts);
        }

        return map;
    }


    @Override
    @Scheduled(cron = "0 0 8 ? * MON")
    public void weeklyReport() {


        DateTime mondayMorning = new DateTime();//.minusDays(1).withHourOfDay(0).withMinuteOfHour(0);
        mondayMorning = mondayMorning.minusWeeks(1);
        DateTime sundayNight = new DateTime(mondayMorning);//new DateTime(yesterdayStart).withHourOfDay(23).withMinuteOfHour(59);
        mondayMorning = mondayMorning.withDayOfWeek(1).withHourOfDay(0).withMinuteOfHour(0);
        sundayNight = sundayNight.withDayOfWeek(7).withMinuteOfHour(23).withMinuteOfHour(59);

        Map<String, Object> map = stats(mondayMorning, sundayNight);

        String pieChartString = outputService.getOutput(map, "piechart");

        String url = getPiechartFileUrl(pieChartString, mondayMorning.toString("yyyyMMdd"));

//        String trueUrl = storePiechartFile(tempUrl, mondayMorning.toString("yyyyMMdd"));

        map.put("chartUrl", url);

        sendMail("维大师运营周报[" + mondayMorning.toLocalDate() + "到" + sundayNight.toLocalDate() + "]",
                outputService.getOutput(map, "weeklyReport"));
    }

    @Autowired
    MonthStatsRepo monthStatsRepo;

    @Override
    @Scheduled(cron = "0 0 8 1 * *")
    public void monthReport() {
        DateTime firstDayStart = new DateTime().withDayOfMonth(1).minusMonths(1).withHourOfDay(0).withMinuteOfHour(0);;//.minusDays(1).withHourOfDay(0).withMinuteOfHour(0);
        DateTime lastDayEnd = new DateTime().withDayOfMonth(1).minusDays(1).withMinuteOfHour(23).withMinuteOfHour(59);

        Map<String, Object> map = stats(firstDayStart, lastDayEnd);

        String pieChartString = outputService.getOutput(map, "piechart");
        String url = getPiechartFileUrl(pieChartString, firstDayStart.toString("yyyyMM"));
        map.put("chartUrl", url);

        int month = Integer.valueOf(firstDayStart.toString("yyyyMM"));
        map.put("month", month);

        sendMail("维大师" + month + "月运营数据", outputService.getOutput(map, "monthlyReport"));


        //保存记录
        MonthStats ms = monthStatsRepo.findByMonth(month);
        if(ms == null){
            ms = new MonthStats();
            ms.setTime(firstDayStart.toDate());
            ms.setMonth(month);
        }
        ms.setNewMissionCount(Integer.valueOf(map.get("newMissionCount").toString()));
        ms.setFinishedMissionCount(Integer.valueOf(map.get("finishedMissionCount").toString()));
        ms.setUnfinishedMissionCount(Integer.valueOf(map.get("unfinishedMissionCount").toString()));
        ms.setIncome(Integer.valueOf(map.get("incomeCent").toString()));
        monthStatsRepo.save(ms);
    }

    @Value("${store.dir}${store.dir.image.chart}")
    String chartPath;

    @Value("${host.static}${store.dir.image.chart}")
    String chartUriPath;

    private String storePiechartFile(String uri, String fileName){
        if(StringUtils.isEmpty(uri)){
            return null;
        }

        try {
            URI parseUri = new URI(uri);
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https")
                    .setHost("export.highchats.com").setPath(parseUri.getPath());

            HttpGet httpGet = new HttpGet(builder.build());

            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpGet);
            if(response != null && response.getStatusLine() != null) {

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String fileNameAndType = fileName + ".png";
                    FileUtils.copyInputStreamToFile(response.getEntity().getContent(), new File(chartPath + "/" + fileNameAndType));
                    return chartUriPath + "/" + fileNameAndType;
                } else {
                    return null;
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getPiechartFileUrl(String pieChartString, String fileName) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("infile", pieChartString);
        jsonMap.put("async", false);
        jsonMap.put("asyncRendering", false);
        jsonMap.put("type", "image/png");
        jsonMap.put("width", false);
        jsonMap.put("styleMode", false);
        jsonMap.put("scale", false);
        jsonMap.put("constr", "Chart");


        String dataString = Jackson.base().writeValueAsString(jsonMap);

        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https")
                    .setHost("export.highcharts.com").setPath("/");

            HttpPost httpPost = new HttpPost(builder.build());

            httpPost.setEntity(new StringEntity(dataString, ContentType.APPLICATION_JSON));

            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            if(response != null && response.getStatusLine() != null) {

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                    String dataStr = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
//                    return dataStr;
                    String fileNameAndType = fileName + ".png";
                    FileUtils.copyInputStreamToFile(response.getEntity().getContent(), new File(chartPath + "/" + fileNameAndType));
                    return chartUriPath + "/" + fileNameAndType;
                } else {
                    return null;
                }
            }

        } catch (URISyntaxException e) {
            log.error("generate request for high charts pie chart converter error : " + e.getMessage());
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    @Autowired
    MessageService messageService;
    private void sendMail(String title, String text){
        Map<String, Object> params = new HashMap<>();
        params.put(MessageParamKey.TITLE , title);
        params.put(MessageParamKey.CONTENT , text);

        params.put(MessageParamKey.MAIL_RECEIVER, allMailString);
        messageService.send(MessageId.WEEKLY_REPORT, params);

    }

    Map<String, Object> areaMap;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        if(areaMap == null) {
            Resource resource = resourceLoader.getResource("classpath:/city.json");
            try {
                StringBuilder stringBuilder = new StringBuilder();
                InputStream is = resource.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
                br.close();
                areaMap = Jackson.base().readValue(stringBuilder.toString(), Map.class);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    public static void main(String[] args){
        System.out.println((5000 + 2550) / 100f);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
