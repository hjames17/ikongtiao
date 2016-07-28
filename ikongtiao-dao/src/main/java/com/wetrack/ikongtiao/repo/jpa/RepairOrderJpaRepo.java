package com.wetrack.ikongtiao.repo.jpa;

import com.wetrack.ikongtiao.domain.RepairOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zhanghong on 16/7/26.
 */
public interface RepairOrderJpaRepo extends JpaRepository<RepairOrder, Long> {
    //select * from repair_order where mission_id in {#list}
    // and repair_order_state!=6 and repair_order_state!=-1 group by mission_id
    @Query("select DISTINCT r.missionId from repair_order r where r.missionId in ?1 and r.repairOrderState not in ?2")
    List<Integer> findNonFinished(List<Integer> missionIds, List<Byte> states);
    //select * from repair_order where mission_id in #{list2} and repair_order_state=6 order by update_time desc
    List<RepairOrder> findByMissionIdInAndRepairOrderState(List<Integer> missionIds, Byte state, Sort sort);
}
